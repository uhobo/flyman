package com.charts.domain.charts;

import java.io.Serializable;

public class ExportExcelFile implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String fileName;
	
	private byte byteArr[];

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getByteArr() {
		return byteArr;
	}

	public void setByteArr(byte[] byteArr) {
		this.byteArr = byteArr;
	}
	
	
}
