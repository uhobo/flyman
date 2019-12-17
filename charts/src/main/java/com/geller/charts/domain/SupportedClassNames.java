package com.geller.charts.domain;


public enum SupportedClassNames {
	INTEGER(Integer.class.getName()),
	STRING( String.class.getName());
	
	private String className;

	SupportedClassNames(String className){
		this.className = className;
	}
	
	public String getClassName() {
		return this.className;
	}
	
	public Object createInstance() throws Exception{
		Class<?> c= Class.forName(className);
		return c.getDeclaredConstructor().newInstance();
	}
}
