package com.charts.domain;


public class InfoData {
	public enum INFO_TYPE{
		GENERAL,
		GROUP
	}
	private String title;
	private INFO_TYPE infoType;
	
	private String content ="";
	private Integer count =  0;
	
	
	public InfoData(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	
}
