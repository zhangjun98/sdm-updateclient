/**
 * Project Name: sdm-upgradetools
 * File Name: UpdateProcessDialog
 * Package Name: com.supermap.digicity.sdm.tools.dialogs
 * Date: 2020/4/29 11:21
 * Copyright (c) 2020,All Rights Reserved.
 */
package com.supermap.digicity.sdm.tools.dialogs;

import com.supermap.digicity.sdm.Thread.DownloadBackTask;
import com.supermap.digicity.sdm.Thread.UpdatePocessSwingWorker;
import com.supermap.digicity.sdm.model.PocessInfo;
import com.supermap.digicity.sdm.model.ProduceInfo;
import com.supermap.digicity.sdm.tools.dialogscommons.ToolsBaseDialog;
import com.supermap.digicity.sdm.tools.dialogscommons.UpdatePocessTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @Author: zhangjun
 * @Description: 文件下载与解压缩的界面
 * @Date: Create in 11:21 2020/4/29 
 */
public class UpdateProcessDialog  extends ToolsBaseDialog {

    private List<ProduceInfo> downloadPrductInfo;
    private UpdatePocessTableModel defaultTableModel;
    private JTable dsTable;
    private JScrollPane jspDs;
    private JPanel mainpanel;
    private JLabel labeltype;
   public UpdateProcessDialog (List<ProduceInfo> downloadPrductInfo){
        super.init();
        this.downloadPrductInfo=downloadPrductInfo;
        System.out.println(this.downloadPrductInfo);
        addInfosToTable(downloadPrductInfo);
    }

    @Override
    protected void initCommpents() {
        mainpanel=new JPanel();
        final String[] columnNames = new String[]{
                "序号", "产品名称", "版本", "任务状态", "任务信息"};
        this.setTitle("更新任务");
        setSize(new Dimension(750, 450));
        defaultTableModel = new UpdatePocessTableModel(new String[][]{}, columnNames) ;

        dsTable = new JTable();
        dsTable.setModel(defaultTableModel);
        dsTable.setPreferredScrollableViewportSize(new Dimension(700, 350));
        labeltype = new JLabel("更新任务");
        jspDs = new JScrollPane(dsTable);
    }


    /**
     * 第一次创建窗体时创建下载任务
     * @param downloadPrductInfo
     */
    public void addInfosToTable(List<ProduceInfo> downloadPrductInfo){
       // "序号", "产品名称", "版本", "任务状态", "任务信息"
        for(int i=0;i<downloadPrductInfo.size();i++) {
            createDownLoadWork(downloadPrductInfo.get(i));
        }
    }
    public  void  createDownLoadWork(ProduceInfo info){
        //SwingWorker的process可以定义约束属性。更改这些属性将触发事件，并从事件调度线程上引起事件处理方法的调用。
        //SwingWorker的done方法，在后台任务完成时自动的在事件调度线程上被调用。
        PocessInfo pocessInfo = new PocessInfo();
        pocessInfo.setPocessStatue("更新中");
        DownloadBackTask task =new DownloadBackTask(defaultTableModel,info,pocessInfo);
        UpdatePocessSwingWorker worker  =  new UpdatePocessSwingWorker(task);
        worker.execute();

    }
    @Override
    protected void addFrmElement() {
        mainpanel.add(labeltype);
        mainpanel.add(jspDs);
        this.add(mainpanel);
    }

    @Override
    protected void addListener() {

    }
    @Override
    protected void setFrameSelf() {
        mainpanel.setLayout(null);
        // 设置titleLebel组件的位置
        labeltype.setBounds(18, 0, 90, 15);
        labeltype.setFont(new Font("宋体", Font.BOLD, 12));
        jspDs.setBounds(18, 18, 500, 530);
        jspDs.setFont(new Font("", Font.BOLD, 18));
        this.setBounds(600, 270, 540, 610);
        dsTable.setRowHeight(30);
        dsTable.getColumnModel().getColumn(0).setPreferredWidth(30);
        dsTable.getColumnModel().getColumn(1).setPreferredWidth(140);
        dsTable.getColumnModel().getColumn(2).setPreferredWidth(95);
        dsTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        dsTable.getColumnModel().getColumn(4).setPreferredWidth(155);
        setTableCellRendererAndEditor();
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        List<ProduceInfo> downloadPrductInfo =new ArrayList<>();
        UpdateProcessDialog aa= new UpdateProcessDialog(downloadPrductInfo);
    }
    @Override
    protected void unregistAction() {

    }



    public  void setTableCellRendererAndEditor () {
        try {
            ProgressRenderer tcr = new ProgressRenderer();
            dsTable.getColumnModel().getColumn(4).setCellRenderer(tcr);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    class ProgressRenderer extends DefaultTableCellRenderer {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        private final JProgressBar b = new JProgressBar(0, 100);

        public ProgressRenderer() {
            super();
            setOpaque(true);
            b.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
            b.setStringPainted(true);
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            int i = (int) value;
            String text = "更新完成";
            if (i < 0) {
                //删除
                text = "取消完毕";
            } else if (i < 100) {
                b.setValue(i);
                return b;
            }
            super.getTableCellRendererComponent(table,text,isSelected, hasFocus,
                    row,column);
            return this;
        }
    }

}
