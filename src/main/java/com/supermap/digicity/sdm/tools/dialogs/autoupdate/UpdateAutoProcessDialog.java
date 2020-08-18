/**
 * Project Name: sdm-upgradetools
 * File Name: UpdateAutoProcessDialog
 * Package Name: com.supermap.digicity.sdm.tools.dialogs
 * Date: 2020/5/7 10:16
 * Copyright (c) 2020,All Rights Reserved.
 */
package com.supermap.digicity.sdm.tools.dialogs.autoupdate;

import com.supermap.digicity.sdm.Thread.AutoDownloadBackTask;
import com.supermap.digicity.sdm.Thread.UpdatePocessSwingWorker;
import com.supermap.digicity.sdm.model.ProduceInfo;
import com.supermap.digicity.sdm.model.VersionInfo;
import com.supermap.digicity.sdm.tools.dialogscommons.GridBagConstraintsHelper;
import com.supermap.digicity.sdm.tools.dialogscommons.ToolsBaseDialog;
import com.supermap.digicity.sdm.utils.CmdExec;
import com.supermap.digicity.sdm.utils.HttpClientUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @Author: zhangjun
 * @Description: 自动更新的界面
 * @Date: Create in 10:16 2020/5/7 
 */
public class UpdateAutoProcessDialog extends ToolsBaseDialog {
    private JPanel mainpanel;
    private JButton updatebutton;
    private JButton canclebutton;
    private JTextArea productinfoArea;
    private JLabel message;
    private JTextField updatemessage;

    private JProgressBar updateprogressbar;
    private CommonButtonListener buttonListener;
    private JFileChooser tempfileChooser;
    private JScrollPane productinfoscroll;
    private String productname;
    private String productversion;
    private String setuplocalpath;


public UpdateAutoProcessDialog(String productname,String productversion,String setuplocalpath){
        this.productname=productname;
        this.productversion=productversion;
        this.setuplocalpath=setuplocalpath;
        init();
    }
    public UpdateAutoProcessDialog(){
        init();
    }

    @Override
    protected void initCommpents() {
        mainpanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                ImageIcon icon=new ImageIcon(this.getClass().getClassLoader().getResource("allback.png"));
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, icon.getIconWidth(),
                        icon.getIconHeight(), icon.getImageObserver());
                this.setSize(icon.getIconWidth(), icon.getIconHeight());
            }
        };
        ImageIcon updatebuttonimage=new ImageIcon(this.getClass().getClassLoader().getResource("updatebutton1.png"));
        ImageIcon canclebuttonimage=new ImageIcon(this.getClass().getClassLoader().getResource("cancelbutton1.png"));
        updatebutton = new JButton(updatebuttonimage);
        canclebutton = new JButton(canclebuttonimage);
        productinfoArea =new JTextArea(10,3)/*{
            @Override
            protected void paintComponent(Graphics g){
                ImageIcon icon=new ImageIcon(this.getClass().getClassLoader().getResource("areaback.png"));;
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, icon.getIconWidth(),
                        icon.getIconHeight(), icon.getImageObserver());
                this.setSize(icon.getIconWidth(), icon.getIconHeight());
            }

        }*/;
       //message=new JLabel("您的产品有新的版本");
        updatemessage=new JTextField();
        buttonListener =new CommonButtonListener();
        productinfoArea.setLineWrap(true);//激活自动换行功能
       // productinfoArea.setOpaque(false);
        productinfoArea.setBorder(BorderFactory.createEtchedBorder());
        productinfoArea.setWrapStyleWord(true);
        productinfoscroll = new JScrollPane(productinfoArea);
        updateprogressbar =new JProgressBar();
        //productinfoscroll.setOpaque(false);
        tempfileChooser = new JFileChooser();
        //传新版本信息
       setproductinfoArea(productname,productversion);
    }

    @Override
    protected void addFrmElement() {
        mainpanel.setLayout(null);
        // 设置titleLebel组件的位置
        productinfoscroll.setBounds(19, 42, 620, 250);
        productinfoscroll.setFont(new Font("宋体", Font.BOLD, 12));
        updatemessage.setBounds(20,300,250,20);
        updateprogressbar.setBounds(20, 327, 630, 20);
        updatebutton.setBounds(390,358,100,30);
        canclebutton.setBounds(540,358,100,30);
        updatebutton.setBorder(BorderFactory.createRaisedBevelBorder());
        //updateprogressbar.setFont(new Font("宋体", Font.BOLD, 12));
        mainpanel.add(updatebutton);
        mainpanel.add(canclebutton);
        mainpanel.add(productinfoscroll);
        mainpanel.add(updateprogressbar);
        mainpanel.add(updatemessage);
        updatemessage.setVisible(true);
        updatemessage.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        updatemessage.setOpaque(false);

        updateprogressbar.setVisible(true);
        updateprogressbar.setStringPainted(true);
        this.add(mainpanel);
    }

    @Override
    protected void addListener() {
    updatebutton.addActionListener(buttonListener);
    canclebutton.addActionListener(buttonListener);
    }

    @Override
    protected void setFrameSelf() {
        //设置为网格布局
        this.setTitle("产品更新");
        this.setVisible(true);
        // this.setUndecorated(false);
        // this.setResizable(false);
        this.setSize(680, 440);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    @Override
    protected void unregistAction() {

    }
    private void setproductinfoArea(String productname ,String productversion){
        VersionInfo info = HttpClientUtils.getOneProductInfo(productname,productversion);
        try {
            if(info!=null){
                if(info.getFeatures().size()>0){
                    productinfoArea.append("新特性"+"\n");
                for(int i=0;i<info.getFeatures().size();i++){
                    productinfoArea.append(info.getFeatures().get(i)+"\n");
                }
            }
                if(info.getEnhances().size()>0){
                    productinfoArea.append("\n"+"增强内容"+"\n");
                    for(int i=0;i<info.getEnhances().size();i++){
                        productinfoArea.append(info.getEnhances().get(i)+"\n");
                    }
                }
                if(info.getCompatibilitys().size()>0){
                    productinfoArea.append("\n"+"兼容性说明"+"\n");
                    for(int i=0;i<info.getCompatibilitys().size();i++){
                        productinfoArea.append(info.getCompatibilitys().get(i)+"\n");
                    }
                }
                if(info.getOthers().size()>0){
                    productinfoArea.append("\n"+"其他说明"+"\n");
                    for(int i=0;i<info.getOthers().size();i++){
                        productinfoArea.append(info.getOthers().get(i)+"\n");
                    }
                }
            }else{
                System.out.println("获取远程信息失败，可能是有产品 无版本之类的的原因  dialog 150行");
            }

            productinfoArea.setEditable(false);
        }catch (NullPointerException e){
            e.getMessage();
        }
    }

    private void btnbtnupdateClicked(){
        ProduceInfo info=new ProduceInfo();
        info.setProduceName(productname);
        info.setDownloadProcessVersion(productversion);
        info.setDownloadProduceSetupLocal(setuplocalpath);
        //添加一个线程
        AutoDownloadBackTask task =new AutoDownloadBackTask(updatemessage,updateprogressbar,info);
        UpdatePocessSwingWorker swingWorker =new UpdatePocessSwingWorker(task);
        swingWorker.execute();
        updatebutton.removeActionListener(buttonListener);
        updatebutton.setEnabled(false);
        updatemessage.setVisible(true);
        updateprogressbar.setVisible(true);
    }

    public static void main(String[] args) {
        UpdateAutoProcessDialog aa = new UpdateAutoProcessDialog();
    }
   private class CommonButtonListener implements ActionListener {
        CommonButtonListener() {}
        @Override
        public void actionPerformed(ActionEvent e){
            JComponent localJComponent = (JComponent)e.getSource();
            if (localJComponent == canclebutton)
            {
                unregistAction();
                System.exit(0);
            }
            else if(localJComponent==updatebutton)
            {
                btnbtnupdateClicked();
            }
        }
    }
}
