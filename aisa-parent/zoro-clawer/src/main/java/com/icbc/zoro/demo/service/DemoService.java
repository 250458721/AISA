package com.icbc.zoro.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icbc.zoro.demo.bean.Demo;
import com.icbc.zoro.demo.mapper.DemoMapper;
  
@Service
public class DemoService{
    @Autowired
    private DemoMapper demoMapper;
    
    public List<Demo> likeName(String name){
        return demoMapper.likeName(name);
    }
}