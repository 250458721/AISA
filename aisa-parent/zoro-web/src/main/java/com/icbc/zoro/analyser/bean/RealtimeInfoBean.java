package com.icbc.zoro.analyser.bean;

public class RealtimeInfoBean {
	String currency;
	String date;
	String endPrice;
	String startPrice;
	String topPrice;
	String bottomPrice;
	String changeRate;

	@Override
	public String toString() {
		return "RealtimeInfoBean [currency=" + currency + ", date=" + date + ", endPrice=" + endPrice + ", startPrice="
				+ startPrice + ", topPrice=" + topPrice + ", bottomPrice=" + bottomPrice + ", changeRate=" + changeRate
				+ "]";
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getEndPrice() {
		return endPrice;
	}
	public void setEndPrice(String endPrice) {
		this.endPrice = endPrice;
	}
	public String getStartPrice() {
		return startPrice;
	}
	public void setStartPrice(String startPrice) {
		this.startPrice = startPrice;
	}
	public String getTopPrice() {
		return topPrice;
	}
	public void setTopPrice(String topPrice) {
		this.topPrice = topPrice;
	}
	public String getBottomPrice() {
		return bottomPrice;
	}
	public void setBottomPrice(String bottomPrice) {
		this.bottomPrice = bottomPrice;
	}
	public String getChangeRate() {
		return changeRate;
	}
	public void setChangeRate(String changeRate) {
		this.changeRate = changeRate;
	}
	
}
