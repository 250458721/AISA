package com.icbc.zoro.job;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.icbc.zoro.analyser.bean.RealtimeInfoBean;
import com.icbc.zoro.analyser.mapper.RealtimeInfoMapper;
import com.icbc.zoro.common.Constance;

/***
 * 数据分析任务
 * 
 * @author xjl
 *
 */
//@Component
public class CurrencyHistoryAnalysisJob {
	private static Logger logger = LogManager.getLogger(CurrencyHistoryAnalysisJob.class.getName());
	//@Autowired
	RealtimeInfoMapper realtimeInfoMapper;

	//@Scheduled(cron = "0/30 * * * * ?")
	public void getRealtimeInfo() {
		URL url;
		InputStream is;
		InputStreamReader isr;
		BufferedReader br;
		String data;
		logger.info("DateAnalysisJob->getRealtimeInfo start");
		try {
			// 创建一个URL实例
			url = new URL("http://hq.sinajs.cn/etag.php?_=" + System.currentTimeMillis() + "&list=fx_sgbpusd");

			// 通过URL的openStrean方法获取URL对象所表示的自愿字节输入流
			is = url.openStream();
			isr = new InputStreamReader(is, "GBK");

			// 为字符输入流添加缓冲
			br = new BufferedReader(isr);
			data = br.readLine();// 读取数据

			if (data != null) {// 读取数据
				String[] datas = data.split(",");
				RealtimeInfoBean info = new RealtimeInfoBean();
				info.setCurrency(Constance.CURRENCY_POUND_DICT_ID);
				info.setEndPrice(datas[1]);
				info.setStartPrice(datas[5]);
				info.setTopPrice(datas[6]);
				info.setBottomPrice(datas[7]);
				info.setChangeRate(datas[10]);

				realtimeInfoMapper.deletePoundInfo(Constance.CURRENCY_POUND_DICT_ID);
				realtimeInfoMapper.addPoundInfo(info);
			}
			br.close();
			isr.close();
			is.close();
			logger.info("DateAnalysisJob->getRealtimeInfo gbpusd finished");
		} catch (Exception e) {
			logger.error("DateAnalysisJob->getRealtimeInfo error", e);
			e.printStackTrace();
		}
		/*
		try {
			// 创建一个URL实例
			url = new URL("http://hq.sinajs.cn/etag.php?_=" + System.currentTimeMillis() + "&list=fx_seurusd");

			// 通过URL的openStrean方法获取URL对象所表示的自愿字节输入流
			is = url.openStream();
			isr = new InputStreamReader(is, "GBK");

			// 为字符输入流添加缓冲
			br = new BufferedReader(isr);
			data = br.readLine();// 读取数据

			if (data != null) {// 读取数据
				String[] datas = data.split(",");
				RealtimeInfoBean info = new RealtimeInfoBean();
				info.setCurrency(Constance.CURRENCY_EURUSD_DICT_ID);
				info.setEndPrice(datas[1]);
				info.setStartPrice(datas[5]);
				info.setTopPrice(datas[6]);
				info.setBottomPrice(datas[7]);
				info.setChangeRate(datas[10]);

				realtimeInfoMapper.deletePoundInfo(Constance.CURRENCY_EURUSD_DICT_ID);
				realtimeInfoMapper.addPoundInfo(info);
			}
			br.close();
			isr.close();
			is.close();
			logger.info("DateAnalysisJob->getRealtimeInfo finished");
		} catch (Exception e) {
			logger.error("DateAnalysisJob->getRealtimeInfo error", e);
			e.printStackTrace();
		}*/
	}
}
