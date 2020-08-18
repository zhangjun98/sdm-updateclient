/**
 * Project Name: sdm-upgradetools
 * File Name: DemoEvent
 * Package Name: com.supermap.digicty.sdm.testevent
 * Date: 2020/4/30 18:15
 * Copyright (c) 2020,All Rights Reserved.
 */
package com.supermap.digicty.sdm.testevent;

import java.util.EventObject;

/**
 * @Author: zhangjun
 * @Description:
 * @Date: Create in 18:15 2020/4/30 
 */
public class DemoEvent  extends EventObject {

    private Object obj;

    private String sName;

    public DemoEvent(Object source,String sName) {

        super(source);

        obj = source;

        this.sName=sName;

    }

    public Object getSource()

    {

        return obj;

    }

    public void say()

    {

        System.out.println("这个是 say 方法");

    }

    public String getName()

    {

        return sName;

    }
}
