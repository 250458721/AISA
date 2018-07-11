package com.icbc.zoro.trend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icbc.zoro.analyser.bean.RealtimeInfoBean;
import com.icbc.zoro.trend.bean.ClawerBean;
import com.icbc.zoro.trend.bean.HistoryTrendBean;
import com.icbc.zoro.trend.bean.KeywordBean;
import com.icbc.zoro.trend.bean.TrendBean;
import com.icbc.zoro.trend.mapper.TrendMapper;
  
@Service
public class TrendService{
	private static Logger logger = LogManager.getLogger(TrendService.class.getName());
	
    @Autowired
    private TrendMapper trendMapper;
    
    public List<RealtimeInfoBean> getRealtimeInfo(){
        return trendMapper.getRealtimeInfo();
    }
    
    public List<TrendBean> getTodayTrend(){
        return trendMapper.getTodayTrend();
    }
    
    public List<HistoryTrendBean> getHistoryInfo(String currency){
       	List<HistoryTrendBean> beanList = trendMapper.getHistoryInfo(currency);
    	for(int i=0;i<beanList.size();i++) {
    		HistoryTrendBean bean = beanList.get(i);
    		String posScore = bean.getPosScore();
    		bean.setPosScore(new BigDecimal(posScore).multiply(new BigDecimal(2)).subtract(new BigDecimal(1)).toString());
    		beanList.set(i, bean);
    	}
        return beanList;
    }
    
    public List<ClawerBean> getNews(Map<String,Object> inParam){
    	try {
    		return trendMapper.getNews(inParam);
    	}catch (Exception e) {
    		logger.error("获取资讯列表失败");
    		logger.error(e);
    		return null;
    	}
    }
    
    public List<KeywordBean> getKeywords(ClawerBean info){
    	try {
    		return trendMapper.getKeywords(info);
    	}catch (Exception e) {
			logger.error("获取关键词列表失败");
			logger.error(e);
			return null;
		}
    }
    public String getKeywordScore(ClawerBean info){
    	try {
    		return trendMapper.getKeywordScore(info);
    	}catch (Exception e) {
			logger.error("获取关键词趋势失败");
			logger.error(e);
			return null;
		}
    }
    public RealtimeInfoBean getHistoryCurrencyInfo(ClawerBean info){
    	try {
    		return trendMapper.getHistoryCurrencyInfo(info);
    	}catch (Exception e) {
			logger.error("获取币种历史价格失败");
			logger.error(e);
			return null;
		}
    }
    
}