package com.charts.domain;

import java.io.Serializable;

public class DataHeader implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	private Integer index;
	private boolean show;
	private boolean disabled;
	
	public DataHeader() {
		this.show =true;
	}
	
	public DataHeader(String title, Integer index){
		this.show = true;
		this.index = index;
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isShow() {
		return show;
	}
	public void setShow(boolean show) {
		this.show = show;
	}

	
	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
	
	
	
	
	
}
