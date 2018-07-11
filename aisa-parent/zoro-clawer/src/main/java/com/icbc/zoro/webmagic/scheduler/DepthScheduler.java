package com.icbc.zoro.webmagic.scheduler;

import com.icbc.zoro.common.Constance;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.scheduler.QueueScheduler;

public class DepthScheduler extends QueueScheduler {
	 
    public DepthScheduler() {
    }
 
    public DepthScheduler(int levelLimit) {
    }
 
    @Override
    public void push(Request request, us.codecraft.webmagic.Task task) {
        if (request.getExtra("_level") == null || ((Integer) request.getExtra("_level")) <= Constance.CLAWER_LEVEL_LIMIT) {
            super.push(request, task);
        }
    }
}
