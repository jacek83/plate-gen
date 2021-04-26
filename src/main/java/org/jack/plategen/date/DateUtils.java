package org.jack.plategen.date;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
	private DateUtils() {
		// to avoid instantiation
	}
	
	public static String getCurrentTs() {
		return getTsForDateTime(LocalDateTime.now());
	}

	public static String getTsForDateTime(LocalDateTime dt) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
		return dt.format(formatter);
	}
}