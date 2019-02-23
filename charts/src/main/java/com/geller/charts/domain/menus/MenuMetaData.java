package com.geller.charts.domain.menus;

import java.util.List;

import com.geller.charts.domain.charts.ChartCriteria;
import com.geller.charts.domain.inputModel.SelectItem;

public class MenuMetaData {

	private ChartCriteria chartCriteria;
	
	private List<SelectItem> chartSelectList;
	
	public List<SelectItem> getChartSelectList() {
		return chartSelectList;
	}
	public void setChartSelectList(List<SelectItem> chartSelectList) {
		this.chartSelectList = chartSelectList;
	}
	public ChartCriteria getChartCriteria() {
		return chartCriteria;
	}
	public void setChartCriteria(ChartCriteria chartCriteria) {
		this.chartCriteria = chartCriteria;
	}
	
	
	
}
