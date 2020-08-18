package com.supermap.digicity.sdm.utils;

import com.supermap.digicity.sdm.Event.PercentEvent;
import com.supermap.digicity.sdm.Event.PercentListener;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * @Description 实现对文件的压缩和解压
 * @Auther zhoujinqiao
 * @Data 2020/5/6 11:24
 **/
public class ZipUtils {
    public  double compresspercent;
    public  double unZippercent;
    PercentListener percentListener;
    long compressallsize;
    long compresstempsize;
    long unZiptmpsize;
    double zipFileLength;
    int flag;

    public ZipUtils(){
        compresspercent=0.0;
        unZippercent=50.0;
        compressallsize=0;
        compresstempsize=0;
        unZiptmpsize=0;
        zipFileLength=0;
        flag=0;
    }

    public void addPercentListener(PercentListener percentListener){
        this.percentListener=percentListener;
    }

    /**
     * 解压文件到指定目录
     * @param zipPath
     * @param descDir
     * @author isea533
     */

    public  void unZipFiles(String zipPath,String descDir)throws IOException{
        File zipFile = new File(zipPath);
        zipFileLength=getZipTrueSize(zipPath);
        File pathFile = new File(descDir);
        String unZipPath = zipPath.substring(0, zipPath.lastIndexOf("."));
        String[] path = unZipPath.split("\\\\");
        String finalPath = descDir+path[path.length-1]+"\\";
        if(!pathFile.exists()){
            pathFile.mkdirs();
        }
        ZipFile zip = new ZipFile(zipFile, Charset.forName("GBK"));
        for(Enumeration entries = zip.entries(); entries.hasMoreElements();){
            ZipEntry entry = (ZipEntry)entries.nextElement();
            String zipEntryName = entry.getName();
            InputStream in = zip.getInputStream(entry);
            unZiptmpsize=in.available();
            unZippercent+=((unZiptmpsize*1.0/(zipFileLength*1.1))*100)/2;
           percentListener.updateEvent(new PercentEvent(this,(int)unZippercent));
            String outPath = (descDir+zipEntryName).replaceAll("\\*", "/");
            //判断路径是否存在,不存在则创建文件路径
            File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
            if(!file.exists()){
                file.mkdirs();
            }
            //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
            if(new File(outPath).isDirectory()){
                continue;
            }
            //输出文件路径信息
           // System.out.println(outPath+"===outpath");
            OutputStream out = new FileOutputStream(outPath);
            byte[] buf1 = new byte[1024];
            int len;
            while((len=in.read(buf1))>0){
                out.write(buf1,0,len);
            }
            in.close();
            out.close();
        }
        //E:\解压测试\apache-ftpserver-1.0.6
        File unZipFile = new File(finalPath);
        File descDirFile = new File(descDir);
        if (!unZipFile.exists()){
            System.out.println("压缩后的文件不存在！"+finalPath);
        }
        if(!descDirFile.exists()){
            System.out.println("目标文件不存在！"+descDir);
        }
        FileUtils.copyDirectory(unZipFile,descDirFile);
        if (unZipFile.length()<=0){
            FileUtils.deleteDirectory(unZipFile);
        }
    }

    public static void main(String[] args) throws Exception {

            String zipPath1 = "D:\\FTP-SuperMap\\iserver\\10.0\\supermap_iserver_1000_17508_3937_win64_deploy.zip";
            String descDir = "E:\\解压测试\\";
            try {
                ZipUtils zip = new ZipUtils();
                zip.unZipFiles(zipPath1,descDir);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    /**
     * 压缩文件
     *
     * @param sourceFilePath 源文件路径
     * @param zipFilePath    压缩后文件存储路径
     * @param zipFilename    压缩文件名
     */

    public  void compressToZip(String sourceFilePath, String zipFilePath, String zipFilename) throws Exception {

        File sourceFile = new File(sourceFilePath);
        compressallsize=getFileSize(sourceFile);
        if(compressallsize==0){
            for(int i=0;i<50;i++){
                compresspercent=i;
                percentListener.updateEvent(new PercentEvent(this,(int)compresspercent));
             //   System.out.println("模拟数据"+compresspercent);
            }
        }
        File zipPath = new File(zipFilePath);
        if (!zipPath.exists()) {
            zipPath.mkdirs();
        }
        File zipFile = new File(zipPath + File.separator + zipFilename);
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
            writeZip(sourceFile, "", zos);
            //文件压缩完成后，删除被压缩文件
            boolean flag = deleteDir(sourceFile);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e.getCause());
        }

    }
    /**
     * 遍历所有文件，压缩
     *
     * @param file       源文件目录
     * @param parentPath 压缩文件目录
     * @param zos        文件流
     */
    private   void writeZip(File file, String parentPath, ZipOutputStream zos) {
        double tmp;
        if (file.isDirectory()) {
            //目录
            parentPath += file.getName() + File.separator;
            File[] files = file.listFiles();
            for (File f : files) {
                writeZip(f, parentPath, zos);
            }
        } else {
            //文件
            try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
                //指定zip文件夹
                compresstempsize=bis.available();
                tmp= ((compresstempsize*1.0/compressallsize)*100)/2;
                compresspercent+=tmp;
                //System.out.println("压缩=="+compresspercent);
                if(compressallsize!=0){
                percentListener.updateEvent(new PercentEvent(this,(int)compresspercent));
                }
                ZipEntry zipEntry = new ZipEntry(parentPath + file.getName());
                zos.putNextEntry(zipEntry);
                int len;
                byte[] buffer = new byte[1024 * 10];
                while ((len = bis.read(buffer, 0, buffer.length)) != -1) {
                    zos.write(buffer, 0, len);
                    zos.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage(), e.getCause());
            }
        }
    }

    /**
     * 删除文件夹
     *
     * @param dir
     * @return
     */
    private  boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        //删除空文件夹
        return dir.delete();
    }

    /**
     * 获取一个文件夹下面的所有文件的大小
     * @param f
     * @return
     * @throws Exception
     */
    private long getFileSize(File f)throws  Exception{
        long  size =  0 ;
        if(f.isDirectory()){
            File flist[] = f.listFiles();
            for  ( int  i =  0 ; i < flist.length; i++){
                if  (flist[i].isDirectory()){
                    size = size + getFileSize(flist[i]);
                }else{
                    size = size + flist[i].length();
                }
            }
        }else{
            size = size + f.length();
        }
        return  size;
    }

    /**
     * 获取zip解压后的文件大小
     * @param filePath
     * @return
     */
    private long getZipTrueSize(String filePath) {
            long size = 0;
                ZipFile f;
            try {
                    f = new ZipFile(filePath,Charset.forName("GBK"));
                    Enumeration<? extends ZipEntry> en = f.entries();
                       while (en.hasMoreElements()) {
                      size += en.nextElement().getSize();
                   }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return size;
            }
}
