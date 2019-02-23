package com.geller.charts.domain.charts;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BarChartDataSet implements ChartDataSetInteface {
	private String label;
	private List<BigDecimal> data = new ArrayList<BigDecimal>();
	private String backgroundColor;
	
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public List<BigDecimal> getData() {
		return data;
	}
	public void setData(List<BigDecimal> data) {
		this.data = data;
	}
	public String getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
	
}
