package com.icbc.zoro.demo.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.icbc.zoro.demo.bean.Demo;
@Mapper
public interface DemoMapper{
    
    @Select("select *from test where name = #{name}")
    public List<Demo> likeName(String name);
    
    @Select("select *from test where id = #{id}")
    public Demo getById(long id);
    
    @Select("select name from test where id = #{id}")
    public String getNameById(long id);
    
    
}
