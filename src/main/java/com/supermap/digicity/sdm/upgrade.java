package com.supermap.digicity.sdm;

import com.supermap.digicity.sdm.tools.dialogs.UpdateToolsDialog;
import com.supermap.digicity.sdm.tools.dialogs.autoupdate.AutoUpdateCheck;
import com.supermap.digicity.sdm.utils.CmdExec;
import com.supermap.digicity.sdm.utils.HttpClientUtils;
import com.supermap.digicity.sdm.utils.loadxml.LoadProductStartupUtils;
import com.supermap.digicity.sdm.utils.loadxml.LoadStartupInfoUtils;
import com.supermap.digicity.sdm.utils.loadxml.LoadVersionInfo;

import javax.swing.*;

/**
 * 主动执行的核心类
 */


public class upgrade {
    private static String productname="";
    private static String version="";
    private static String setuplocalpath;
    public static void main(String[] args) {
      /*  try {
            UIManager.setLookAndFeel(new SubstanceModerateLookAndFeel());
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
        } catch (Exception e) {
        }*/
        if(args.length > 0){
            //通过命令运行。
            //参数有产品名称 版本号
            for (int i = 0; i < args.length; i++) {
                //传入本地版本号与产品名称
                if (args[i].equals("-setuplocal")) {
                    setuplocalpath = args[i + 1];
                    continue;
                }
            }
            LoadVersionInfo info = new LoadVersionInfo(setuplocalpath);
            if(info!=null){
                try {
                    productname=info.getVersionInfo().getProductname();
                    version=info.getVersionInfo().getProductversion();
                    System.out.println("产品当前版本："+version);
                }catch (NullPointerException e){
                    e.getMessage();
                    JOptionPane.showMessageDialog(null, "缺失版本信息文件", "启动失败",JOptionPane.WARNING_MESSAGE);
                }
            }
            try {
                LoadProductStartupUtils.setLocalpath(setuplocalpath);
                if(productname!=""&&version!=""&&setuplocalpath!=null){
                    AutoUpdateCheck check= new AutoUpdateCheck();
                    check.checkProductAndVersion(productname,version,setuplocalpath);
                }else{
                    CmdExec.startApp();
                }
            }catch ( Exception e){

            }

        }else{
            //设置远程路径
            HttpClientUtils.setUri(LoadStartupInfoUtils.getRemoteInfo().getHTTPURL());
            UpdateToolsDialog frm = new UpdateToolsDialog();
        }
    }
}
