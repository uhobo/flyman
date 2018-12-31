package com.geller.charts.domain;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class AskedResult {
	
	//@DBRef
	//@Field("asked")
	//@JsonIgnoreProperties("")
	@Transient
	private Asked asked;
	
	@Field("askedId")
	@JsonIgnoreProperties("")
	private String askedId;
	
	@Field("answers")
	@JsonIgnoreProperties("")
	private Map<Integer, Integer> answers = new HashMap<>();
	
	
	public Asked getAsked() {
		return asked;
	}
	public void setAsked(Asked asked) {
		this.asked = asked;
	}
	public Map<Integer, Integer> getAnswers() {
		return answers;
	}
	
	public void setAnswers(Map<Integer, Integer> answers) {
		this.answers = answers;
	}
	public String getAskedId() {
		return askedId;
	}
	public void setAskedId(String askedId) {
		this.askedId = askedId;
	}
	
	
	
	
}
