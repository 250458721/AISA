package com.icbc.zoro.clawer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.icbc.zoro.clawer.mapper.ClawerMapper;
import com.icbc.zoro.currency.bean.CurrencyBean;
import com.icbc.zoro.webmagic.CommonPageProcessor;
import com.icbc.zoro.webmagic.DBPipeline;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;

@Component("clawerTask")
@Scope("prototype")
public class ClawerTask extends Thread {
	private static Logger logger = LogManager.getLogger(ClawerTask.class.getName());
	@Autowired
	private ClawerMapper clawerMapper;
    @Override
    public void run() {
        logger.info("线程:ClawerTask 运行中.....");
        
     // 日期初始化,用于创建每日文件夹
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	// 初始化爬虫参数
        List<CurrencyBean> currencyList =new ArrayList<CurrencyBean>() ;
        CurrencyBean currency1 = new CurrencyBean();
        currency1.setNameRegex("英镑[对兑/]{0,1}美元+");
        currency1.setShortNameRegex("镑[对兑/]{0,1}美+");
        currency1.setName("英镑");
        currency1.setShortName("镑");
        currency1.setId("1");
        currencyList.add(currency1);
        /*
        CurrencyBean currency2 = new CurrencyBean();
        currency2.setNameRegex("欧元[对兑/]{0,1}美元+");
        currency2.setShortNameRegex("欧[对兑/]{0,1}美+");
        currency2.setName("欧元");
        currency2.setShortName("欧");
        currency2.setId("2");
        currencyList.add(currency2);
        */

        List<String> webSiteLinks =  clawerMapper.getClawerWeb();
        /*
        String startUrlTest = "http://yzforex.com/news/view/?id=196709";
        // 网易外汇
        String startUrl1 = "http://money.163.com/forex/";
        // 中国外汇网
        String startUrl2 = "http://www.cnforex.com/";
        // 中国外汇网
        String startUrl3 = "http://yzforex.com/";
        // 和讯外汇网
        String startUrl4 = "http://forex.hexun.com/";
        // 汇达财经
        String startUrl5 = "http://www.huidafx.com/";
        // FX168
        String startUrl6 = "http://www.fx168.com/";
        // 外汇通
        String startUrl7 = "http://www.forex.com.cn/";
        // 外汇之星
        String startUrl8 = "http://www.forexstar.com.cn/";
        // 外汇之声
        String startUrl9 = "http://www.fmforex.com/";
        // 厚元咨询
        String startUrl10 = "http://www.hfc168.com/";
        
        // 田洪良外汇网
        String startUrl11 = "http://www.tianhongliang.com/";
        // 凤凰外汇
        String startUrl12 = "http://finance.ifeng.com/forex/";
     	// 汇通网
        String startUrl13 = "http://www.fx678.com/";
        // 外汇110网
        String startUrl14 = "https://www.fx110.com/";
        // 腾讯外汇网
        String startUrl15 = "http://finance.qq.com/money/forex/";
        // 新浪外汇网
        String startUrl16 = "http://finance.sina.com.cn/forex/";
        // 全景外汇
        String startUrl17 = "http://www.p5w.net/forex/";
        // 亚汇网
        String startUrl18 = "http://www.yahui.cc/";
        // 汇丰外汇网
        String startUrl19 = "http://www.tfx888.com/";
        // 外汇112网
        String startUrl20 = "http://www.fx112.com/";
        */
    	try {
    		Spider spider = Spider.create(new CommonPageProcessor(currencyList));
    		for(int i=0;i<webSiteLinks.size();i++) {
    			spider.addUrl(webSiteLinks.get(i));
    		}
    		spider.addPipeline(new DBPipeline(clawerMapper)) 
			//.addPipeline(new ConsolePipeline()) 
			.addPipeline(new JsonFilePipeline("D:\\zoro_data\\"+sdf.format(new Date())+"\\"))
			//.thread(5)
			.thread(5)
			.run();
		} catch (Exception e) {
			logger.error("Spider.create error："+e.getMessage());
		}
    }

}