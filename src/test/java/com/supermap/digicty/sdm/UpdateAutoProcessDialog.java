/**
 * Project Name: sdm-upgradetools
 * File Name: UpdateAutoProcessDialog
 * Package Name: com.supermap.digicity.sdm.tools.dialogs
 * Date: 2020/5/7 10:16
 * Copyright (c) 2020,All Rights Reserved.
 */
package com.supermap.digicty.sdm;

import com.supermap.digicity.sdm.tools.dialogscommons.ToolsBaseDialog;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

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
    private JLabel setuplocal;
    private JProgressBar updateprogressbar;

    private JFileChooser tempfileChooser;
    private JScrollPane productinfoscroll;
    private String productname;
    private String productversion;
    private String setuplocalpath;
    ImageIcon image;
    JLabel labelbackgroud;

    public UpdateAutoProcessDialog() {
        init();
    }

    @Override
    protected void initCommpents() {
        mainpanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                ImageIcon icon = new ImageIcon(this.getClass().getClassLoader().getResource("allback.png"));
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, icon.getIconWidth(),
                        icon.getIconHeight(), icon.getImageObserver());
                this.setSize(icon.getIconWidth(), icon.getIconHeight());
            }
        };
        ImageIcon updatebuttonimage = new ImageIcon(this.getClass().getClassLoader().getResource("updatebutton1.png"));
        ImageIcon canclebuttonimage = new ImageIcon(this.getClass().getClassLoader().getResource("cancelbutton1.png"));
        updatebutton = new JButton(updatebuttonimage);
        updatebutton.setFocusPainted(false);
        updatebutton.setBorderPainted(false);
        canclebutton = new JButton(canclebuttonimage);
        ImageIcon icon = new ImageIcon(this.getClass().getClassLoader().getResource("areaback.png"));
        productinfoArea = new JTextArea(10, 3) {
            Image image = icon.getImage();
            {
               setOpaque(false);
            }
            @Override
            public void paint(Graphics g) {
                g.drawImage(image, 0, 0, icon.getIconWidth(),
                        icon.getIconHeight(), icon.getImageObserver());
                super.paint(g);
            }
            @Override
            public void setSelectedTextColor(Color c){
                Color colorStyle = Color.white;
                super.setSelectedTextColor(colorStyle);
            }
            @Override
            public void setCaretColor(Color c){
                Color colorStyle = Color.blue;
                super.setCaretColor(colorStyle);
            }
        };
        productinfoArea.setBorder(BorderFactory.createEmptyBorder());
        productinfoArea.setFont(new Font("宋体",Font.PLAIN,16));
        message = new JLabel();
        updatemessage = new JTextField("wjflsjflfdjladjf");

        //updatemessage.setOpaque(false);
        setuplocal = new JLabel();
        productinfoArea.setLineWrap(true);//激活自动换行功能
        productinfoArea.setOpaque(false);
        productinfoArea.setWrapStyleWord(true);
        productinfoscroll = new JScrollPane(productinfoArea);
        //productinfoscroll.setOpaque(false);
        updateprogressbar = new JProgressBar();
        tempfileChooser = new JFileChooser();
        labelbackgroud = new JLabel();
        JTextPane editorPane=new JTextPane();
        //JColorChooser colorChooser=new JColorChooser();
        Color color=Color.white;
        Document document= editorPane.getDocument();
        StyleContext sc   =   StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);
        Font font=new Font("隶书",Font.BOLD,30);
        aset=sc.addAttribute(aset, StyleConstants.Family, font.getFamily());
        aset=sc.addAttribute(aset, StyleConstants.FontSize, 30);
    int start= editorPane.getSelectionStart();
    int end=editorPane.getSelectionEnd();
        String str= null;
        try {
            str = document.getText(start,end-start);
            document.remove(start, end-start);

        //重新插入字符串，并按新设置的样式进行插入
            document.insertString(start, str, aset);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
//由于没找到直接设置所选字的方法，只有先移除原来的字符串    


    }


    @Override
    protected void addFrmElement() {

        mainpanel.setLayout(null);
        // 设置titleLebel组件的位置
        productinfoscroll.setBounds(19, 42, 620, 250);
        productinfoscroll.setBorder(BorderFactory.createEmptyBorder());
        productinfoscroll.setFont(new Font("宋体", Font.BOLD, 12));
        updatemessage.setBounds(20, 300, 250, 20);
        updatemessage.setBorder(BorderFactory.createEmptyBorder());
        updateprogressbar.setBounds(20, 327, 630, 20);
        updatebutton.setBounds(390, 358, 100, 30);
        canclebutton.setBounds(540, 358, 100, 30);
        updatebutton.setBorder(BorderFactory.createRaisedBevelBorder());

        //updateprogressbar.setFont(new Font("宋体", Font.BOLD, 12));
        mainpanel.add(updatebutton);
        mainpanel.add(canclebutton);
        mainpanel.add(productinfoscroll);
        mainpanel.add(updateprogressbar);
        mainpanel.add(updatemessage);
        updatemessage.setVisible(true);
        updatemessage.setOpaque(false);
        updateprogressbar.setVisible(true);
        updateprogressbar.setStringPainted(true);
        this.add(mainpanel);
    }

    @Override
    protected void addListener() {

        productinfoArea.append("功能" + "\n");
        productinfoArea.append("1 数据管理系统（DM）优化启动、加载数据目录等功能" );
        productinfoArea.append("2 数据管理系统（DM）优化启动、加载数据目录等功能" + "\n");
        productinfoArea.append("3 数据管理系统（DM）优化启动、加载数据目录等功能" );
        productinfoArea.append("4 数据管理系统（DM）优化启动、加载数据目录等功能" + "\n");
        productinfoArea.append("\n");
        productinfoArea.append("功能" + "\n");
        productinfoArea.append("1 数据管理系统（DM）优化启动、加载数据目录等功能" );
        productinfoArea.append("2 数据管理系统（DM）优化启动、加载数据目录等功能" + "\n");
        productinfoArea.append("3 数据管理系统（DM）优化启动、加载数据目录等功能" );
        productinfoArea.append("4 数据管理系统（DM）优化启动、加载数据目录等功能" + "\n");  productinfoArea.append("功能" + "\n");
        productinfoArea.append("1 数据管理系统（DM）优化启动、加载数据目录等功能" );
        productinfoArea.append("2 数据管理系统（DM）优化启动、加载数据目录等功能" + "\n");
        productinfoArea.append("3 数据管理系统（DM）优化启动、加载数据目录等功能" );
        productinfoArea.append("4 数据管理系统（DM）优化启动、加载数据目录等功能" + "\n");

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


    public static void main(String[] args) {
        UpdateAutoProcessDialog aa = new UpdateAutoProcessDialog();
    }

}
