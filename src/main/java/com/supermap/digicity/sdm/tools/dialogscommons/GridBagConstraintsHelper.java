/**
 * Project Name: sdm-upgradetools
 * File Name: GridBagConstraintsHelper
 * Package Name: com.supermap.digicity.sdm.tools.dialogs
 * Date: 2020/5/15 13:12
 * Copyright (c) 2020,All Rights Reserved.
 */
package com.supermap.digicity.sdm.tools.dialogscommons;

/**
 * @Author:
 * @Description: 公司产品  直接复制过来的
 * @Date: Create in 13:12 2020/5/15 
 */

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class GridBagConstraintsHelper extends GridBagConstraints {
    public static final int CONTROLS_GAP = 5;
    public static final int FRAME_CONTROL_GAP = 10;
    private static final long serialVersionUID = 1L;

    public GridBagConstraintsHelper(int gridx, int gridy) {
        this.gridx = gridx;
        this.gridy = gridy;
    }

    public GridBagConstraintsHelper(int gridx, int gridy, int gridwidth, int gridheight) {
        this.gridx = gridx;
        this.gridy = gridy;
        this.gridwidth = gridwidth;
        this.gridheight = gridheight;
    }

    public GridBagConstraintsHelper setSize(int gridWidth, int gridHeight) {
        this.gridwidth = gridWidth;
        this.gridheight = gridHeight;
        return this;
    }

    public GridBagConstraintsHelper setAnchor(int anchor) {
        this.anchor = anchor;
        return this;
    }

    public GridBagConstraintsHelper setFill(int fill) {
        this.fill = fill;
        return this;
    }

    public GridBagConstraintsHelper setWeight(double weightx, double weighty) {
        this.weightx = weightx;
        this.weighty = weighty;
        return this;
    }

    public GridBagConstraintsHelper setInsets(int distance) {
        this.insets = new Insets(distance, distance, distance, distance);
        return this;
    }

    public GridBagConstraintsHelper setInsets(int top, int left, int bottom, int right) {
        this.insets = new Insets(top, left, bottom, right);
        return this;
    }

    public GridBagConstraintsHelper setIpad(int ipadx, int ipady) {
        this.ipadx = ipadx;
        this.ipady = ipady;
        return this;
    }

    public static GridBagConstraintsHelper getSwitchConstraints() {
        return (new GridBagConstraintsHelper(0, 0)).setWeight(1.0D, 1.0D).setFill(1).setInsets(10);
    }
}

