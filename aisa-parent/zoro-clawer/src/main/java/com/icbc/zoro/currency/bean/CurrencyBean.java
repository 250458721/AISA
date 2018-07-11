package com.icbc.zoro.currency.bean;

public class CurrencyBean {
	// 币种对正则表达式
	String nameRegex;
	// 币种对简写正则表达式
	String shortNameRegex;
	// 币种编号
	String id;
	// 币种名称
	String name;
	// 币种名称缩写
	String shortName;
	public String getNameRegex() {
		return nameRegex;
	}
	public void setNameRegex(String nameRegex) {
		this.nameRegex = nameRegex;
	}
	public String getShortNameRegex() {
		return shortNameRegex;
	}
	public void setShortNameRegex(String shortNameRegex) {
		this.shortNameRegex = shortNameRegex;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	@Override
	public String toString() {
		return "CurrencyBean [nameRegex=" + nameRegex + ", shortNameRegex=" + shortNameRegex + ", id=" + id + ", name="
				+ name + ", shortName=" + shortName + "]";
	}

	
}
