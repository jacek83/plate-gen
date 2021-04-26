package org.jack.plategen.generator;

import org.springframework.beans.factory.annotation.Value;

public class ScaleConverter {
	@Value("${images.scale}")
	private String imagesScale;

	public double getScale() {
		String propertyValue = this.imagesScale;
		return this.convertToNumericScale(propertyValue);
	}

	double convertToNumericScale(String propertyValue) {
		int colonIdx = propertyValue.lastIndexOf(58);
		if (colonIdx != -1) {
			String scaleNumber = propertyValue.substring(colonIdx + 1);
			Double scaleVal = Double.parseDouble(scaleNumber);
			return 1.0d / scaleVal;
		} else {
			throw new IllegalArgumentException("Invalid scale format: " + propertyValue + " Expected sth like 1:18");
		}
	}
}