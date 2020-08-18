/**
 * Project Name: sdm-upgradetools
 * File Name: DemoSource
 * Package Name: com.supermap.digicty.sdm.testevent
 * Date: 2020/4/30 18:17
 * Copyright (c) 2020,All Rights Reserved.
 */
package com.supermap.digicty.sdm.testevent;

import java.util.Enumeration;
import java.util.Vector;

/**
 * @Author: zhangjun
 * @Description:
 * @Date: Create in 18:17 2020/4/30 
 */
public class DemoSource {
    private Vector repository = new Vector();

    private DemoListener dl;

    private String sName="";

    public DemoSource()

    {

    }

//注册监听器，如果这里没有使用Vector而是使用ArrayList那么要注意同步问题

    public void addDemoListener(DemoListener dl)

    {
        repository.addElement(dl);//这步要注意同步问题
    }

//如果这里没有使用Vector而是使用ArrayList那么要注意同步问题






//删除监听器，如果这里没有使用Vector而是使用ArrayList那么要注意同步问题

    public void removeDemoListener(DemoListener dl)

    {

        repository.remove(dl);//这步要注意同步问题

    }

    /**

     * 设置属性

     * @param str1 String

     */

    public void setName(String str1)

    {

        boolean bool=false;

        if(str1==null && sName!=null) bool=true;

        else if(str1!=null && sName==null) bool=true;

        else if(!sName.equals(str1)) bool=true;

        this.sName=str1;

//如果改变则执行事件

        dl.demoEvent(new DemoEvent(this,sName));

    }

    public String getName()

    {

        return sName;

    }
}
