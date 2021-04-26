package org.jack.plategen.generator.date;

import java.time.LocalDateTime;

import org.jack.plategen.date.DateUtils;
import org.junit.Assert;
import org.junit.Test;

public class DateUtilsTest {

	@Test
	public void testDateFormat() {
		Assert.assertEquals("20201007_223311", DateUtils.getTsForDateTime(LocalDateTime.of(2020, 10, 7, 22, 33, 11)));
		Assert.assertEquals("20220312_020103", DateUtils.getTsForDateTime(LocalDateTime.of(2022, 3, 12, 2, 1, 3)));
		Assert.assertEquals("20190117_003301", DateUtils.getTsForDateTime(LocalDateTime.of(2019, 1, 17, 00, 33, 01)));
	}
	
}
