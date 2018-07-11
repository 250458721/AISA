package com.icbc.zoro.controller;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icbc.zoro.common.Constance;
import com.icbc.zoro.common.ZoroUtil;
import com.icbc.zoro.trend.bean.ClawerBean;
import com.icbc.zoro.trend.bean.TrendBean;
import com.icbc.zoro.trend.service.TrendService;

@RestController
public class DbController {
	@Autowired
	private TrendService trendService;
	/***
	 * 获取实时信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/getRealtimeInfo")
	public String getRealtimeInfo(HttpServletRequest request) {
		JSONArray array = new JSONArray();
		array.addAll(trendService.getRealtimeInfo());
		return array.toJSONString();
	}
	/***
	 * 获取趋势
	 * @param currency
	 * @param request
	 * @return
	 */
	@RequestMapping("/getTrend/{currency}")
	public String getTrend(@PathVariable String currency, HttpServletRequest request) {
		JSONArray array = new JSONArray();
		List<TrendBean> list = trendService.getTodayTrend();
		if(currency.equals(Constance.CURRENCY_ALL_DICT_ID)) {
			for (int i = 0; i < list.size(); i++) {
				java.text.DecimalFormat dft = new java.text.DecimalFormat("0.00");
				TrendBean temp = list.get(i);
				temp.setPos_score(dft.format(new BigDecimal(temp.getPos_score())).replaceAll("0.", ""));
				array.add(temp);
			}
		}
		else{
			for (int i = 0; i < list.size(); i++) {
				if(currency.equals(list.get(i).getCurrency())) {
					java.text.DecimalFormat dft = new java.text.DecimalFormat("0.00");
					TrendBean temp = list.get(i);
					temp.setPos_score(dft.format(new BigDecimal(temp.getPos_score())).replaceAll("0.", ""));
					array.add(temp);
				}
			}
		}
		return array.toJSONString();
	}
	/***
	 * 获取历史趋势
	 * @param currency
	 * @param request
	 * @return
	 */
	@RequestMapping("/getHistory/{currency}")
	public String getHistory(@PathVariable String currency, HttpServletRequest request) {
		JSONArray array = new JSONArray();
		array.addAll(trendService.getHistoryInfo(currency));
		return array.toJSONString();
	}
	/***
	 * 获取新闻
	 * @param maxId
	 * @param request
	 * @return
	 */
	@RequestMapping("/getNews/{maxId}/{count}")
	public String getNews(@PathVariable String maxId,@PathVariable String count, HttpServletRequest request) {
		JSONArray array = new JSONArray();
		Map<String, Object> inParam = new HashMap<String, Object>();
		inParam.put("maxId", maxId);
		inParam.put("count", Integer.parseInt(count));
		array.addAll(trendService.getNews(inParam));
		return array.toJSONString();
	}
	/**
	 * 获取当日关键词饼图
	 * @param currency
	 * @param date
	 * @param request
	 * @return
	 */
	@RequestMapping("/getLatestKeywords/{currency}")
	public String getLatestKeywords(@PathVariable String currency, HttpServletRequest request) {
		String date = ZoroUtil.dateToStr(new Date());
		JSONArray keywordArray = new JSONArray();
		ClawerBean info = new ClawerBean();
		info.setCurrency(currency);
		info.setDate(date);
		keywordArray.addAll(trendService.getKeywords(info));
	
		// 如果今日关键词数据过少，则显示前一日数据
		if(keywordArray.size()<5) {
			keywordArray.clear();
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)-1);
			info.setDate(ZoroUtil.dateToStr(calendar.getTime()));
			keywordArray.addAll(trendService.getKeywords(info));

		}
		JSONObject result = new JSONObject();
		result.put("keywordArray", keywordArray);
		result.put("keywordScore", trendService.getKeywordScore(info));
		
		return result.toJSONString();
	}
	/**
	 * 获取最近关键词饼图
	 * @param currency
	 * @param date
	 * @param request
	 * @return
	 */
	@RequestMapping("/getKeywords/{currency}/{date}")
	public String getKeywords(@PathVariable String currency, @PathVariable String date, HttpServletRequest request) {
		JSONArray KeywordArray = new JSONArray();
		ClawerBean info = new ClawerBean();
		info.setCurrency(currency);
		info.setDate(date);
		KeywordArray.addAll(trendService.getKeywords(info));
		JSONObject result = new JSONObject();
		result.put("keywordArray", KeywordArray);
		result.put("keywordScore", trendService.getKeywordScore(info));
		return result.toJSONString();
	}
	/***
	 * 获取历史币种信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/getHistoryCurrencyInfo/{currency}/{date}")
	public String getHistoryCurrencyInfo(@PathVariable String currency, @PathVariable String date, HttpServletRequest request) {
		ClawerBean info = new ClawerBean();
		info.setCurrency(currency);
		info.setDate(date);
		JSONArray array = new JSONArray();
		array.add(trendService.getHistoryCurrencyInfo(info));
		return array.toJSONString();
	}
}