package com.supermap.digicity.sdm.utils;

import com.supermap.digicity.sdm.utils.loadxml.LoadProductStartupUtils;
import org.dom4j.DocumentException;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Description 启动启动文件的工具
 * @Auther zhoujinqiao
 * @Data 2020/5/8 14:19
 **/
public class CmdExec {
    /**
     * filepath exe 安装目录绝对路径 如：E:/SERVER
     */
    public static String filepath;
    /**
     * pidName exe名字  如：Invoice
     */
    public static String pidName;

    static{
        try {
            filepath= LoadProductStartupUtils.getProductStartup().get("filepath");
            pidName=LoadProductStartupUtils.getProductStartup().get("pidName");
        } catch (DocumentException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "请将start.xml该文件放在指定目录下！", "启动失败",JOptionPane.WARNING_MESSAGE);
        }
    }
    public static String getFilepath() {
        return filepath;
    }

    public static void setFilepath(String filepath) {
        CmdExec.filepath = filepath;
    }

    public static String getPidName() {
        return pidName;
    }

    public static void setPidName(String pidName) {
        CmdExec.pidName = pidName;
    }

    /**
     * @Title: cmdKill
     * @Description: 杀死指定的程序，程序全名称如notepad.exe
     * @param @param programName
     * @param @throws IOException
     * @return void
     * @throws
     */
    public void cmdKill(String programName){
        Runtime rt = Runtime.getRuntime();
        Process proc = null;
        String cmdStr = "tasklist /nh /fo csv";
        try {
            proc = rt.exec(cmdStr);
            String line="";
            InputStreamReader isr = new InputStreamReader(proc.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                if(line.indexOf(programName)>-1){
                    Runtime.getRuntime().exec("taskkill /f /t /im "+programName+"");
                }
            }
            proc.waitFor();
            proc.destroy();
        } catch (Exception e1) {
            System.out.println(cmdStr+" 杀死进程执行失败! "+e1.getMessage());
        }
    }

    /**
     * @Title: cmdExec
     * @Description: 执行cmd命令
     * @param @param cmdStr
     * @return void
     * @throws
     */
    public void cmdExec(String cmd,String programName){
        Boolean programFlag =false;
        Desktop desktop = Desktop.getDesktop();
        Runtime rt = Runtime.getRuntime();
        Process proc = null;
        String cmdStr = "tasklist /nh /fo csv";
        try {
            proc = rt.exec(cmdStr);
            String line="";
            InputStreamReader isr = new InputStreamReader(proc.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                if(line.indexOf(programName)>-1){
                    programFlag = true;
                }
            }
            if(!programFlag){
                desktop.open(new File(cmd));
            }
            br.close();
            isr.close();
            proc.waitFor();
            proc.destroy();
        } catch (Exception e1) {
           e1.printStackTrace();
        }
    }

    /**
     * cmd执行.exe或者.sh ,.bat
     * @param
     * @param
     */
    public static void  startApp(){
        try {
            filepath= LoadProductStartupUtils.getProductStartup().get("filepath");
            pidName=LoadProductStartupUtils.getProductStartup().get("pidName");
            System.out.println("目录"+filepath);
            System.out.println("程序名称"+pidName);
            System.out.println("重新指定目录");
            System.out.println("我是新jar");
        } catch (DocumentException e){
            e.printStackTrace();
            System.out.println("重新指定异常");
        }

        BufferedReader reader=null;
        try {
            Runtime runtime = Runtime.getRuntime();
            String digicity ="cd /d "+filepath;
          if(pidName.endsWith(".bat")||pidName.endsWith(".sh")){
              runtime.exec("cmd /k  start"+filepath+"/"+pidName);
          }else{
              String url = (filepath+"/"+pidName).replaceAll("\\*", "/");
              System.out.println("请求执行--"+url);
            //  if(!runtime.exec(url).isAlive()){
                  System.out.println("正在执行--"+"cmd.exe /k start /b cmd"+digicity);
                  runtime.exec("cmd.exe /c start /b cmd"+digicity);
                  runtime.exec(filepath+"/"+pidName);
            //  }

          }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public  void  startAppWhenUpdateDone(String filepath,String pidName){
        BufferedReader reader=null;
        try {
            Runtime runtime = Runtime.getRuntime();
            String digicity ="cd /d "+filepath;
            if(pidName.endsWith(".bat")||pidName.endsWith(".sh")){
                runtime.exec("cmd /k  start"+filepath+"/"+pidName);
            }else{
                String url = (filepath+"/"+pidName).replaceAll("\\*", "/");
                System.out.println("请求执行--"+url);
                if(!runtime.exec(url).isAlive()){
                System.out.println("正在执行--"+"cmd.exe /k start /b cmd"+digicity);
                runtime.exec("cmd.exe /k start /b cmd"+digicity);
                runtime.exec(filepath+"/"+pidName);
                 }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        CmdExec.setFilepath("E:\\a整体功能测试\\DM");
        CmdExec.setPidName("SuperMap iDesktop .NET.exe");
        startApp();
    }

}
