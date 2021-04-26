package org.jack.plategen.generator;

import org.junit.Assert;
import org.junit.Test;

public class ScaleConverterTest {

	private ScaleConverter sc = new ScaleConverter();
	
	@Test
	public void testScaleConversion() {
		Assert.assertEquals(1.0/1, sc.convertToNumericScale("1:1"), 0.001d);
		Assert.assertEquals(1.0/8, sc.convertToNumericScale("1:8"), 0.001d);
		Assert.assertEquals(1.0/9, sc.convertToNumericScale("1:9"), 0.001d);
		Assert.assertEquals(1.0/12, sc.convertToNumericScale("1:12"), 0.001d);
		Assert.assertEquals(1.0/18, sc.convertToNumericScale("1:18"), 0.001d);
		Assert.assertEquals(1.0/24, sc.convertToNumericScale("1:24"), 0.001d);
		Assert.assertEquals(1.0/25, sc.convertToNumericScale("1:25"), 0.001d);
		Assert.assertEquals(1.0/32, sc.convertToNumericScale("1:32"), 0.001d);
		Assert.assertEquals(1.0/43, sc.convertToNumericScale("1:43"), 0.001d);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testWrongScaleFormat() {
		sc.convertToNumericScale("1:a1");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testWrongScaleNoColon() {
		sc.convertToNumericScale("118");
	}
}
