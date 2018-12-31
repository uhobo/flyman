package com.geller.charts.domain.charts;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ChartDataSet {
	private String label;
	private List<BigDecimal> data = new ArrayList<BigDecimal>();
	
	
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
	
	
}
