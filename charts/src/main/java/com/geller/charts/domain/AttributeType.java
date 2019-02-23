package com.geller.charts.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "attributeType")
public class AttributeType {
	@Id
	private String id;
	 
	private String description;
	 
	//TODO replace with enum for single,select, multi select
	private Boolean enableList;
	  
	 
	private String className;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Boolean getEnableList() {
		return enableList;
	}

	public void setEnableList(Boolean enableList) {
		this.enableList = enableList;
	}
	  
	  
	  
	  
}
