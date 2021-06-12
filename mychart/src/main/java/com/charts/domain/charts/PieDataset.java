package com.charts.domain.charts;

import java.util.ArrayList;
import java.util.List;

public class PieDataset extends ChartDataset {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private List<String> backgroundColor = new ArrayList<>();
    private List<String> hoverBackgroundColor = new ArrayList<>();
    private List<Double> originalValues = new ArrayList<>();
	public List<String> getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(List<String> backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public List<String> getHoverBackgroundColor() {
		return hoverBackgroundColor;
	}
	public void setHoverBackgroundColor(List<String> hoverBackgroundColor) {
		this.hoverBackgroundColor = hoverBackgroundColor;
	}
	public List<Double> getOriginalValues() {
		return originalValues;
	}
	public void setOriginalValues(List<Double> originalValues) {
		this.originalValues = originalValues;
	}
	
	
}
