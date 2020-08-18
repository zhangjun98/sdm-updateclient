/**
 * Project Name: Ris-modules
 * File Name: ToolsBaseDialog
 * Package Name: com.supermap.digicity.sdm.tools
 * Date: 2020/4/24 20:01
 * Copyright (c) 2020,All Rights Reserved.
 */
package com.supermap.digicity.sdm.tools.dialogs;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;

/**
 * @Author: zhangjun
 * @Description: 图形界面的基类
 * @Date: Create in 20:01 2020/4/24 
 */
public abstract class ToolsBaseDialog extends JFrame {


    private static final long serialVersionUID = 1L;
    public ToolsBaseDialog() {

    }
    /**
     * 自动执行方法的顺序
     * @throws IOException
     */
    public void init()   {
        this.initCommpents();
        this.addFrmElement();
        this.addListener();
        this.setFrameSelf();
        ImageIcon imageIcon = new ImageIcon(this.getClass().getClassLoader().getResource("icon.png"));
        this.setIconImage(imageIcon.getImage());

    }
    protected abstract void initCommpents();
    protected abstract void addFrmElement();
    protected abstract void addListener() ;
    protected abstract void setFrameSelf();
    protected abstract void unregistAction();
}
