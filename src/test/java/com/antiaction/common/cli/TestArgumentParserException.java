package com.antiaction.common.cli;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestArgumentParserException {

	@Test
	public void test_argumentparser_exception() {
		ArgumentParserException e;

		e = new ArgumentParserException();
		Assert.assertNotNull( e );
		Assert.assertNull(e.getMessage());
		Assert.assertNull(e.getCause());

		e = new ArgumentParserException( "Message!" );
		Assert.assertNotNull( e );
		Assert.assertEquals("Message!", e.getMessage());
		Assert.assertNull(e.getCause());

		Throwable t = new Throwable();

		e = new ArgumentParserException( t );
		Assert.assertNotNull( e );
		Assert.assertEquals(t.toString(), e.getMessage());
		Assert.assertEquals(t, e.getCause());

		e = new ArgumentParserException( "Message!", t );
		Assert.assertNotNull( e );
		Assert.assertEquals("Message!", e.getMessage());
		Assert.assertEquals(t, e.getCause());
	}

}
