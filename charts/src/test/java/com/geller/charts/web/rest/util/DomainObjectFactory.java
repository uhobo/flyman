package com.geller.charts.web.rest.util;

import com.geller.charts.domain.*;

public class DomainObjectFactory {

	public static Attribute createAttribute(String name, AttributeType attributeType) {
		Attribute attribute = new Attribute();
		attribute.setName(name);
		attribute.setAttributeType(attributeType);
		return attribute;
	}
}
