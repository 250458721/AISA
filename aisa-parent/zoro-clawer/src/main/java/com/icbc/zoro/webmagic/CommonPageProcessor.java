package com.icbc.zoro.webmagic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.icbc.zoro.common.ZoroUtil;
import com.icbc.zoro.currency.bean.CurrencyBean;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Xpath2Selector;

public class CommonPageProcessor implements PageProcessor {
	private static Logger logger = LogManager.getLogger(CommonPageProcessor.class.getName());
	private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

	List<CurrencyBean> currencyList;

	public CommonPageProcessor(List<CurrencyBean> currencyList) throws Exception {
		if (currencyList == null) {
			logger.error("CommonPageProcessor数据初始化错误,不得为空");
			throw new Exception("CommonPageProcessor数据初始化错误,不得为空");
		}
		this.currencyList = currencyList;
	}

	@Override
	public void process(Page page) {
		boolean ifSkip = false;

		// 页面日期
		String date = null;
		// 日期匹配规则 yyyy-MM-dd 或者yyyy/MM/dd
		String todayRegex = "20[0-9]{2}[-/][0-9]{1,2}[-/][0-9]{1,2}";
		// 日期肯定下挂在div标签下
		Xpath2Selector xpath2Selector = new Xpath2Selector("//div//text()[name(..) != \"a\"][name(..) != \"td\"][name(..) != \"li\"]");
		List<String> dateList = xpath2Selector.selectList(page.getHtml().get());
		// 按指定模式在字符串查找
		// 创建 Pattern 对象
		for (int i = 0; i < dateList.size(); i++) {
			Pattern r = Pattern.compile(todayRegex);
			if (dateList.get(i).length() >= 10) {
				// 现在创建 matcher 对象
				Matcher m = r.matcher(dateList.get(i));
				if (m.find()) {
					date = m.group(0);
					break;
				}
			}
		}
		if(null!=date) {
			date = date.replace("/", "-").replace(" ", "");
		} else {
			String todayRegexChinese = "20[0-9]{2}[年][0-9]{1,2}[月][0-9]{1,2}[日]";
			// 日期肯定下挂在div标签下
			List<String> dateListChinese = page.getHtml()
					.xpath("//div//text()[name(..)!=\"a\"][name(..)!=\"li\"][normalize-space()]")
					.regex(todayRegexChinese).all();
			if (dateListChinese.size() > 0) {
				date = dateListChinese.get(0).replace("年", "-").replace("月", "-").replace("日", "");
			}
		}
		// 异常处理
		if(null==date) {
			date = "1970-01-01";
		}else {
			try {
				date = ZoroUtil.dateToStr(ZoroUtil.strToDate(date));
			}catch(Exception e) {
				date = "1970-01-01";
			}
		}
		// 页面所属网站url
		String url = page.getUrl().get();
		url = url.substring(0, url.substring(7, url.length()).indexOf("/") + 7);
		// 添加来源分类，为了后续入库处理
		page.putField("url", url);
		String targetRequestsRegex = "(" + url.replace(".", "\\.") + "/([\\s\\S])*)";
		// 添加页面加载种子
		
		//page.addTargetRequests(page.getHtml().links().regex(targetRequestsRegex).all());
		List<String> nextLinks = page.getHtml().links().regex(targetRequestsRegex).all();
	    for (String nextLink : nextLinks) {
	    	Request request = new Request();
	    	request.setUrl(nextLink);
	    	Map<String, Object> extras = new HashMap<String, Object>();
	     	if (page.getRequest().getExtra("_level") == null) {
	        	extras.put("_level",  1);
	       	} else {
	         	//获取上层页面的深度再加一就是这个URL的深度
	         	extras.put("_level", (Integer) page.getRequest().getExtra("_level") + 1);
	       	}
	     	request.setExtras(extras);
	     	page.addTargetRequest(request);
	 	}
		// 添加关键字匹配项
		int count = 0;
		for (int i = 0; i < currencyList.size(); i++) {
			// System.out.println(currencyList.get(i).toString());
			// title中必须有币种对关键词，正文中必须符合币种对正则表达式
			if ((page.getHtml().xpath("//title").regex(currencyList.get(i).getName()).get() != null
						)// 去除错误页面	
					//|| page.getHtml().xpath("//title").regex(currencyList.get(i).getShortName()).get() != null)
					&& (page.getHtml().regex(currencyList.get(i).getNameRegex()).get() != null
							|| page.getHtml().regex(currencyList.get(i).getShortNameRegex()).get() != null)) {
				page.putField("currency" + count, currencyList.get(i).getId());
				count++;
			}
		}
		page.putField("currencyCount", count);
		// 如果所有关键都无法匹配
		if (count == 0) {
			ifSkip = true;
		}
		if (ifSkip) {
			page.setSkip(true);
		} else {
			// String todayRegex = "[0-9]{4}[-/][0-9]{2}[-/][0-9]{2}) ";
			// 添加时间
			page.putField("date", date);
			// 添加链接
			String links = "";
			if (page.getUrl().all().size() > 0) {
				links = page.getUrl().all().get(0);
			}
			if (links.length() > 200) {
				links = links.substring(0, 200);
			}
			page.putField("links", links);
			// 添加标题
			String title = "";
			if (page.getHtml().xpath("//title").all().size() > 0) {
				title = page.getHtml().xpath("//title").all().get(0).replaceAll("<title>", "").replaceAll("</title>",
						"");
			}
			if (title.length() > 200) {
				title = title.substring(0, 200);
			}
			page.putField("title", title);
			// 添加作者
			String author = "";
			if (page.getHtml().regex("(作者[:： ]+([\\s\\S])*?</)").all().size() > 0) {
				author = page.getHtml().regex("(作者[:： ]+([\\s\\S])*?</)").all().get(0).replace("</", "");
			}
			if (author.length() > 100) {
				author = author.substring(0, 100);
			}
			page.putField("author", author);
			// 添加来源
			String source = "";
			if (page.getHtml().regex("(本文来源[:： ]+([\\s\\S])*?</)").all().size() > 0) {
				source = page.getHtml().regex("(本文来源[:： ]+([\\s\\S])*?</)").all().get(0).replace("</", "");
			} else if (page.getHtml().regex("(来源[:： ]+([\\s\\S])*?</)").all().size() > 0) {
				source = page.getHtml().regex("(来源[:： ]+([\\s\\S])*?</)").all().get(0).replace("</", "");
			}
			if (source.length() > 100) {
				source = source.substring(0, 100);
			}
			source = source.replaceAll("[^\\u4e00-\\u9fa5：]", "");
			page.putField("source", source);

			// 添加文章内容
			String content = "";
			if (!page.getHtml().xpath("//div").all().isEmpty()) {
				int max = 0;
				List<String> contentList = page.getHtml().xpath("//div").all();
				for (int i = 0; i < contentList.size(); i++) {
					String tmp = ZoroUtil.divFormat(contentList.get(i));
					if (null != tmp && !tmp.contains("<ul") &&tmp.contains(currencyList.get(0).getName())) {
						if (ZoroUtil.getChineseCount(tmp) > max) {
							max = ZoroUtil.getChineseCount(tmp);
							// 只保留中文
							content = tmp.replaceAll("[^\\u4e00-\\u9fa5，。]", "");
						}
					}
				}
			}
			if(content.equals("")) {
				content=title;
			}else {
				content = content.replaceAll("微软雅黑", "").replaceAll("宋体", "").replaceAll("楷体", "").replaceAll("黑体", "").replace("原标题", "");
				content = ZoroUtil.getMainContent(content, currencyList.get(0).getName());
			}
			if (content.length() > 5000) {
				content = content.substring(0, 5000);
			}
			page.putField("content", content);
		}
	}

	@Override
	public Site getSite() {
		return site;
	}

}
