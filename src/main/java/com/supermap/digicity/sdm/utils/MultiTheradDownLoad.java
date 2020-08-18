package com.supermap.digicity.sdm.utils;

import com.supermap.digicity.sdm.Event.PercentEvent;
import com.supermap.digicity.sdm.Event.PercentListener;
import com.supermap.digicity.sdm.model.ProduceInfo;
import com.supermap.digicity.sdm.utils.loadxml.LoadProductStartupUtils;
import org.dom4j.DocumentException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: zhangjun
 * @Description:根据输入的url和设定的线程数，来完成断点续传功能。
 *  *每个线程支负责某一小段的数据下载；再通过RandomAccessFile完成数据的整合
 * @Date: Create in 10:15 2020/5/13
 * 。
 */
public class MultiTheradDownLoad {

    static String uri;

    static {
        try {
            uri = LoadProductStartupUtils.getProductStartup().get("HTTPURL");
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private String urlStr = null;
    private String filename = null;
    private String tmpfilename = null;
    private int threadNum = 1;
    private CountDownLatch latch = null;//设置一个计数器，代码内主要用来完成对缓存文件的删除
    private long fileLength = 0L;
    private long filebrackpointlenth=0L;
    private long threadLength = 0L;
    private long[] startPos;//保留每个线程下载数据的起始位置。
    private long[] endPos;//保留每个线程下载数据的截止位置。
    private boolean bool = false;
    private URL url = null;
    private String productversion;
    private String productname;
    private ProduceInfo produceInfo;
    private long allcount=0L;
    private long remotefilesize;
    public  int percent=0;

    PercentListener percentListener;

    public void addPercentListener(PercentListener percentListener){
        this.percentListener=percentListener;
    }

    //有参构造函数，先构造需要的数据 productname productversion
    public MultiTheradDownLoad(ProduceInfo produceInfo) throws UnsupportedEncodingException {
        String productname= URLEncoder.encode(produceInfo.getProduceName(),"UTF-8");
        this.urlStr = uri+"downFile/"+productname+"/"+produceInfo.getDownloadProcessVersion();
        this.remotefilesize=HttpClientUtils.getRemoteZipSize(produceInfo.getProduceName(),produceInfo.getDownloadProcessVersion());
        this.produceInfo=produceInfo;
        this.productname = produceInfo.getProduceName();
        this.productversion = produceInfo.getDownloadProcessVersion();
        startPos = new long[this.threadNum];
        endPos = new long[this.threadNum];
        latch = new CountDownLatch(this.threadNum);
    }

    /*
     * 组织断点续传功能的方法
     */
    public void downloadPart() {
        File file = null;
        File tmpfile = null;
        //设置HTTP网络访问代理
        //从文件链接中获取文件名，此处没考虑文件名为空的情况，此种情况可能需使用UUID来生成一个唯一数来代表文件名。
        //改成从request中获取文件名
        try {
            //创建url
            url = new URL(urlStr);
            //打开下载链接，并且得到一个HttpURLConnection的一个对象httpcon
            HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
            httpcon.setRequestMethod("GET");
            httpcon.setConnectTimeout(2000000);
            //文件的实际名称与大小
            String productPackagename= HttpClientUtils.getNewProductName(produceInfo.getProduceName(),produceInfo.getDownloadProcessVersion());
            //下载到当前安装目录的父级
            File downloadpath =new File(produceInfo.getDownloadProduceSetupLocal());
            downloadpath.getParent();
            filename = downloadpath.getParent()+"\\"+ productPackagename;
            tmpfilename = filename + "_tmp";
            fileLength = httpcon.getContentLengthLong();
            //下载文件和临时文件
            file = new File(filename);//相对目录
            tmpfile = new File(tmpfilename);
            //每个线程需下载的资源大小；由于文件大小不确定，为避免数据丢失
            threadLength = fileLength%threadNum == 0 ? fileLength/threadNum : fileLength/threadNum+1;
            //打印下载信息
            System.out.println("80 文件名称: " + filename + " ," + "文件长度= "
                    + fileLength + "  线程的每个文件的大小= " + threadLength);

            //各个线程在exec线程池中进行，起始位置--结束位置
            if (file.exists()&& file.length() == fileLength) {
                System.out.println("85 文件已存在!!");
                return;
            } else {
                setBreakPoint(startPos, endPos, tmpfile,file);
                ExecutorService exec = Executors.newCachedThreadPool();
                for (int i = 0; i < threadNum; i++) {
                    exec.execute(new DownLoadThread(startPos[i], endPos[i],
                            this, i, tmpfile, latch));
                }
                latch.await();//当你的计数器减为0之前，会在此处一直阻塞。
                exec.shutdown();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //下载完成后，判断文件是否完整，并删除临时文件
        if (file.length() == fileLength) {
            if (tmpfile.exists()) {
                tmpfile.delete();
            }
        }
    }

    /**
     * 断点设置方法，当有临时文件时，直接在临时文件中读取上次下载中断时的断点位置。没有临时文件，即第一次下载时，重新设置断点。
     * Rantmpfile.seek()跳转到一个位置的目的是为了让各个断点存储的位置尽量分开。
     * 这是实现断点续传的重要基础。
     */
    private void setBreakPoint(long[] startPos, long[] endPos, File tmpfile,File file) {
        RandomAccessFile rantmpfile = null;
        try {
            if (tmpfile.exists()){
             //   System.out.println("继续下载!!");
                filebrackpointlenth=file.length();
                percent=(int)((filebrackpointlenth/(remotefilesize*1.01))*100);
                percentListener.updateEvent(new PercentEvent(this,percent));
                rantmpfile = new RandomAccessFile(tmpfile, "rw");
                for (int i = 0; i < threadNum; i++) {
                    rantmpfile.seek(8 * i + 8);
                    startPos[i] = rantmpfile.readLong();
                    rantmpfile.seek(8 * (i + 1000) + 16);
                    endPos[i] = rantmpfile.readLong();
                    System.out.println("线程的每个分片的大小: ");
                    System.out.println("thre 线程   " + (i + 1) + "  开始节点:"
                            + startPos[i] + ", 结束节点: " + endPos[i]);
                }
            } else {
                System.out.println(" 149 没有临时文件 重新开始下载 ");
                rantmpfile = new RandomAccessFile(tmpfile, "rw");
                //最后一个线程的截止位置大小为请求资源的大小
                for (int i = 0; i < threadNum; i++) {
                    startPos[i] = threadLength * i;
                    if (i == threadNum - 1) {
                        endPos[i] = fileLength;
                    } else {
                        endPos[i] = threadLength * (i + 1) - 1;
                    }
                    rantmpfile.seek(8 * i + 8);
                    rantmpfile.writeLong(startPos[i]);
                    rantmpfile.seek(8 * (i + 1000) + 16);
                    rantmpfile.writeLong(endPos[i]);
                    System.out.println("重新开始下载的文件分片:");
                    System.out.println(" 线程   " + (i + 1) + " 开始节点: "
                            + startPos[i] + ", 结束节点  : " + endPos[i]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rantmpfile != null) {
                    rantmpfile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * 实现下载功能的内部类，通过读取断点来设置向服务器请求的数据区间。
     */
    class DownLoadThread implements Runnable {

        private long startPos;
        private long endPos;
        private MultiTheradDownLoad task = null;
        private RandomAccessFile downloadfile = null;
        private int id;
        private File tmpfile = null;
        private RandomAccessFile rantmpfile = null;
        private CountDownLatch latch = null;

        public DownLoadThread(long startPos, long endPos,
                              MultiTheradDownLoad task, int id, File tmpfile,
                              CountDownLatch latch) {
            this.startPos = startPos;
            this.endPos = endPos;
            this.task = task;
            this.tmpfile = tmpfile;
            try {
                this.downloadfile = new RandomAccessFile(this.task.filename,"rw");
                this.rantmpfile = new RandomAccessFile(this.tmpfile, "rw");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            this.id = id;
            this.latch = latch;
        }

        @Override
        public void run() {
            HttpURLConnection httpcon = null;
            InputStream is = null;
            int length = 0;
            System.out.println("线程" + id + " 开始下载!!");
            while (true) {
                try {
                    httpcon = (HttpURLConnection) task.url.openConnection();
                    httpcon.setRequestMethod("GET");
                    //防止网络阻塞，设置指定的超时时间；单位都是ms。超过指定时间，就会抛出异常
                     httpcon.setReadTimeout(200000000);//读取数据的超时设置
                     httpcon.setConnectTimeout(200000000);//连接的超时设置
                    if (startPos < endPos) {
                        //向服务器请求指定区间段的数据，这是实现断点续传的根本。
                        httpcon.setRequestProperty("Range", "bytes=" + startPos+ "-" + endPos);
                        System.out.println("线程 " + id+ " 长度:---- "+ (endPos - startPos));
                        downloadfile.seek(startPos);
                        if (httpcon.getResponseCode() != HttpURLConnection.HTTP_OK
                                && httpcon.getResponseCode() != HttpURLConnection.HTTP_PARTIAL) {
                            this.task.bool = true;
                            httpcon.disconnect();
                            downloadfile.close();
                            System.out.println("线程 ---" + id + " 下载完成!!");
                            latch.countDown();//计数器自减
                            break;
                        }
                        is = httpcon.getInputStream();//获取服务器返回的资源流
                        long count = 0L;
                        byte[] buf = new byte[1024*1024*2];
                        while (!this.task.bool && (length = is.read(buf)) != -1) {
                            allcount+=length;
                            count += length;
                            downloadfile.write(buf, 0, length);
                            //不断更新每个线程下载资源的起始位置，并写入临时文件；为断点续传做准备
                            startPos += length;
                            rantmpfile.seek(8 * id + 8);
                            rantmpfile.writeLong(startPos);
                            percent=(int)(((allcount+filebrackpointlenth)/(remotefilesize*1.01))*100);
                            percentListener.updateEvent(new PercentEvent(this,percent));
                        }
                        is.close();
                        httpcon.disconnect();
                        downloadfile.close();
                        rantmpfile.close();
                    }
                    latch.countDown();//计数器自减
                    System.out.println("线程 " + id + " 下载完成!!");
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    public static void main(String[] args) throws UnsupportedEncodingException {

        int threadNum = 1;
        String productname= URLEncoder.encode("数据处理系统","UTF-8");
        String filepath = "http://172.16.15.250:8080/ftp/downFile/"+productname+"/10.0";
        System.out.println(filepath);
       // MultiTheradDownLoad load = new MultiTheradDownLoad(filepath ,threadNum);
        //load.downloadPart();
    }
}