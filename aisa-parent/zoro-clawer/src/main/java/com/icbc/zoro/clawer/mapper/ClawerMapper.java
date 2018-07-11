package com.icbc.zoro.clawer.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.dao.DataAccessException;

import com.icbc.zoro.clawer.bean.ClawerBean;
@Mapper
public interface ClawerMapper{
	// 保存到历史表
    @Insert("insert into zoro_clawer_history_info(url,currency,date,links,title,author,source,content,submit_time,status) values (#{url},#{currency},#{date},#{links},#{title},#{author},#{source},#{content},sysdate(),'1')")
    public int addCurrencyHistoryInfo(ClawerBean info) throws DataAccessException;
    
    @Select("select count(1) from zoro_clawer_history_info where links = #{links} and currency = #{currency}")
    public int getCurrencyHistoryCountByLinks(ClawerBean info) throws DataAccessException;
    
    @Select("select count(1) from zoro_clawer_history_info where title = #{title} and currency = #{currency}")
    public int getCurrencyHistoryCountByTitle(ClawerBean info) throws DataAccessException;
    
    // 保存到当前表
    @Insert("insert into zoro_clawer_info(url,currency,date,links,title,author,source,content,submit_time,status) values (#{url},#{currency},#{date},#{links},#{title},#{author},#{source},#{content},sysdate(),'1')")
    public int addCurrencyInfo(ClawerBean info) throws DataAccessException;
    
    @Select("select count(1) from zoro_clawer_info where links = #{links} and currency = #{currency}")
    public int getCurrencyCountByLinks(ClawerBean info) throws DataAccessException;
    
    @Select("select count(1) from zoro_clawer_info where title = #{title} and currency = #{currency}")
    public int getCurrencyCountByTitle(ClawerBean info) throws DataAccessException;

    // 爬虫信息超过30天的记录移动到历史表
    @Delete("delete from zoro_clawer_history_info where links in (select links from zoro_clawer_info where date_sub(sysdate(), INTERVAL 30 DAY) > date )")
    public int deleteClawerHistoryInfoBeforeInsert() throws DataAccessException;
    
    @Insert("insert into zoro_clawer_history_info(url, currency, date, links, title, author, source, content,submit_time,update_time,status,pos_score,neg_score) (select url,currency,date,links,title,author,source,content,submit_time,update_time,status,pos_score,neg_score from zoro_clawer_info where date_sub(sysdate(), INTERVAL 30 DAY) > date ) ")
    public int moveClawerInfoToHistory() throws DataAccessException;
    
    @Delete("delete from zoro_clawer_info where date_sub(sysdate(), INTERVAL 30 DAY) > date ")
    public int deleteClawerInfoAfterInsert() throws DataAccessException;
    
    @Select("select links from zoro_clawer_website_info")
    public List<String> getClawerWeb() throws DataAccessException;
    
}
