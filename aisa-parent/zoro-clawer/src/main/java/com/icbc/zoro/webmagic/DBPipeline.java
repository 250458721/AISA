package com.icbc.zoro.webmagic;

import java.util.Calendar;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.icbc.zoro.clawer.bean.ClawerBean;
import com.icbc.zoro.clawer.mapper.ClawerMapper;
import com.icbc.zoro.common.Constance;
import com.icbc.zoro.common.ZoroUtil;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class DBPipeline implements Pipeline {

	private static Logger logger = LogManager.getLogger(DBPipeline.class.getName());

	ClawerMapper clawerMapper;

	public DBPipeline(ClawerMapper mapper) {
		this.clawerMapper = mapper;
	}

	@Override
	public void process(ResultItems resultItems, Task task) {
		ClawerBean info = new ClawerBean();
		for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
			if (entry.getValue() != null) {
				switch (entry.getKey()) {
				case "url":
					info.setUrl((String) entry.getValue());
					break;
				case "date":
					info.setDate((String) entry.getValue());
					break;
				case "links":
					info.setLinks((String) entry.getValue());
					break;
				case "title":
					info.setTitle((String) entry.getValue());
					break;
				case "author":
					info.setAuthor((String) entry.getValue());
					break;
				case "source":
					info.setSource((String) entry.getValue());
					break;
				case "content":
					info.setContent((String) entry.getValue());
					break;
				/*
				 * case "currencyCount": for(int i= 0;) {
				 * 
				 * } switch((String) entry.getValue()) { case
				 * Constance.CURRENCY_POUND_DICT_NAME:
				 * info.setCurrency(Constance.CURRENCY_POUND_DICT_ID); break; default:
				 * info.setCurrency(Constance.CURRENCY_POUND_DICT_ID);; } break;
				 */
				default:
					;
				}
			}
		}
		//**
		logger.info("获取记录结果：" + info.toString());
		// 没有重复数据已在数据库中。

		// 获取当前处理币种
		String currency = resultItems.get("currency0");
		info.setCurrency(currency);
		// 判断是否存入历史信息表
		Boolean ifSaveIntoHistoryTable = true;
		try {
			// 获取日志
			if(null != info.getDate()) {
				Calendar calendar=Calendar.getInstance();
				calendar.setTime(ZoroUtil.strToDate(info.getDate()));
				long oldday=calendar.getTimeInMillis();
				Calendar calendarNow=Calendar.getInstance();//默认是当前日期
				long nowday=calendarNow.getTimeInMillis();
				System.out.println("date="+info.getDate()+" nowday-oldday ="+(nowday-oldday) );
				if(nowday-oldday < Double.parseDouble(Constance.CURRENCY_STORE_DAY_LIMIT) && nowday-oldday >= 0) {
					ifSaveIntoHistoryTable = false;
				}
			}
		} catch(Exception e) {
			logger.error(e.getMessage());
		}
		if(ifSaveIntoHistoryTable) {
			if (this.clawerMapper.getCurrencyHistoryCountByLinks(info) == 0
					&& this.clawerMapper.getCurrencyHistoryCountByTitle(info) == 0) {
				// 插入信息。
				this.clawerMapper.addCurrencyHistoryInfo(info);
				logger.info("结果记录历史表成功！");
			} else {
				logger.info("结果记录历史表失败！记录已存在");
			}
		} else {
			if (this.clawerMapper.getCurrencyCountByLinks(info) == 0
					&& this.clawerMapper.getCurrencyCountByTitle(info) == 0) {
				// 插入信息。
				this.clawerMapper.addCurrencyInfo(info);
				logger.info("结果记录成功！");
			} else {
				logger.info("结果记录失败！记录已存在");
			}
		}
	}
}