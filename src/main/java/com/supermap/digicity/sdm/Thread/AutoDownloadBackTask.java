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
import com.supermap.digicity.sdm.model.ProduceInfo;
import com.supermap.digicity.sdm.utils.CmdExec;
import com.supermap.digicity.sdm.utils.HttpClientUtils;
import com.supermap.digicity.sdm.utils.MultiTheradDownLoad;
import com.supermap.digicity.sdm.utils.ZipUtils;
import com.supermap.digicity.sdm.utils.loadxml.LoadProductStartupUtils;
import org.dom4j.DocumentException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @Author: zhangjun
 * @Description: 自动下载线程
 * @Date: Create in 15:58 2020/4/30 
 */
public class AutoDownloadBackTask implements IBackTask {

    ProduceInfo produceInfo;
    String realdownloadpath;
   JProgressBar updateprogressbar;
    JTextField updatemessage;
    String  filepath;
    String  pidName;
    CmdExec cmdExec;
    {
        try {
            pidName = LoadProductStartupUtils.getProductStartup().get("pidName");
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        try {
            filepath = LoadProductStartupUtils.getProductStartup().get("filepath");
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public AutoDownloadBackTask(JTextField updatemessage,JProgressBar updateprogressbar,ProduceInfo  info){
        this.updatemessage=updatemessage;
        this.produceInfo=info;
        this.updateprogressbar=updateprogressbar;
        cmdExec = new CmdExec();
    }
    public AutoDownloadBackTask(ProduceInfo info){
        this.produceInfo=info;
    }


    @Override
    public int addDownWork() {
        return 0;
    }

    @Override
    public Object run() throws Exception {

        boolean downloadstatue=true;
        boolean compressionstatue =true;
        boolean  Decompressionstatue=true;
        try {
                updatemessage.setBorder(new EmptyBorder(0,0,0,0));
                updatemessage.setText("正在下载...");
                download();
            int n = JOptionPane.showConfirmDialog(null, "更新包下载完成，是否立即安装？", "更新包下载完成",JOptionPane.YES_NO_OPTION);
            if(n==0){
                //压缩步骤
                try {
                    updatemessage.setBorder(new EmptyBorder(0,0,0,0));
                    updatemessage.setText("正在备份历史版本...");
                  compression();
                }catch (Exception e){
                    e.printStackTrace();
                    compressionstatue=false;
                    int n1 = JOptionPane.showConfirmDialog(null, "文件夹或文件被占用,更新失败！点击确定重启原系统", "文件夹或文件被占用",JOptionPane.YES_NO_OPTION);
                    if(n1==0){
                        restartproduct();
                    }else{
                        System.exit(0);
                    }
                }
                //解压步骤
                try {
                    if(compressionstatue&&downloadstatue){
                        updatemessage.setBorder(new EmptyBorder(0,0,0,0));
                        updatemessage.setText("正在解压新产品包...");
                        Decompression();
                        updatemessage.setText("更新完成，等待启动中。如无法自动启动，还请手动启动..");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Decompressionstatue=false;
                    JOptionPane.showMessageDialog(null, "自动解压产品包失败，还请自行解压启动", "解压失败",JOptionPane.WARNING_MESSAGE);
                }

            }else{
                System.exit(0);
            }
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "下载失败", "下载失败",JOptionPane.WARNING_MESSAGE);
            downloadstatue=false;
            restartproduct();
        }
        updateprogressbar.setValue(100);
        //弹框 选择是否重启
        if(compressionstatue&&downloadstatue&&Decompressionstatue){
        int n = JOptionPane.showConfirmDialog(null, "更新完成，是否重启系统?", "更新完成",JOptionPane.YES_NO_OPTION);
        if(n==0){
            try {
                 cmdExec.startAppWhenUpdateDone(filepath,pidName);
                 System.exit(0);
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, "启动失败", "启动失败",JOptionPane.WARNING_MESSAGE);
            }
        }else{
            System.exit(0);
        }
        }

        return null;
    }

    @Override
    public void updateEvent(PercentEvent event) {
        updateprogressbar.setValue(event.getPercent());
       // System.out.println(event.getPercent());
    }

    /**
     * 下载使用的是上一级路径
     * @throws IOException
     */
    private void  download() throws IOException {
        MultiTheradDownLoad ftp =new MultiTheradDownLoad(produceInfo);
        ftp.addPercentListener(this);
        ftp.downloadPart();
        updateprogressbar.setValue(100);
    }

    /**
     * 压缩
     */
    private void  compression() throws Exception {
        Date date = new Date();
        String datesub=String.valueOf(date.getYear()+1900)+"-"+String.valueOf(date.getMonth()+1)+"-"+String.valueOf(date.getDate())+"-"+Long.toString(date.getTime());;
        ZipUtils zip= new ZipUtils();
        zip.addPercentListener(this);
        File file = new File(produceInfo.getDownloadProduceSetupLocal());
        //实际下载路径使用的是该路径的上一级路径
        realdownloadpath= file.getParent();
        //压缩原文件路径
        String sourcePath = produceInfo.getDownloadProduceSetupLocal();
        //压缩完成之后安装包的存放路径
        String zipPath = realdownloadpath;
        String zipName =datesub+".zip";
        System.out.println("压缩后存储路径为=="+realdownloadpath);
        System.out.println("压缩的目录为=="+sourcePath);
        System.out.println("压缩后文件名为=="+zipName);
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
        System.out.println("压缩后存放的路径=="+zippath);
        System.out.println("压缩包的路径"+getZipPath());
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
   private  void restartproduct(){
       JOptionPane.showMessageDialog(null, "更新出现异常，正在启动原产品", "更新失败",JOptionPane.WARNING_MESSAGE);
       CmdExec.startApp();
   }
}
