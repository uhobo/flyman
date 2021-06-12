package com.charts.domain.charts;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ChartResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer chartType;
	
	private String chartTypeStr;
	
	private ChartData data;
	
	private Map<String, Object> options;

	
	
	public String getChartTypeStr() {
		return chartTypeStr;
	}

	public void setChartTypeStr(String chartTypeStr) {
		this.chartTypeStr = chartTypeStr;
	}

	public Integer getChartType() {
		return chartType;
	}

	public void setChartType(Integer chartType) {
		this.chartType = chartType;
	}

	public ChartData getData() {
		return data;
	}

	public void setData(ChartData data) {
		this.data = data;
	}

	public Map<String, Object> getOptions() {
		return options;
	}

	public void setOptions(Map<String, Object> options) {
		this.options = options;
	}

	@Override
	public String toString() {
		ObjectMapper map = new ObjectMapper();
		try {
			map.setSerializationInclusion(Include.NON_NULL);
			return map.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	} 
	
	
	
}
