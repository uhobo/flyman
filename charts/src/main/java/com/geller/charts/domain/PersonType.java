package com.geller.charts.domain;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "personType")
public class PersonType {
	 @Id
	 private String id;
	 
	
	 private String description;
	 
	 private Set<Attribute> templateAttributes;

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

	public Set<Attribute> getTemplateAttributes() {
		return templateAttributes;
	}

	public void setTemplateAttributes(Set<Attribute> templateAttributes) {
		this.templateAttributes = templateAttributes;
	}

	
	 
	 
}
