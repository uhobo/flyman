package com.geller.charts.domain.menus;

import java.util.ArrayList;
import java.util.List;

public class MenuItem {
	
	private String label;
	private String icon;
	private String data;
	
	
	private List<MenuItem> children = new ArrayList<>();
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public List<MenuItem> getChildren() {
		return children;
	}
	public void setChildren(List<MenuItem> children) {
		this.children = children;
	}
	
	
	
	
	
	
	
	
}
