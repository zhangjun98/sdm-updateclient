/**
 * Project Name: sdm-upgradetools
 * File Name: TestDemo
 * Package Name: com.supermap.digicty.sdm.testevent
 * Date: 2020/4/30 18:18
 * Copyright (c) 2020,All Rights Reserved.
 */
package com.supermap.digicty.sdm.testevent;

/**
 * @Author: zhangjun
 * @Description:
 * @Date: Create in 18:18 2020/4/30 
 */
public class TestDemo implements DemoListener {

    private DemoSource ds;

    public TestDemo()

    {

        ds=new DemoSource();

        ds.addDemoListener(this);

        System.out.println("添加监听器完毕");

        try {

            Thread.sleep(3000);

            //改变属性,触发事件

            ds.setName("改变属性,触发事件");

            for(int i=1;i<100;i++){
                ds.setName("改变属性,触发事件"+i);
                System.out.println(i);
            }

        }

        catch (InterruptedException ex) {

            ex.printStackTrace();

        }




    }

    public static void main(String args[])

    {

        new TestDemo();

    }

    /**

     * demoEvent

     *

     * @param dm DemoEvent

     * @todo Implement this test.DemoListener method

     */

    public void demoEvent(DemoEvent dm) {

        System.out.println("事件处理方法");

        System.out.println(dm.getName());

        dm.say();

    }

}
