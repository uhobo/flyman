package com.geller.charts.domain.charts;

import java.util.List;

import com.geller.charts.domain.inputModel.SelectItem;

public class ChartDataWrapper {
	
	private String chartType;
	private ChartData chartData;
	private ChartOption options;
	
	
	public ChartData getChartData() {
		return chartData;
	}
	public void setChartData(ChartData chartData) {
		this.chartData = chartData;
	}
	public ChartOption getOptions() {
		return options;
	}
	public void setOptions(ChartOption options) {
		this.options = options;
	}
	public String getChartType() {
		return chartType;
	}
	public void setChartType(String chartType) {
		this.chartType = chartType;
	}
	
	
	
}
