package com.antiaction.common.cli;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestOption {

	@Test
	public void test_option() {
		Option option;
		Option option2;

		option = new Option();
		Assert.assertNotNull(option);

		Assert.assertEquals(0, option.type);
		Assert.assertEquals(null, option.command);
		Assert.assertEquals(null, option.shortName);
		Assert.assertEquals(null, option.longName);
		Assert.assertEquals(0, option.id);
		Assert.assertEquals(0, option.subId);
		Assert.assertEquals(null, option.desc);
		Assert.assertEquals(false, option.bValueRequired);
		Assert.assertEquals(null, option.shortValueOptions);
		Assert.assertEquals(null, option.bShortValueOptional);
		Assert.assertEquals(0, option.min);
		Assert.assertEquals(0, option.max);
		Assert.assertEquals(false, option.bStopParsing);

		option2 = option.setValueRequired();
		Assert.assertTrue(option == option2);

		Assert.assertNotNull(option);
		Assert.assertEquals(0, option.type);
		Assert.assertEquals(null, option.command);
		Assert.assertEquals(null, option.shortName);
		Assert.assertEquals(null, option.longName);
		Assert.assertEquals(0, option.id);
		Assert.assertEquals(0, option.subId);
		Assert.assertEquals(null, option.desc);
		Assert.assertEquals(true, option.bValueRequired);
		Assert.assertEquals(null, option.shortValueOptions);
		Assert.assertEquals(null, option.bShortValueOptional);
		Assert.assertEquals(0, option.min);
		Assert.assertEquals(0, option.max);
		Assert.assertEquals(false, option.bStopParsing);

		option2 = option.setStopParsing();
		Assert.assertNotNull(option);
		Assert.assertEquals(0, option.type);
		Assert.assertEquals(null, option.command);
		Assert.assertEquals(null, option.shortName);
		Assert.assertEquals(null, option.longName);
		Assert.assertEquals(0, option.id);
		Assert.assertEquals(0, option.subId);
		Assert.assertEquals(null, option.desc);
		Assert.assertEquals(true, option.bValueRequired);
		Assert.assertEquals(null, option.shortValueOptions);
		Assert.assertEquals(null, option.bShortValueOptional);
		Assert.assertEquals(0, option.min);
		Assert.assertEquals(0, option.max);
		Assert.assertEquals(true, option.bStopParsing);
	}

}
