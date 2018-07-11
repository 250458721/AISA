package com.icbc.zoro.trend.mapper;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.icbc.zoro.analyser.bean.RealtimeInfoBean;
import com.icbc.zoro.trend.bean.ClawerBean;
import com.icbc.zoro.trend.bean.HistoryTrendBean;
import com.icbc.zoro.trend.bean.KeywordBean;
import com.icbc.zoro.trend.bean.TrendBean;
@Mapper
public interface TrendMapper{
	// 获取当前预测人趋势值
    @Select("select currency, pos_score from zoro_daily_gensim_info where date = date_format(now(),'%Y-%m-%d')")
    public List<TrendBean> getTodayTrend();
    
    // 获取历史信息
    //@Select("select date as date , end_price as endPrice, change_rate as changeRate from zoro_currency_history_info where currency = #{currency} order by id desc limit 0,14")
    @Select("select t1.date , t1.end_price as endPrice, t1.change_rate as changeRate, t2.pos_score as posScore from zoro_currency_history_info t1, zoro_daily_gensim_info t2 where t1.currency =#{currency} and t2.currency = t1.currency and t1.date = t2.date and t1.change_rate!='0.0' order by date desc limit 0,20")
    public List<HistoryTrendBean> getHistoryInfo(String currency);

    // 获取资讯
    @Select("select id, title, url, date, links from zoro_clawer_info t where id < #{maxId} order by id desc limit 1,#{count}")
    public List<ClawerBean> getNews(Map<String,Object> inPatam);

    // 获取所有币种对实时价格
    @Select("select currency,date,end_price as endPrice, start_price as startPrice,top_price as topPrice,bottom_price as bottomPrice,change_rate as changeRate from zoro_currency_history_info where date = date_format(now(),'%Y-%m-%d')")
    public List<RealtimeInfoBean> getRealtimeInfo();

    // 获取关键词信息
    @Select("select t1.keyword as id,t2.dict_name as name, t1.count as value from zoro_keyword_count_info t1, zoro_gen_dict t2 where t1.currency = #{currency} and t1.date = #{date} and t1.keyword=t2.dict_id and t2.dict_type = 'keyword' and count>0 order by count desc limit 10")
    public List<KeywordBean> getKeywords(ClawerBean info);

    // 获取关键词趋势
    @Select("select pos_score from zoro_keyword_trend_info where currency = #{currency} and date = #{date}")
    public String getKeywordScore(ClawerBean info);

    // 获取所有币种对实时价格
    @Select("select end_price as endPrice, start_price as startPrice,top_price as topPrice,bottom_price as bottomPrice,change_rate as changeRate from zoro_currency_history_info where date = #{date} and currency = #{currency}")
    public RealtimeInfoBean getHistoryCurrencyInfo(ClawerBean info);

}
