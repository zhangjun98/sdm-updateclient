/**
 * Project Name: sdm-upgradetools
 * File Name: UpdateAutoProcessDialog
 * Package Name: com.supermap.digicity.sdm.tools.dialogs
 * Date: 2020/5/7 10:16
 * Copyright (c) 2020,All Rights Reserved.
 */
package com.supermap.digicty.sdm.digicty;

import com.supermap.digicity.sdm.tools.dialogscommons.ToolsBaseDialog;

import javax.swing.*;
import javax.swing.text.*;
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
    private JTextPane productinfoArea;
    private JTextField updatemessage;
    private JProgressBar updateprogressbar;
    private JScrollPane productinfoscroll;


    private Box box = null; // 放输入组件的容器
    private JButton b_insert = null, b_remove = null; // 插入按钮;清除按钮;插入图片按钮
    private JTextField addText = null; // 文字输入框
    private JComboBox fontName = null, fontSize = null, fontStyle = null,
            fontColor = null, fontBackColor = null; // 字体名称;字号大小;文字样式;文字颜色;文字背景颜色
    private StyledDocument doc = null;
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
        productinfoArea = new JTextPane() {
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
        };
        productinfoArea.setBorder(BorderFactory.createEmptyBorder());
        productinfoArea.setFont(new Font("宋体",Font.PLAIN,16));
        updatemessage = new JTextField("产品正在下载中");
        productinfoscroll = new JScrollPane(productinfoArea);
        updateprogressbar = new JProgressBar();

        productinfoArea.setEditable(false);

        doc = productinfoArea.getStyledDocument(); // 获得JTextPane的Document

        addText = new JTextField(18);
        String[] str_name = { "宋体", "黑体", "Dialog", "Gulim" };
        String[] str_Size = { "12", "14", "16", "18", "30", "40" };
        String[] str_Style = { "常规", "斜体", "粗体", "粗斜体" };
        String[] str_Color = { "黑色", "红色", "蓝色", "黄色", "白色" };
        String[] str_BackColor = { "1.0F", "0.9F", "1.5F", "2.5F", "2.6F", "1.8F" };
        fontName = new JComboBox(str_name); // 字体名称
        fontSize = new JComboBox(str_Size); // 字号
        fontStyle = new JComboBox(str_Style); // 样式
        fontColor = new JComboBox(str_Color); // 颜色
        fontBackColor = new JComboBox(str_BackColor); // 背景颜色
        b_insert = new JButton("插入"); // 插入
        b_remove = new JButton("清空"); // 清除

        b_insert.addActionListener(new ActionListener() { // 插入文字的事件
            public void actionPerformed(ActionEvent e) {
                insert(getFontAttrib());
                addText.setText("");
            }
        });

        b_remove.addActionListener(new ActionListener() { // 清除事件
            public void actionPerformed(ActionEvent e) {
                productinfoArea.setText("");
            }
        });

        box = Box.createVerticalBox(); // 竖结构
        Box box_1 = Box.createHorizontalBox(); // 横结构
        Box box_2 = Box.createHorizontalBox(); // 横结构
        box.add(box_1);
        box.add(Box.createVerticalStrut(8)); // 两行的间距
        box.add(box_2);
        box.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8)); // 8个的边距
        // 开始将所需组件加入容器

        box_1.add(new JLabel("字体：")); // 加入标签
        box_1.add(fontName); // 加入组件
        box_1.add(Box.createHorizontalStrut(8)); // 间距
        box_1.add(new JLabel("样式："));
        box_1.add(fontStyle);
        box_1.add(Box.createHorizontalStrut(8));
        box_1.add(new JLabel("字号："));
        box_1.add(fontSize);
        box_1.add(Box.createHorizontalStrut(8));
        box_1.add(new JLabel("颜色："));
        box_1.add(fontColor);
        box_1.add(Box.createHorizontalStrut(8));
        box_1.add(new JLabel("行距："));
        box_1.add(fontBackColor);
        box_1.add(Box.createHorizontalStrut(8));

       // box_2.add(addText);
        box_2.add(Box.createHorizontalStrut(8));
        box_2.add(b_insert);
        box_2.add(Box.createHorizontalStrut(8));
        box_2.add(b_remove);
        this.getRootPane().setDefaultButton(b_insert); // 默认回车按钮

        this.getContentPane().add(box, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        addText.requestFocus();
       /* JTextPane editorPane=new JTextPane();
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
        }*/
        //由于没找到直接设置所选字的方法，只有先移除原来的字符串    
        JTextPane editor1 = new JTextPane();
        editor1.setSize(50, 50);
        SimpleAttributeSet a = new SimpleAttributeSet();
        StyleConstants.setLineSpacing(a, .9f); //此处设定行间距
        editor1.setParagraphAttributes(a, false);

    }
    private void insert(FontAttrib attrib) {
        try { // 插入文本
            productinfoArea.setText("");
            doc.insertString(doc.getLength(), hahah() + "\n",
                    attrib.getAttrSet());
            productinfoArea.setCaretPosition(0);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
    private FontAttrib getFontAttrib() {
        FontAttrib att = new FontAttrib();
        att.setText(addText.getText());
        att.setName((String) fontName.getSelectedItem());
        att.setSize(Integer.parseInt((String) fontSize.getSelectedItem()));
        String temp_style = (String) fontStyle.getSelectedItem();
        if (temp_style.equals("常规")) {
            att.setStyle(FontAttrib.GENERAL);
        } else if (temp_style.equals("粗体")) {
            att.setStyle(FontAttrib.BOLD);
        } else if (temp_style.equals("斜体")) {
            att.setStyle(FontAttrib.ITALIC);
        } else if (temp_style.equals("粗斜体")) {
            att.setStyle(FontAttrib.BOLD_ITALIC);
        }
        String temp_color = (String) fontColor.getSelectedItem();
        if (temp_color.equals("黑色")) {
            att.setColor(new Color(0, 0, 0));
        } else if (temp_color.equals("红色")) {
            att.setColor(new Color(255, 0, 0));
        } else if (temp_color.equals("蓝色")) {
            att.setColor(new Color(0, 0, 255));
        } else if (temp_color.equals("黄色")) {
            att.setColor(new Color(255, 255, 0));
        } else if (temp_color.equals("白色")) {
            att.setColor(Color.white);
        }
        String temp_backColor = (String) fontBackColor.getSelectedItem();
        att.setLine(Float.parseFloat(temp_backColor));
        return att;
    }

    @Override
    protected void addFrmElement() {

        mainpanel.setLayout(null);
        // 设置titleLebel组件的位置
        productinfoscroll.setBounds(19, 42, 620, 250);
        productinfoscroll.setBorder(BorderFactory.createEmptyBorder());
        productinfoscroll.setFont(new Font("宋体", Font.BOLD, 12));
        //updatemessage.setBounds(20, 300, 250, 20);
        //updatemessage.setBorder(BorderFactory.createEmptyBorder());
        // updateprogressbar.setBounds(20, 327, 630, 20);

        //updatebutton.setBounds(390, 358, 100, 30);
       // canclebutton.setBounds(540, 358, 100, 30);
        //updatebutton.setBorder(BorderFactory.createRaisedBevelBorder());
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


    }
  private String hahah(){
      String text="";
      text+="功能" + "\n";
      text+="2 数据管理系统（DM）优化启动、加载数据目录等功能" + "\n";
      text+="3 数据管理系统（DM）优化启动、加载数据目录等功能数据管理系统（DM）优化启动、加载数据目录等功能" ;
      text+="4 数据管理系统（DM）优化启动、加载数据目录等功能" + "\n";
      text+="\n";
      text+="功能" + "\n";
      text+="1 数据管理系统（DM）优化启动、加载数据目录等功能" ;
      text+="2 数据管理系统（DM）优化启动、加载数据目录等功能" + "\n";
      text+="3 数据管理系统（DM）优化启动、加载数据目录等功能" ;
      text+="4 数据管理系统（DM）优化启动、加载数据目录等功能" + "\n";
      text+="1 数据管理系统（DM）优化启动、加载数据目录等功能" ;
      text+="2 数据管理系统（DM）优化启动、加载数据目录等功能" + "\n";
      text+="3 数据管理系统（DM）优化启动、加载数据目录等功能" ;
      text+="4 数据管理系统（DM）优化启动、加载数据目录等功能" + "\n";
      return text;
  }

    public JTextField getAddText() {
        return addText;
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
        try { // 使用Windows的界面风格
            UIManager
                    .setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        UpdateAutoProcessDialog aa = new UpdateAutoProcessDialog();
    }

    private class FontAttrib {
        public static final int GENERAL = 0; // 常规
        public static final int BOLD = 1; // 粗体
        public static final int ITALIC = 2; // 斜体
        public static final int BOLD_ITALIC = 3; // 粗斜体
        private SimpleAttributeSet attrSet = null; // 属性集
        private String text = null, name = null; // 要输入的文本和字体名称
        private int style = 0, size = 0; // 样式和字号
        private Color color = null, backColor = null; // 文字颜色和背景颜色
        private float line=1.5F;

        public float getLine() {
            return line;
        }

        public void setLine(float line) {
            this.line = line;
        }

        public FontAttrib() {
        }
        public SimpleAttributeSet getAttrSet() {
            attrSet = new SimpleAttributeSet();
            if (name != null) {
                StyleConstants.setFontFamily(attrSet, name);
            }
            if (style == FontAttrib.GENERAL) {
                StyleConstants.setBold(attrSet, false);
                StyleConstants.setItalic(attrSet, false);
            } else if (style == FontAttrib.BOLD) {
                StyleConstants.setBold(attrSet, true);
                StyleConstants.setItalic(attrSet, false);
            } else if (style == FontAttrib.ITALIC) {
                StyleConstants.setBold(attrSet, false);
                StyleConstants.setItalic(attrSet, true);
            } else if (style == FontAttrib.BOLD_ITALIC) {
                StyleConstants.setBold(attrSet, true);
                StyleConstants.setItalic(attrSet, true);
            }
            StyleConstants.setFontSize(attrSet, size);
            if (color != null) {
                StyleConstants.setForeground(attrSet, color);
           }

                StyleConstants.setLineSpacing(attrSet, line);


            return attrSet;
        }

        public void setAttrSet(SimpleAttributeSet attrSet) {
            this.attrSet = attrSet;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public Color getBackColor() {
            return backColor;
        }

        public void setBackColor(Color backColor) {
            this.backColor = backColor;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getStyle() {
            return style;
        }

        public void setStyle(int style) {
            this.style = style;
        }
    }


}
