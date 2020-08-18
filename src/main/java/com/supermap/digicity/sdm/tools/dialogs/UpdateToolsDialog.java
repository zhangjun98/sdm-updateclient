/**
 * Project Name: Ris-modules
 * File Name: UpdateToolsDialog
 * Package Name: com.supermap.digicity.sdm.tools
 * Date: 2020/4/24 19:40
 * Copyright (c) 2020,All Rights Reserved.
 */
package com.supermap.digicity.sdm.tools.dialogs;

import com.supermap.digicity.sdm.model.ProduceInfo;
import com.supermap.digicity.sdm.model.VersionInfo;
import com.supermap.digicity.sdm.tools.dialogscommons.ToolsBaseDialog;
import com.supermap.digicity.sdm.utils.HttpClientUtils;
import com.supermap.digicity.sdm.utils.listutils;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhangjun
 * @Description: 获取产品的界面
 * @Date: Create in 19:40 2020/4/24 
 */
public class UpdateToolsDialog extends ToolsBaseDialog {
    private DefaultTableModel defaultTableModel;
    private JTable dsTable;
    private JScrollPane jspDs;
    private TableColumn filePathColumn;
    private TableColumn checkboxColumn;
    private TableColumn productversionColumn;
    private JButton btnupdate;
    private JButton btncancel;
    private JTextArea productinfo;
    private JScrollPane productinfoscroll;
    private JPanel mainpanel;
    private JLabel labeltype;
    private CommonButtonListener buttonListener;
    private JCheckBox checkBox;
    private JFileChooser tempfileChooser;
    private UpdateProcessDialog upd;


    public UpdateToolsDialog() {
        this.init();
    }
    @Override
    protected void initCommpents() {
        mainpanel=new JPanel();
        final String[] columnNames = new String[]{
                "","序号", "产品名称", "版本", "安装版本", "安装目录"};
        this.setTitle("产品列表");
        setSize(new Dimension(750, 450));
        defaultTableModel = new DefaultTableModel(new String[][]{}, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column==0||column==3)
                {
                    return true;
                }else{
                    return false;
                }
            }
            @Override
            public Object getValueAt(int row, int column) {

                if (column==1)
                {
                    return row+1;
                }
                else{
                    return super.getValueAt(row,column);
                }
            }
        };
        dsTable = new JTable();
        dsTable.setModel(defaultTableModel);
        buttonListener = new CommonButtonListener();
        this.filePathColumn = this.dsTable.getColumn(this.dsTable.getModel().getColumnName(5));
        this.checkboxColumn=this.dsTable.getColumn(this.dsTable.getModel().getColumnName(0));
        this.productversionColumn=this.dsTable.getColumn(this.dsTable.getModel().getColumnName(3));
        dsTable.setPreferredScrollableViewportSize(new Dimension(700, 350));
        labeltype = new JLabel("产品列表");
        jspDs = new JScrollPane(dsTable);
        productinfo =new JTextArea();
        productinfo.setLineWrap(true);     //激活自动换行功能
        productinfo.setWrapStyleWord(true);
        productinfoscroll = new JScrollPane(productinfo);
        btnupdate = new JButton("升级");
        btncancel =new JButton("取消");
        checkBox = new JCheckBox();
        tempfileChooser = new JFileChooser();
        addTableElement();

    }

    /**
     * 获取文件信息。加入到表格中去
     */
    private void addTableElement(){
       List<ProduceInfo> allproduct= HttpClientUtils.getproductlist();
        ProduceInfo  info;
        for(int i=0;i<allproduct.size();i++){
            info=allproduct.get(i);
            addInfoToTable(info);
        }
    }


    @Override
    protected void addFrmElement() {
    //添加所有的主键到窗体
        mainpanel.add(labeltype);
        mainpanel.add(jspDs);
        mainpanel.add(productinfoscroll);
        mainpanel.add(btnupdate);
        mainpanel.add(btncancel);
        this.add(mainpanel);
        setTableCellRendererAndEditor(dsTable);
    }

    @Override
    protected void addListener() {
    this.btncancel.addActionListener(buttonListener);
    this.btnupdate.addActionListener(buttonListener);
    this.dsTable.addMouseListener(tableMouseListener);
   defaultTableModel.addTableModelListener(tableModelListener);
    }

    @Override
    protected void setFrameSelf() {
        mainpanel.setLayout(null);
        // 设置titleLebel组件的位置
        labeltype.setBounds(30, 0, 90, 20);
        labeltype.setFont(new Font("宋体", Font.BOLD, 12));
        jspDs.setBounds(30, 30, 500, 530);
        jspDs.setFont(new Font("", Font.BOLD, 18));
        productinfo.setPreferredSize(new Dimension(480,190));
        productinfoscroll.setFont(new Font("", Font.BOLD, 18));
        btnupdate.setBounds(105, 570, 100, 40);
        btnupdate.setFont(new Font("宋体", Font.BOLD, 12));
        btncancel.setBounds(355, 570, 100, 40);
        btncancel.setFont(new Font("宋体", Font.BOLD, 12));
        this.setBounds(580, 230, 560, 660);
        dsTable.setRowHeight(20);
        dsTable.getColumnModel().getColumn(0).setPreferredWidth(20);
        dsTable.getColumnModel().getColumn(1).setPreferredWidth(30);
        dsTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        dsTable.getColumnModel().getColumn(3).setPreferredWidth(95);
        dsTable.getColumnModel().getColumn(4).setPreferredWidth(95);
        dsTable.getColumnModel().getColumn(5).setPreferredWidth(140);
        this.setDefaultCloseOperation(3);
        this.setResizable(false);
        this.setTitle("升级工具");
        this.setVisible(true);
    }

    @Override
    protected void unregistAction() {
        this.btncancel.removeActionListener(buttonListener);
        this.btnupdate.removeActionListener(buttonListener);

    }

    private String[] addFourthColumnElement(ProduceInfo produceInfo){
     int size =produceInfo.getProduceVersion().size();
     String [] values=produceInfo.getProduceVersion().toArray(new String[size]);
     return values;
    }
    /**
     * 添加表中元素
     * @param produceInfo
     */
    private void addInfoToTable(ProduceInfo produceInfo){
        String enProductname="";
        String setupversion="";
        String setuplocal="";
        try {
          // enProductname=  ProductType.valueOf(produceInfo.getProduceName()).toString();
          // setupversion= OperateRegistryUtils.getValue("supermapSGS",enProductname,"setupVersion");
          //setuplocal= OperateRegistryUtils.getValue("supermapSGS",enProductname,"setupLocal");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        defaultTableModel.addRow(new Object[]{
                new Boolean(false),
                0,
                produceInfo.getProduceName(),
                addFourthColumnElement(produceInfo),
                setupversion,
                setuplocal
        });
    }

    public  void setTableCellRendererAndEditor (JTable table) {
        try {
            CWCheckBoxRenderer tcr = new CWCheckBoxRenderer() ;
            CheckBoxCellEditor dei =new CheckBoxCellEditor();
            comboxCellEditor editor = new comboxCellEditor();
            comboxRenderer  renderer = new comboxRenderer();
            dsTable.getColumnModel().getColumn(3).setCellRenderer(renderer);
            dsTable.getColumnModel().getColumn(3).setCellEditor(editor);
            dsTable.getColumnModel().getColumn(0).setCellRenderer(tcr);
            dsTable.getColumnModel().getColumn(0).setCellEditor(dei);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * 文件选择
     */
    private void chooseFilePath() {
        int selectedRow = dsTable.getSelectedRow();
        String value = dsTable.getValueAt(selectedRow, 5).toString();
        tempfileChooser.setSelectedFile(new File(value));
        tempfileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY );
        int dialog = tempfileChooser.showDialog(this,"选择");
        if (dialog == 0) {
            String filePath = tempfileChooser.getSelectedFile().getPath();
            if (null != filePath && (new File(filePath)).exists() && (new File(filePath)).isDirectory()) {
                dsTable.setValueAt(filePath, selectedRow, 5);
                dsTable.updateUI();
            }
        }
    }

    /**
     * 更新前面的产品选择按钮
     */
    private void  changecheckbox() {
        int selectedRow = dsTable.getSelectedRow();
        boolean value = (Boolean) dsTable.getValueAt(selectedRow, 0);
        dsTable.setValueAt(!value, selectedRow, 0);
        dsTable.updateUI();
    }

    List<Integer>  oldupdaterowlist= new ArrayList<>();
    //第一次点击就改为0
    boolean isfirst=true;
    //记录不同的点击之后的difflist的size是否大于零
    boolean sizeGthz=true;
    private void btnbtnupdateClicked (){
        List<Integer>  newupdaterowlist = new ArrayList<>();
        List<ProduceInfo> downloadProcessProductinfoList=new ArrayList<>();
        //先判断第一列的值
        int rowCount=dsTable.getRowCount();
        for (int i =0;i<rowCount;i++){
            if((Boolean) dsTable.getModel().getValueAt(i,0)){
                newupdaterowlist.add(i);
            }
        }
      if(!isfirst){
          // 比较第二次点击是否会产生新的下载任务,找出有区别的列数，只能有新增，减少忽略掉
          List<Integer>  diff=  listutils.getDiffrent(oldupdaterowlist,newupdaterowlist);
          if(diff.size()==0){
              sizeGthz=false;
          }
            for(int j=0;j<diff.size();j++){
                ProduceInfo downloadproduct=new ProduceInfo();
                String productname=(String) dsTable.getModel().getValueAt(diff.get(j),2);
                String[] selectversion= (String[]) (dsTable.getModel().getValueAt(diff.get(j),3));
                String localversion=(String) dsTable.getModel().getValueAt(diff.get(j),4);
                String downloadProduceSetupLocal=(String) dsTable.getModel().getValueAt(diff.get(j),5);
                downloadproduct.setProduceName(productname);
                downloadproduct.setDownloadProcessVersion(selectversion[0]);
                downloadproduct.setLoaclVersion(localversion);
                downloadproduct.setDownloadProduceSetupLocal(downloadProduceSetupLocal);
                downloadProcessProductinfoList.add(downloadproduct);
            }
            //将不同的加入旧的行记录里面
            for(int j=0;j<diff.size();j++){
                oldupdaterowlist.add(diff.get(j));
            }
      }else{
          //第一次点击执行的事件
          isfirst=false;
          oldupdaterowlist=newupdaterowlist;
          for(int j=0;j<newupdaterowlist.size();j++){
              ProduceInfo downloadproduct=new ProduceInfo();
              String productname=(String) dsTable.getModel().getValueAt(newupdaterowlist.get(j),2);
              String[] selectversion= (String[]) (dsTable.getModel().getValueAt(newupdaterowlist.get(j),3));
              String localversion=(String) dsTable.getModel().getValueAt(newupdaterowlist.get(j),4);
              String downloadProduceSetupLocal=(String) dsTable.getModel().getValueAt(newupdaterowlist.get(j),5);
              downloadproduct.setProduceName(productname);
              downloadproduct.setDownloadProcessVersion(selectversion[0]);
              downloadproduct.setLoaclVersion(localversion);
              downloadproduct.setDownloadProduceSetupLocal(downloadProduceSetupLocal);
              downloadProcessProductinfoList.add(downloadproduct);
      }

        }
        //进入更新界面
            if (upd == null) {
                if(downloadProcessProductinfoList.size()==0) {
                    JOptionPane.showMessageDialog(this, "未选择待更新产品", "提示", JOptionPane.WARNING_MESSAGE);
                }else{
                upd = new UpdateProcessDialog(downloadProcessProductinfoList);
                }
            } else {
                upd.setVisible(true);
                if(sizeGthz){
                    upd.addInfosToTable(downloadProcessProductinfoList);
                }

            }
        }

    public static void main(String[] args) {
        UpdateToolsDialog frm = new UpdateToolsDialog();
    }

    /**
     * 按钮事件的内部类
     */
 private class CommonButtonListener implements ActionListener {
        CommonButtonListener() {}
        @Override
        public void actionPerformed(ActionEvent e){
            JComponent localJComponent = (JComponent)e.getSource();
            if (localJComponent == btncancel)
            {
                unregistAction();
                System.exit(0);
            }
            else if(localJComponent==btnupdate)
            {
                try {
                    dsTable.getCellEditor().stopCellEditing();
                }catch (Exception ex)
                {
                    System.out.println(ex.getMessage());
                }
                btnbtnupdateClicked();
            }

        }

    }

    private void changeProductInfoText(String productname,String productversion){
        jspDs.setBounds(30, 30, 500, 352);
        productinfoscroll.setBounds(30, 385, 500, 166);
        VersionInfo info = HttpClientUtils.getOneProductInfo(productname,productversion);
        try {
            if(info!=null){
                if(info.getFeatures().size()>0){
                    productinfo.append("新特性"+"\n");
                    for(int i=0;i<info.getFeatures().size();i++){
                        productinfo.append(info.getFeatures().get(i)+"\n");
                    }
                }
                if(info.getEnhances().size()>0){
                    productinfo.append("\n"+"增强内容"+"\n");
                    for(int i=0;i<info.getEnhances().size();i++){
                        productinfo.append(info.getEnhances().get(i)+"\n");
                    }
                }
                if(info.getCompatibilitys().size()>0){
                    productinfo.append("\n"+"兼容性说明"+"\n");
                    for(int i=0;i<info.getCompatibilitys().size();i++){
                        productinfo.append(info.getCompatibilitys().get(i)+"\n");
                    }
                }
                if(info.getOthers().size()>0){
                    productinfo.append("\n"+"其他说明"+"\n");
                    for(int i=0;i<info.getOthers().size();i++){
                        productinfo.append(info.getOthers().get(i)+"\n");
                    }
                }
            }else{
                System.out.println("获取远程信息失败，可能是有产品 无版本之类的的原因  dialog 150行");
            }

            productinfo.setEditable(false);
        }catch (NullPointerException e){
            e.getMessage();
        }
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
            JTable  table,
            Object  value,
            boolean isSelected,
            boolean hasFocus,
            int     row,
            int     column) {
        if (value instanceof Boolean) {
            setSelected(((Boolean) value).booleanValue());
            setForeground(table.getForeground());
            setBackground(table.getBackground());

        }

        return this;
    }
}


    class comboxCellEditor extends AbstractCellEditor implements TableCellEditor {
        JComboBox box;
        Object Gvalue;

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

            String[] values = (String[])value;
            Gvalue=value;
            box =new JComboBox(values);
            box.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    String item = e.getItem().toString();
                    int stateChange = e.getStateChange();
                    System.out.println(e.getStateChange());
                    if (stateChange == ItemEvent.SELECTED) {

                        //关闭编辑状态  productinfo.setText("我是因为他状态改变"+item);
                        dsTable.getCellEditor().stopCellEditing();
                    }else if (stateChange == ItemEvent.DESELECTED) {
                      //  System.out.println("此次事件由取消选中“" + item + "”触发！");
                    }else {
                    }
                }
            });
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
            String[] values = (String[])value;
            JComboBox box=new JComboBox(values);
            return box;
        }

    }
    private MouseListener tableMouseListener = new MouseAdapter() {
        @Override
        public void mouseExited(MouseEvent e) {
            if (null != dsTable.getCellEditor()&& e.getSource().equals(jspDs)) {
                dsTable.getCellEditor().stopCellEditing();
            }
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            if (2 == e.getClickCount()) {
                int selectedColumn =dsTable.getSelectedColumn();
                if (selectedColumn > 0) {
                    TableColumn tableColumn = dsTable.getColumn(dsTable.getModel().getColumnName(selectedColumn));
                    if (e.getSource().equals(dsTable) && tableColumn.equals(filePathColumn)) {
                        chooseFilePath();
                    }
                }
            } if (1 == e.getClickCount()){
                int selectedColumn =dsTable.getSelectedColumn();
                if (selectedColumn > 0) {
                    TableColumn tableColumn = dsTable.getColumn(dsTable.getModel().getColumnName(selectedColumn));
                    if (e.getSource().equals(dsTable) &&!tableColumn.equals(filePathColumn)&&!tableColumn.equals(checkboxColumn)) {
                        changecheckbox();
                    }
                }
            }

        }

    };
    private TableModelListener tableModelListener = new TableModelListener() {
        @Override
        public void tableChanged(TableModelEvent e) {
            if(e.getType() == TableModelEvent.UPDATE){
                //业务逻辑
                if(e.getColumn() == 3){
                    //传值有 产品名称，产品版本
                   String[]  versions= (String[]) defaultTableModel.getValueAt(e.getFirstRow(),e.getColumn());
                   String version =versions[0];
                   String  name =(String) defaultTableModel.getValueAt(e.getFirstRow(),2);
                   changeProductInfoText(name,version);
                }
            }

        }
    };
}