package com.icbc.zoro.trend.bean;

public class TrendBean {
	String currency;
	String date;
	String pos_score;
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
	public String getPos_score() {
		return pos_score;
	}
	public void setPos_score(String pos_score) {
		this.pos_score = pos_score;
	}
	@Override
	public String toString() {
		return "TrendBean [currency=" + currency + ", date=" + date + ", pos_score=" + pos_score 
				+  "]";
	}

}
