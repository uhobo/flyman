package com.charts.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImportExcelData {
	private String sheetName;
	private List<List<Object>> data = new ArrayList<>();
	private List<Double> seriesList;
	private Map<String,String> errorSuspect = new HashMap<>();
	
	
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public List<List<Object>>  getData() {
		return data;
	}
	public void setData(List<List<Object>> data) {
		this.data = data;
	}
	public List<Double> getSeriesList() {
		return seriesList;
	}
	public void setSeriesList(List<Double> seriesList) {
		this.seriesList = seriesList;
	}
	public Map<String, String> getErrorSuspect() {
		return errorSuspect;
	}
	public void setErrorSuspect(Map<String, String> errorSuspect) {
		this.errorSuspect = errorSuspect;
	}
	
	
	
	
}
