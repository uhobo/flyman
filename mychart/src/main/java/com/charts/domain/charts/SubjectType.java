package com.charts.domain.charts;

public enum SubjectType {
	 SERIE(1, "Series", "seriesDiagramHandler", "seriesMenuHandler"),
	 CATEGORY(2, "Category", "categoryDiagramHandler", "categoryMenuHandler"),
	 TOPIC(3, "Topic", "topicDiagramHandler", "topicMenuHandler");
	 
	private Integer value;
	private String caption;
	private String diagramHandlerBeanName;
	private String menuHandlerBeanName;
	
	SubjectType(Integer value, String caption, String diagramHandlerBeanName, String menuHandlerBeanName){
		this.value = value;
		this.caption = caption;
		this.diagramHandlerBeanName = diagramHandlerBeanName;
		this.menuHandlerBeanName = menuHandlerBeanName;
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
	

	public String getMenuHandlerBeanName() {
		return menuHandlerBeanName;
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
