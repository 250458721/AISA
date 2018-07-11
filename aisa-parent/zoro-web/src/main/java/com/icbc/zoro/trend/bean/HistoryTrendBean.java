package com.icbc.zoro.trend.bean;

public class HistoryTrendBean {
	String currency;
	String date;
	String endPrice;
	String changeRate;
	String posScore;
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
	public String getChangeRate() {
		return changeRate;
	}
	public void setChangeRate(String changeRate) {
		this.changeRate = changeRate;
	}
	public String getPosScore() {
		return posScore;
	}
	public void setPosScore(String posScore) {
		this.posScore = posScore;
	}
	@Override
	public String toString() {
		return "HistoryTrendBean [currency=" + currency + ", date=" + date + ", endPrice=" + endPrice + ", changeRate="
				+ changeRate + ", posScore=" + posScore + "]";
	}
	

}
