package com.geller.charts.domain.charts;

import java.util.List;


public class ChartCriteria {
	/**
	 * support
	 * 1. Percent 
	 * 2. Amount 
	 */
	private String chartType;
	private String surveyId;
	private String respondingId;
	//relevant only if charType is percent
	private Integer questionId;
	/**
	 * True: x = scale y = sum of scale 
	 * False: x = asked y =    
	 */
	//private boolean byScale;
	public String getChartType() {
		return chartType;
	}
	public void setChartType(String chartType) {
		this.chartType = chartType;
	}
	public String getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}
	public String getRespondingId() {
		return respondingId;
	}
	public void setRespondingId(String respondingId) {
		this.respondingId = respondingId;
	}
	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	
	
	//private List<SurveyResult> result; //to prevent fetching again, send already exists data
	
	
	
	
}
