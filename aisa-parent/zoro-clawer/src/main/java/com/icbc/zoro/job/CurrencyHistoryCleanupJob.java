package com.icbc.zoro.job;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.icbc.zoro.clawer.mapper.ClawerMapper;

/***
 * 数据分析任务
 * 
 * @author xjl
 *
 */
@Component
public class CurrencyHistoryCleanupJob {
	private static Logger logger = LogManager.getLogger(CurrencyHistoryCleanupJob.class.getName());
	
	@Autowired
	private ClawerMapper clawerMapper;
	// 每6个小时归档一次
	@Scheduled(cron = "0 0 0/6 * * ?")
	public void clawerInfoCleanUp() {
		logger.error("CurrencyHistoryCleanupJob start");
		try {
			// 清理已存在的相同归档记录
			clawerMapper.deleteClawerHistoryInfoBeforeInsert();
			// 归档移动
			clawerMapper.moveClawerInfoToHistory();
			// 清理原数据库
			clawerMapper.deleteClawerInfoAfterInsert();
			
		}catch(Exception e) {
			logger.error("CurrencyHistoryCleanupJob error:"+e.getMessage());
			e.printStackTrace();
		}
		logger.error("CurrencyHistoryCleanupJob succeed");
	}
}
