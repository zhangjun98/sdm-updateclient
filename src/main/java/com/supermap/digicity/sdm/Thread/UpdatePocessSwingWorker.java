/**
 * Project Name: sdm-upgradetools
 * File Name: DownLoadSwingWorker
 * Package Name: com.supermap.digicity.sdm.Thread
 * Date: 2020/4/30 15:57
 * Copyright (c) 2020,All Rights Reserved.
 */
package com.supermap.digicity.sdm.Thread;

import com.supermap.digicity.sdm.Thread.interfaces.IBackTask;

import javax.swing.*;

/**
 * @Author: zhangjun
 * @Description: UpdatePocessSwingWorker 更新的 swingworker
 * @Date: Create in 15:57 2020/4/30 
 */
public class UpdatePocessSwingWorker extends SwingWorker<Boolean, IBackTask> {

    private IBackTask task;

    public UpdatePocessSwingWorker(IBackTask task){
        this.task = task;
    }
    @Override
    protected Boolean doInBackground() throws Exception {
          return (Boolean) task.run();
    }
    @Override
    protected void done() {

    }

}
