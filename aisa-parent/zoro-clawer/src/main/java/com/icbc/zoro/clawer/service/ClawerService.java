package com.icbc.zoro.clawer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icbc.zoro.clawer.bean.ClawerBean;
import com.icbc.zoro.clawer.mapper.ClawerMapper;

@Service
public class ClawerService {

	@Autowired
	private ClawerMapper clawerMapper;

	public int addPoundInfo(ClawerBean info) {
		return clawerMapper.addCurrencyInfo(info);
	}
}