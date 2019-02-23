package com.geller.charts.domain.menus;

import java.util.ArrayList;
import java.util.List;

import com.geller.charts.domain.inputModel.SelectItem;

public class MenuItem {
	
	private String label;
	private String icon;
	private Object data;
	
	
	private List<MenuItem> children = new ArrayList<>();
	
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
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
