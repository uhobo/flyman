package com.charts.service.util;

import java.awt.Color;
import java.util.Random;

public class ColorUtil {
	private static Random random = new Random();
	private static final String ColorPool[] = {
			"#9CCC65", 
			"#565656",  
			"#FF6384",
            "#36A2EB",
            "#FFCE56",
            "#C71585",
            "#DDA0DD0",
            "#FF6347",
            "#4682B4",
            "#FFA500"};
	
	public static String allocateColor(Integer place) {
		if(place < ColorPool.length) {
			return ColorPool[place];
		}
		return randomColor();
	}
	
	private static String randomColor() {
		final float hue = random.nextFloat();
		// Saturation between 0.1 and 0.3
		final float saturation = (random.nextInt(2000) + 1000) / 10000f;
		final float luminance = 0.9f;
		final Color color = Color.getHSBColor(hue, saturation, luminance);
		return "#"+Integer.toHexString(color.getRGB()).substring(2).toUpperCase();
	}
}
