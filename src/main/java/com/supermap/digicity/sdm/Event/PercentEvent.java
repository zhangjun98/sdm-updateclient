/**
 * Project Name: sdm-upgradetools
 * File Name: PercentEvent
 * Package Name: com.supermap.digicity.sdm.Event
 * Date: 2020/4/30 17:39
 * Copyright (c) 2020,All Rights Reserved.
 */
package com.supermap.digicity.sdm.Event;

import java.util.EventObject;

/**
 * @Author: zhangjun
 * @Description: 比例变化的事件
 * @Date: Create in 17:39 2020/4/30 
 */
public class PercentEvent extends EventObject {

    private Object source;

    private int  percent;

    /**
     * 构造方法
     * @param source 监听的对象
     * @param percent 监听的变量
     */
    public PercentEvent(Object source, int  percent) {
        super(source);
        this.source=source;
        this.percent=percent;
    }
    public int getPercent(){
        return percent;
    }
}
