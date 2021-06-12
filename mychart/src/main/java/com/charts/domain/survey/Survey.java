package com.charts.domain.survey;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;

public class Survey implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    private String id;
	
	private String title;
	
	private String description;
	
	private List<AnswerScale> scaleList;
	
	private List<SurveyQuestion> questionList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<AnswerScale> getScaleList() {
		return scaleList;
	}

	public void setScaleList(List<AnswerScale> scaleList) {
		this.scaleList = scaleList;
	}

	public List<SurveyQuestion> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<SurveyQuestion> questionList) {
		this.questionList = questionList;
	}
	
	
	
	
	
	
}
