package com.geller.charts.domain.charts;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PieChartDataSet implements ChartDataSetInteface{
	
	private List<BigDecimal> data = new ArrayList<BigDecimal>();
	private List<String> backgroundColor = new ArrayList<String>();
	
	public List<BigDecimal> getData() {
		return data;
	}
	public void setData(List<BigDecimal> data) {
		this.data = data;
	}
	public List<String> getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(List<String> backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
	
	
}
