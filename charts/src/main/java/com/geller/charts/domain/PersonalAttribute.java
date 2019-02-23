package com.geller.charts.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class PersonalAttribute {
	
	@DBRef
	private AttributeType addributeType;
	
	
	//replace with type Object 
	@DBRef
	Set<AttributeValue> selectedAttributesList = new HashSet<>();
	

	@Deprecated
	private Object attributeValue;

	public AttributeType getAddributeType() {
		return addributeType;
	}

	public void setAddributeType(AttributeType addributeType) {
		this.addributeType = addributeType;
	}

	public Set<AttributeValue> getSelectedAttributesList() {
		return selectedAttributesList;
	}

	public void setSelectedAttributesList(Set<AttributeValue> selectedAttributesList) {
		this.selectedAttributesList = selectedAttributesList;
	}

	public Object getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(Object attributeValue) {
		this.attributeValue = attributeValue;
	}
	
	
	
	
}
