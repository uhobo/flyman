package com.charts.service.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.util.StringUtils;

import com.charts.domain.SeriesItem;


public class ChartAppUtil {
	
	public static String getSeriesLabel(SeriesItem seriesItem) {
		return StringUtils.isEmpty(seriesItem.getTitle())? 
							String.valueOf(seriesItem.getValue()) :
								seriesItem.getTitle() + " (" + String.valueOf(seriesItem.getValue()) + ")";
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = BigDecimal.valueOf(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	public static Double getDouble(Object value) {
		if(value == null || StringUtils.isEmpty(value.toString())) {
			return Double.valueOf(0);
		}
		
		return Double.valueOf(value.toString());  	
	 }
}
