package com.icbc.zoro.job;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/***
 * 数据分析任务
 * 
 * @author xjl
 *
 */
@Component
public class DateAnalysisJob {
	private static Logger logger = LogManager.getLogger(DateAnalysisJob.class.getName());
	@Value("${python.rootpath}")
	String PythonRoot;
	/**
	 * 爬虫数据评分 10分钟一次
	 * @param input
	 */
	@Scheduled(cron = "0 0/10 * * * ?")
	public void predict() {
		logger.info("DateAnalysisJob->predict start");
		try {
			final Process process = Runtime.getRuntime().exec("python " + PythonRoot + "dataAnalysis.py");
			printInfoMessage(process.getInputStream());
			printErrorMessage(process.getErrorStream());
			process.waitFor();
			logger.info("DateAnalysisJob->predict finished");
		} catch (Exception e) {
			logger.error("DateAnalysisJob->predict error", e);
		}
	}
	/**
	 * 预测当前币种涨幅  10分钟一次
	 * @param input
	 */
	@Scheduled(cron = "0 0/10 * * * ?")
	public void caculate() {
		logger.info("DateAnalysisJob->caculate start");
		try {
			final Process process = Runtime.getRuntime().exec("python " + PythonRoot + "trendAnalysis.py");
			printInfoMessage(process.getInputStream());
			printErrorMessage(process.getErrorStream());
			process.waitFor();
			logger.info("DateAnalysisJob->caculate finished");
		} catch (Exception e) {
			logger.error("DateAnalysisJob->caculate error", e);
		}
	}
	/***
	 *  keywordStatistics 关键词统计
	 */
	@Scheduled(cron = "0 0/10 * * * ?")
	public void keywordStatistics() {
		logger.info("keywordStatistics start");
		try {
			final Process process = Runtime.getRuntime().exec("python " + PythonRoot + "keywordStatistics.py");
			printInfoMessage(process.getInputStream());
			printErrorMessage(process.getErrorStream());
			process.waitFor();
			logger.info("keywordStatistics finished");
		} catch (Exception e) {
			logger.error("keywordStatistics error", e);
		}
	}
	/***
	 *  keywordAnalysis 关键词向量计算
	 */
	@Scheduled(cron = "0 0/10 * * * ?")
	public void keywordAnalysis() {
		logger.info("keywordAnalysis start");
		try {
			final Process process = Runtime.getRuntime().exec("python " + PythonRoot + "keywordAnalysis.py");
			printInfoMessage(process.getInputStream());
			printErrorMessage(process.getErrorStream());
			process.waitFor();
			logger.info("keywordAnalysis finished");
		} catch (Exception e) {
			logger.error("keywordAnalysis error", e);
		}
	}
	
	/***
	 *  keywordAnalysis 关键词预测
	 */
	@Scheduled(cron = "0 0/10 * * * ?")
	//@Scheduled(cron = "0/10 * * * * ?")
	public void keywordPredict() {
		logger.info("keywordAnalysis->keywordPredict start");
		try {
			final Process process = Runtime.getRuntime().exec("python " + PythonRoot + "keywordPredict.py");
			printInfoMessage(process.getInputStream());
			printErrorMessage(process.getErrorStream());
			process.waitFor();
			logger.info("keywordAnalysis->keywordPredict finished");
		} catch (Exception e) {
			logger.error("keywordAnalysis->keywordPredict error", e);
		}
	}
	/**
	 * 异步python输出运行日志
	 * @param input
	 */

	private static void printInfoMessage(final InputStream input) {
		new Thread(new Runnable() {
			 @Override
			public void run() {
				Reader reader = new InputStreamReader(input);
				BufferedReader bf = new BufferedReader(reader);
				try {
					while ((bf.readLine()) != null) {
						// info日志过多，暂时关闭
						// System.out.println(line);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	/**
	 * 异步python输出错误日志
	 * @param input
	 */
	private static void printErrorMessage(final InputStream input) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Reader reader = new InputStreamReader(input);
				BufferedReader bf = new BufferedReader(reader);
				String line = null;
				try {
					while ((line = bf.readLine()) != null) {
						logger.error(line);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
