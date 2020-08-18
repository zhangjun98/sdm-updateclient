package com.supermap.digicity.sdm.Thread.interfaces;

import com.supermap.digicity.sdm.Event.PercentListener;



/**
 * @Author: zhangjun
 * @Description: 线程接口
 * @Date: Create in 19:30 2020/4/30
 */
public interface IBackTask extends PercentListener {

    int addDownWork();
    Object run()throws Exception ;
}
