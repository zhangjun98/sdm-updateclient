/**
 * Project Name: sdm-upgradetools
 * File Name: Mytable
 * Package Name: com.supermap.digicity.sdm.tools.dialogs
 * Date: 2020/4/28 18:02
 * Copyright (c) 2020,All Rights Reserved.
 */
package com.supermap.digicty.sdm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

/**
 * @Author: zhangjun
 * @Description:
 * @Date: Create in 18:02 2020/4/28 
 */
public class MyTable extends JTable {
    /**
     * 序列化
     */
    private static final long serialVersionUID = 1L;
    private JCheckBox checkBox;
    public  void  aaaa() {
        MyTable table = new MyTable();
        checkBox =new JCheckBox();
        final String[] columnNames = new String[]{
                "","序号"};
        DefaultTableModel defaultTableModel = new DefaultTableModel(new String[][]{}, columnNames);
        String[] values = new String[] { "5", "2", "3" };//这是下拉框的元素 由于是下拉框，这里必须是string数组传入
        String[] value1 = new String[] { "3", "2", "1" };
        String[] value2 = new String[] { "7", "8", "1" };
        table.setModel(defaultTableModel);
        defaultTableModel.addRow(new Object[]{true,values});//将下拉框元素传入table的tablemodel中
        defaultTableModel.addRow(new Object[]{false,value1});
        defaultTableModel.addRow(new Object[]{false,value2});
        table.getModel().getColumnName(1);

        table.getColumnModel().getColumn(0).setCellEditor(new CheckBoxCellEditor());
        table.getColumnModel().getColumn(0).setCellRenderer( new CWCheckBoxRenderer());
        table.getColumnModel().getColumn(1).setCellEditor(new MyComboBoxEditor());
        table.getColumnModel().getColumn(1).setCellRenderer( new comboxRenderer());
        JScrollPane jp=new JScrollPane(table);
        jp.setViewportView(table);
        jp.setSize(400,300);
        JFrame jf=new JFrame();
        jf.getContentPane().add(jp);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(400,300);
        jf.setVisible(true);
    }

    class MyComboBoxEditor extends AbstractCellEditor implements TableCellEditor {
        JComboBox box;
        Object Gvalue;
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            String[] values = (String[])value;
            Gvalue=value;
            box =new JComboBox(values);
            return box;
        }
            @Override
            public Object getCellEditorValue() {
                ArrayList<String> StringList=new ArrayList<>();
                StringList.add((String)box.getSelectedItem());
                String [] args =(String[]) Gvalue;
                for(int i=0;i<args.length;i++)
                {
                    if(!args[i].equals(box.getSelectedItem())){
                        StringList.add(args[i]);
                    }
                }
                String  []  strings= StringList.toArray(new  String[args.length]);
                return strings;
        }
    }
    class comboxRenderer extends JComboBox implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            String[] values = (String[])value ;
            JComboBox box=new JComboBox(values);
            return box;
        }
    }
    public static void main(String[] args) {
        MyTable table = new MyTable();
        table.aaaa();
    }
    class CheckBoxCellEditor extends AbstractCellEditor implements TableCellEditor {

        private static final long serialVersionUID = 1L;

        public CheckBoxCellEditor() {

            checkBox.setHorizontalAlignment(SwingConstants.CENTER);
        }

        @Override
        public Object getCellEditorValue() {
            return Boolean.valueOf(checkBox.isSelected());
        }

        @Override
        public Component getTableCellEditorComponent(
                JTable  table,
                Object  value,
                boolean isSelected,
                int     row,
                int     column) {
            checkBox.setSelected(((Boolean) value).booleanValue());

            return checkBox;

        }
    }
    class CWCheckBoxRenderer extends JCheckBox implements TableCellRenderer {

        private static final long serialVersionUID = 1L;


        public CWCheckBoxRenderer() {
            super();
            setOpaque(true);
            setHorizontalAlignment(SwingConstants.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column) {
            if (value instanceof Boolean) {
                setSelected(((Boolean) value).booleanValue());
                setForeground(table.getForeground());
                setBackground(table.getBackground());

            }

            return this;
        }
    }
    }
