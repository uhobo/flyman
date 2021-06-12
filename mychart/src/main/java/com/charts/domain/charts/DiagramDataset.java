package com.charts.domain.charts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class DiagramDataset extends ChartDataset {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private double categoryPercentage = 0.8;
	
	private double barPercentage = 0.9;
	private String type;
	
	private boolean fill = true;
	private Boolean spanGaps;
    private String pointStyle;
    private Integer order;
    
    @JsonInclude(Include.NON_NULL)
	private String stack;
    
    
    public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getCategoryPercentage() {
		return categoryPercentage;
	}
	public void setCategoryPercentage(double categoryPercentage) {
		this.categoryPercentage = categoryPercentage;
	}
	private String backgroundColor;
    private String borderColor;
    
	public String getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public String getBorderColor() {
		return borderColor;
	}
	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}
	public boolean isFill() {
		return fill;
	}
	public void setFill(boolean fill) {
		this.fill = fill;
	}
	public double getBarPercentage() {
		return barPercentage;
	}
	public void setBarPercentage(double barPercentage) {
		this.barPercentage = barPercentage;
	}
	public Boolean getSpanGaps() {
		return spanGaps;
	}
	public void setSpanGaps(Boolean spanGaps) {
		this.spanGaps = spanGaps;
	}
	public String getPointStyle() {
		return pointStyle;
	}
	public void setPointStyle(String pointStyle) {
		this.pointStyle = pointStyle;
	}
	public String getStack() {
		return stack;
	}
	public void setStack(String stack) {
		this.stack = stack;
	}

    
    
    
}
