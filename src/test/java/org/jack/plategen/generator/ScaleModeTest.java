package org.jack.plategen.generator;

import org.junit.Test;
import org.junit.Assert;

public class ScaleModeTest {

	@Test
	public void testEnum() {
		Assert.assertEquals(ScalingMode.HEIGHT, ScalingMode.valueOf("HEIGHT"));
		Assert.assertEquals(ScalingMode.WIDTH, ScalingMode.valueOf("WIDTH"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEnumException() {
		ScalingMode.valueOf("hight");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEnumException2() {
		ScalingMode.valueOf("WID");
	}

}
