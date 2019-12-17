package com.charts.service.util;

import org.springframework.util.StringUtils;

import com.charts.domain.SeriesItem;


public class ChartAppUtil {
	
	public static String getSeriesLabel(SeriesItem seriesItem) {
		return StringUtils.isEmpty(seriesItem.getTitle())? 
							String.valueOf(seriesItem.getValue()) :
								seriesItem.getTitle() + " (" + String.valueOf(seriesItem.getValue()) + ")";
	}
}
