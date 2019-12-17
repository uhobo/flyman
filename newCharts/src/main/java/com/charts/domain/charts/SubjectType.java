package com.charts.domain.charts;

public enum SubjectType {
	 SERIE(1, "Series", "seriesDiagramHandler"),
	 CATEGORY(2, "Category", "categoryDiagramHandler"),
	 TOPIC(3, "Topic", "topicDiagramHandler");
	 
	private Integer value;
	private String caption;
	private String diagramHandlerBeanName;
	
	SubjectType(Integer value, String caption, String diagramHandlerBeanName){
		this.value = value;
		this.caption = caption;
		this.diagramHandlerBeanName = diagramHandlerBeanName;
	}

	public Integer getValue() {
		return value;
	}
	
	
	public String getCaption() {
		return caption;
	}
	
	

	public String getDiagramHandlerBeanName() {
		return diagramHandlerBeanName;
	}

	public static SubjectType getSubjectType(Integer value) {
		for(SubjectType type : SubjectType.values()) {
			if(type.getValue().equals(value)) {
				return type;
			}
		}
		return null;
	}
}
