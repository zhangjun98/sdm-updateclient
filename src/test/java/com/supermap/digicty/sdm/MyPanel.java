/**
 * Project Name: sdm-upgradetools
 * File Name: MyPanel
 * Package Name: com.supermap.digicty.sdm
 * Date: 2020/4/30 13:59
 * Copyright (c) 2020,All Rights Reserved.
 */
package com.supermap.digicty.sdm;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Random;

/**
 * @Author: zhangjun
 * @Description:
 * @Date: Create in 13:59 2020/4/30 
 */
public  class  MyPanel  extends JPanel {
                /**
           *
           */
                private  static  final  long  serialVersionUID  =  1L;

                private  static  final  Color  evenColor  =  new  Color(250,  250,  250);

                private  final  MyTableModel  model  =  new  MyTableModel();

                private  final TableRowSorter<MyTableModel> sorter  =  new  TableRowSorter<MyTableModel>(
                                model);

                private  final  JTable  table;

                public  MyPanel()  {
                        super(new BorderLayout());
                        table  =  new  JTable(model)  {
                                /**
                           *
                           */
                                private  static  final  long  serialVersionUID  =  1L;

                                public  Component  prepareRenderer(
                                        TableCellRenderer tableCellRenderer, int  row, int  column)  {
                                        Component  component  =  super.prepareRenderer(tableCellRenderer,  row,
                                                        column);
                                        //背景色及字体设置
                                        if  (isRowSelected(row))  {
                                                component.setForeground(getSelectionForeground());
                                                component.setBackground(getSelectionBackground());
                                        }  else  {
                                                component.setForeground(getForeground());
                                                component.setBackground((row  %  2  ==  0)  ?  evenColor  :  table
                                                                .getBackground());
                                        }
                                        return  component;
                                }

                                public  JPopupMenu  getComponentPopupMenu()  {
                                        return  makePopup();
                                }
                        };
                        table.setRowSorter(sorter);
                        model.addTest(new  Test("进度条测试",  100),  null);

                        //  滚动条
                        JScrollPane  scrollPane  =  new  JScrollPane(table);
                        //  背景色
                        scrollPane.getViewport().setBackground(Color.black);
                        //  弹出菜单
                        table.setComponentPopupMenu(new  JPopupMenu());
                        //  是否始终大到足以填充封闭视口的高度
                        table.setFillsViewportHeight(true);
                        //  将单元格间距的高度和宽度设置为指定的Dimension
                        table.setIntercellSpacing(new  Dimension());
                        //  是否绘制单元格间的水平线
                        table.setShowHorizontalLines(true);
                        //  是否绘制单元格间的垂直线
                        table.setShowVerticalLines(false);
                        //  停止编辑时重新定义焦点，避免TableCellEditor丢失数据
                        table.putClientProperty("terminateEditOnFocusLost",  Boolean.TRUE);
                        //  表示JTable中列的所有属性，如宽度、大小可调整性、最小和最大宽度等。
                        TableColumn column  =  table.getColumnModel().getColumn(0);
                        column.setMaxWidth(60);
                        column.setMinWidth(60);
                        column.setResizable(false);
                        column  =  table.getColumnModel().getColumn(2);
                        //  绘制此列各值的TableCellRenderer
                        column.setCellRenderer(new  ProgressRenderer());

                        //  添加按钮
                        add(new  JButton(new  CreateNewAction("添加",  null)),  BorderLayout.SOUTH);
                        add(scrollPane,  BorderLayout.CENTER);
                        setPreferredSize(new  Dimension(320,  180));
                }

                class  CreateNewAction  extends  AbstractAction  {
                        /**
                   *
                   */
                        private  static  final  long  serialVersionUID  =  1L;

                        public  CreateNewAction(String  label,  Icon  icon)  {
                                super(label,  icon);
                        }

                        public  void  actionPerformed(ActionEvent  evt)  {
                                createNewActionPerformed(evt);
                        }
                }

                /**
           *  创建事件
           *  @param  evt
           */
                private  void  createNewActionPerformed(ActionEvent  evt)  {
                        final  int  key  =  model.getRowCount();
                        //在jdk1.6后，当一个Swing程序需要执行一个多线程任务时，可以通过javax.swing.SwingWorker实例进行实现。
                        //SwingWorker的process可以定义约束属性。更改这些属性将触发事件，并从事件调度线程上引起事件处理方法的调用。
                        //SwingWorker的done方法，在后台任务完成时自动的在事件调度线程上被调用。
                        SwingWorker<Integer,  Integer>  worker  =  new  SwingWorker<Integer,  Integer>()  {
                                //  随机sleep
                                private  int  sleepDummy  =  new Random().nextInt(100)  +  1;

                                //  最大任务数量
                                private  int  taskSize  =  200;

                                protected  Integer  doInBackground()  {
                                        int  current  =  0;
                                        while  (current  <  taskSize  &&  !isCancelled())  {
                                                current++;
                                                try  {
                                                        Thread.sleep(sleepDummy);
                                                }  catch  (InterruptedException  ie)  {
                                                        publish(-1);
                                                        break;
                                                }
                                                int a =(100  *  current  /  taskSize);
                                                publish(100  *  current  /  taskSize);
                                        }
                                        return  sleepDummy  *  taskSize;
                                }

                                /**
                           *  进行中处理
                           */
                                protected  void  process(java.util.List<Integer>  data)  {
                                        for  (Integer  value  :  data)  {
                                                //  把数据填入对应的行列
                                                model.setValueAt(value,  key,  2);
                                        }
                                        //  传送变更事件给指定行列
                                        model.fireTableCellUpdated(key,  2);
                                }

                                /**
                           *  完成后处理
                           */
                                protected  void  done()  {
                                }
                        };
                        model.addTest(new  Test("进度条测试",  0),  worker);
                        worker.execute();
                }

                class  CancelAction  extends  AbstractAction  {
                        /**
                   *
                   */
                        private  static  final  long  serialVersionUID  =  1L;

                        public  CancelAction(String  label,  Icon  icon)  {
                                super(label,  icon);
                        }

                        public  void  actionPerformed(ActionEvent  evt)  {
                                cancelActionPerformed(evt);
                        }
                }

                /**
           *  取消进度
           *  @param  evt
           */
                public  synchronized  void  cancelActionPerformed(ActionEvent evt)  {
                        int[]  selection  =  table.getSelectedRows();
                        if  (selection  ==  null  ||  selection.length  <=  0)
                                return;
                        for  (int  i  =  0;  i  <  selection.length;  i++)  {
                                int  midx  =  table.convertRowIndexToModel(selection[i]);
                                SwingWorker  worker  =  model.getSwingWorker(midx);
                                if  (worker  !=  null  &&  !worker.isDone())  {
                                        worker.cancel(true);
                                }
                                worker  =  null;
                        }
                        table.repaint();
                }

                /**
           *  取消下载进程
           *
           *  @author  chenpeng
           *
           */
                class  DeleteAction  extends  AbstractAction  {
                        /**
                   *
                   */
                        private  static  final  long  serialVersionUID  =  1L;

                        public  DeleteAction(String  label,  Icon  icon)  {
                                super(label,  icon);
                        }

                        public  void  actionPerformed(ActionEvent  evt)  {
                                deleteActionPerformed(evt);
                        }
                }

                private  final HashSet<Integer> set  =  new  HashSet<Integer>();

                public  synchronized  void  deleteActionPerformed(ActionEvent  evt)  {
                        int[]  selection  =  table.getSelectedRows();
                        if  (selection  ==  null  ||  selection.length  <=  0)
                                return;
                        for  (int  i  =  0;  i  <  selection.length;  i++)  {
                                int  midx  =  table.convertRowIndexToModel(selection[i]);
                                set.add(midx);
                                SwingWorker  worker  =  model.getSwingWorker(midx);
                                if  (worker  !=  null  &&  !worker.isDone())  {
                                        worker.cancel(true);
                                }
                                worker  =  null;
                        }
                        //  JTable过滤器
                        final  RowFilter<MyTableModel,  Integer>  filter  =  new  RowFilter<MyTableModel,  Integer>()  {

                                public  boolean  include(
                                                Entry<?  extends  MyTableModel,  ?  extends  Integer>  entry)  {
                                        Integer  midx  =  entry.getIdentifier();
                                        return  !set.contains(midx);
                                }
                        };
                        sorter.setRowFilter(filter);
                        table.repaint();
                }

                private  JPopupMenu  makePopup()  {
                        JPopupMenu  pop  =  new  JPopupMenu();
                        Action  act  =  new  CreateNewAction("添加",  null);
                        pop.add(act);
                        act  =  new  CancelAction("取消",  null);
                        int[]  selection  =  table.getSelectedRows();
                        if  (selection  ==  null  ||  selection.length  <=  0)
                                act.setEnabled(false);
                        pop.add(act);
                        //  分割线
                        pop.add(new  JSeparator());
                        act  =  new  DeleteAction("删除",  null);
                        if  (selection  ==  null  ||  selection.length  <=  0)
                                act.setEnabled(false);
                        pop.add(act);
                        return  pop;
                }

                public  static  void  main(String[]  args)  {
                        EventQueue.invokeLater(new  Runnable()  {
                                public  void  run()  {
                                        createGUI();
                                }
                        });
                }

                public  static  void  createGUI()  {

                        JFrame  frame  =  new  JFrame("在JTable中加载进度条及进行操作");
                        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                        frame.getContentPane().add(new  MyPanel());
                        frame.setSize(400,  400);
                        //  透明度90%
                        //  NativeLoader.getInstance().setTransparence(frame,  0.9f);
                        //  居中
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);

                }
        }

