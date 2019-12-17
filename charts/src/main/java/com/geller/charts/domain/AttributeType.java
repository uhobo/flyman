package com.geller.charts.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "attributeType")
public class AttributeType {
	@Id
	private String id;
	 
	private String description;
	 
	
	private InputTypeEnum inputType;
	  
	 
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

	public InputTypeEnum getInputType() {
		return inputType;
	}

	public void setInputType(InputTypeEnum inputType) {
		this.inputType = inputType;
	}

	
	  
	  
	  
}
