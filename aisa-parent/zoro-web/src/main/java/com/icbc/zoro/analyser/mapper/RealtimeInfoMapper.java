package com.icbc.zoro.analyser.mapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import com.icbc.zoro.analyser.bean.RealtimeInfoBean;
@Mapper
public interface RealtimeInfoMapper{
    
    @Delete ("delete from zoro_currency_history_info where currency = #{currency} and date = date_format(now(),'%Y-%m-%d')")
    public int deletePoundInfo(String currency) throws DataAccessException;
    
    @Insert("insert into zoro_currency_history_info(currency, date, end_price, start_price, top_price, bottom_price , change_rate)values( #{currency}, date_format(now(),'%Y-%m-%d'), #{endPrice}, #{startPrice}, #{topPrice}, #{bottomPrice}, #{changeRate})") 
 	public int addPoundInfo(RealtimeInfoBean info) throws DataAccessException;
    
}
