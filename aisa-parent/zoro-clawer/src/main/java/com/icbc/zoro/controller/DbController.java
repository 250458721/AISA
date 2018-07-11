package com.icbc.zoro.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.icbc.zoro.clawer.bean.ClawerBean;
import com.icbc.zoro.clawer.service.ClawerService;
import com.icbc.zoro.demo.bean.Demo;
import com.icbc.zoro.demo.service.DemoService;

@RestController
public class DbController {
	@Autowired
	private DemoService demoService;

	@Autowired
	private ClawerService poundClawerService;

	@RequestMapping("/likeName")
	public List<Demo> likeName(String name) {
		return demoService.likeName(name);
	}

	@RequestMapping("/test")
    public int test(HttpServletRequest request){
    	ClawerBean info = new ClawerBean();
    	info.setUrl("url");
    	info.setCurrency("1");
    	info.setDate("date");
    	info.setLinks("links");
    	info.setTitle("title");
    	info.setAuthor("author");
    	info.setSource("source");
    	info.setContent("content");
    	return poundClawerService.addPoundInfo(info);
    }
}