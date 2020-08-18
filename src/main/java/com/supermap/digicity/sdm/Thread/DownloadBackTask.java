/**
 * Project Name: sdm-upgradetools
 * File Name: BackgroundTask
 * Package Name: com.supermap.digicity.sdm.Thread
 * Date: 2020/4/30 15:58
 * Copyright (c) 2020,All Rights Reserved.
 */
package com.supermap.digicity.sdm.Thread;
import com.supermap.digicity.sdm.Event.PercentEvent;
import com.supermap.digicity.sdm.Thread.interfaces.IBackTask;
import com.supermap.digicity.sdm.model.PocessInfo;
import com.supermap.digicity.sdm.model.ProduceInfo;
import com.supermap.digicity.sdm.tools.dialogscommons.UpdatePocessTableModel;
import com.supermap.digicity.sdm.utils.*;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @Author: zhangjun
 * @Description: 自动更新下载线程
 * @Date: Create in 15:58 2020/4/30 
 */
public class DownloadBackTask implements IBackTask {
    UpdatePocessTableModel model;
    ProduceInfo produceInfo;
    PocessInfo pocessInfo;
    String realdownloadpath;


    public DownloadBackTask(UpdatePocessTableModel model, ProduceInfo info, PocessInfo pocessInfo){
        this.produceInfo=info;
        this.pocessInfo=pocessInfo;
        this.model=model;

    }
   @Override
    public int addDownWork() {
       pocessInfo.setRowKey(model.getRowCount());
        Object[] obj = {"", produceInfo.getProduceName(), produceInfo.getDownloadProcessVersion(),pocessInfo.getPocessStatue(),0};
        model.addRow(obj);
       return pocessInfo.getRowKey();
    }

    private String getdownRemotePath(String produceName,String produceVersion){
        String remotePath = "/"+produceName+"/"+produceVersion+"/";
        return remotePath;
    }
    @Override
    public Object run() throws Exception {
    /**
    * 1.更新方法分为以下几步：
    *   *  1.压缩原文件。命名加上old+redom随机  使用选中路径 并且命名加上时间值
     *  *  2.下载文件 下载路径改成上一级路径
    *  *  3.下载的新产品进行解压缩
    */
        addDownWork();
        System.out.println("压缩线程--"+produceInfo.getProduceName()+"正在执行");
        //压缩
        try {
            compression();
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("下载线程--"+produceInfo.getProduceName()+"正在执行");
        //下载
        try {
            download();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("解压线程--"+produceInfo.getProduceName()+"正在执行");
        //解压
        try {
            Decompression();
        }catch (Exception e){
            e.printStackTrace();
        }
        model.setValueAt("更新完成",pocessInfo.getRowKey(),3);
        model.setValueAt(100,pocessInfo.getRowKey(),4);
        return null;
    }

    @Override
    public void updateEvent(PercentEvent event) {
        model.setValueAt(event.getPercent(),pocessInfo.getRowKey(),4);
    }

    /**
     * 下载使用的是上一级路径
     * @throws IOException
     */
    private void  download() throws IOException {
        MultiTheradDownLoad ftp =new MultiTheradDownLoad(produceInfo);
        ftp.addPercentListener(this);
        ftp.downloadPart();
        System.out.println("下载线程--"+produceInfo.getProduceName()+"--完成");
    }

    /**
     * 压缩
     */
    private void  compression() throws Exception {
        Date date = new Date();
        String datesub=String.valueOf(date.getYear()+1900)+"-"+String.valueOf(date.getMonth()+1)+"-"+String.valueOf(date.getDate())+"-"+Long.toString(date.getTime());
        ZipUtils zip= new ZipUtils();
        zip.addPercentListener(this);
        File file = new File(produceInfo.getDownloadProduceSetupLocal());
        //实际下载路径使用的是该路径的上一级路径
        realdownloadpath= file.getParent();
        //压缩原文件路径
        String sourcePath = produceInfo.getDownloadProduceSetupLocal();
        //压缩完成之后安装包的存放路径
        String zipPath = realdownloadpath;
        String zipName = datesub+".zip";
        zip.compressToZip(sourcePath,zipPath,zipName);
    }

    /**
     * 解压
     * @throws IOException
     */
    private void Decompression() throws IOException {
        ZipUtils zip= new ZipUtils();
        zip.addPercentListener(this);
        String zippath=produceInfo.getDownloadProduceSetupLocal()+"\\";
        zip.unZipFiles(getZipPath(),zippath);
    }

    /**
     * 获取下载到的压缩包的路径
     * @return
     * @throws IOException
     */
    private String getZipPath() throws IOException {
      String productname=  HttpClientUtils.getNewProductName(produceInfo.getProduceName(),produceInfo.getDownloadProcessVersion());
        File file = new File(produceInfo.getDownloadProduceSetupLocal());
        //实际下载路径使用的是该路径的上一级路径
        realdownloadpath= file.getParent();
      String Zippath=realdownloadpath+"\\"+productname;
      return Zippath;
    }
}
