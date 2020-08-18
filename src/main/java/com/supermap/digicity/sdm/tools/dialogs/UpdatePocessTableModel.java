/**
 * Project Name: sdm-upgradetools
 * File Name: downloadTableModel
 * Package Name: com.supermap.digicity.sdm.tools.dialogs
 * Date: 2020/4/30 15:22
 * Copyright (c) 2020,All Rights Reserved.
 */
package com.supermap.digicity.sdm.tools.dialogs;

import javax.swing.table.DefaultTableModel;


/**
 * @Author: zhangjun
 * @Description: 更新进程界面的Tablemodel
 * @Date: Create in 15:22 2020/4/30 
 */
public class UpdatePocessTableModel extends DefaultTableModel {

    public UpdatePocessTableModel(Object[][] data, Object[] columnNames){
        super(data,columnNames);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
    @Override
    public Object getValueAt(int row, int column) {

        if (column==0)
        {
            return row+1;
        }
        else{
            return super.getValueAt(row,column);
        }
    }
}
