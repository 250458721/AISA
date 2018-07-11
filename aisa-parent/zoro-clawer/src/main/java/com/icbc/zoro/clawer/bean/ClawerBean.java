package com.icbc.zoro.clawer.bean;

public class ClawerBean {
	
	@Override
	public String toString() {
		return "ClawerBean [url=" + url + ", currency=" + currency + ", date=" + date + ", links=" + links + ", title="
				+ title + ", author=" + author + ", source=" + source + ", content=" + content + ", submitTime="
				+ submitTime + ", updateTime=" + updateTime + ", status=" + status + ", posScore=" + posScore
				+ ", negScore=" + negScore + "]";
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
	public String getLinks() {
		return links;
	}
	public void setLinks(String links) {
		this.links = links;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPosScore() {
		return posScore;
	}
	public void setPosScore(String posScore) {
		this.posScore = posScore;
	}
	public String getNegScore() {
		return negScore;
	}
	public void setNegScore(String negScore) {
		this.negScore = negScore;
	}

	String url;
	String currency;
	String date;
	String links;
	String title;
	String author;
	String source;
	String content;
	String submitTime;
	String updateTime;
	String status;
	String posScore;
	String negScore;
}
