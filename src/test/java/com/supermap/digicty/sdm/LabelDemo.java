/**
 * Project Name: sdm-upgradetools
 * File Name: LabelDemo
 * Package Name: com.supermap.digicty.sdm
 * Date: 2020/4/29 16:14
 * Copyright (c) 2020,All Rights Reserved.
 */
package com.supermap.digicty.sdm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @Author: zhangjun
 * @Description:
 * @Date: Create in 16:14 2020/4/29 
 */
public class LabelDemo extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JLabel label2;

    public LabelDemo() {
        super(new GridLayout(2,1));
        JButton b1 = new JButton("click me");
        b1.addActionListener(this);
        label2 = new JLabel("Label");
        add(label2);
        add(b1);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("LabelDemo");
        ImageIcon imageIcon = new ImageIcon(LabelDemo.class.getResource(
                "/icon.png"));
        // 设置标题栏的图标为face.gif
        frame.setIconImage(imageIcon.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new LabelDemo());
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new Thread(new Runnable(){
            @Override
            public void run() {
                for(int i = 0 ; i < 10 ; i ++){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {}
                    final int x =i;
                    SwingUtilities.invokeLater(new Runnable(){
                        @Override
                        public void run() {
                            label2.setText(x + "");
                        }
                    });
                }
            }
        }).start();
    }
}

