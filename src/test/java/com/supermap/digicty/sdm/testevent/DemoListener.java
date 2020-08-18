package com.supermap.digicty.sdm.testevent;

import java.util.EventListener;

/**
 * @Author: zhangjun
 * @Description:
 * @Date: Create in 18:16 2020/4/30
 */
public interface DemoListener extends EventListener {
    public void demoEvent(DemoEvent dm);
}
