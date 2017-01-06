package com.antiaction.common.cli;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestOptions {

	@Test
	public void test_options() {
		Option option;
		Options options = new Options();
		Assert.assertNotNull( options );

		try {
			options.addOption( null, null, 1, 1, ".." );
			Assert.fail( "Exception expected!" );
		}
		catch (ArgumentParserException e) {
		}
		try {
			options.addOption( null, "", 1, 1, ".." );
			Assert.fail( "Exception expected!" );
		}
		catch (ArgumentParserException e) {
		}
		try {
			options.addOption( "", null, 1, 1, ".." );
			Assert.fail( "Exception expected!" );
		}
		catch (ArgumentParserException e) {
		}
		try {
			options.addOption( "", "", 1, 1, ".." );
			Assert.fail( "Exception expected!" );
		}
		catch (ArgumentParserException e) {
		}
		try {
			options.addOption( "l", "", 1, 1, ".." );
			Assert.fail( "Exception expected!" );
		}
		catch (ArgumentParserException e) {
		}
		try {
			options.addOption( "", "long", 1, 1, ".." );
			Assert.fail( "Exception expected!" );
		}
		catch (ArgumentParserException e) {
		}
		try {
			options.addOption( "-", null, 1, 1, ".." );
			Assert.fail( "Exception expected!" );
		}
		catch (ArgumentParserException e) {
		}
		try {
			options.addOption( null, "--", 1, 1, ".." );
			Assert.fail( "Exception expected!" );
		}
		catch (ArgumentParserException e) {
		}
		try {
			options.addOption( null, "--prefix=", 1, 1, ".." );
			Assert.fail( "Exception expected!" );
		}
		catch (ArgumentParserException e) {
		}
		try {
			options.addOption( null, "--fail--fast", 1, 1, ".." );
			Assert.fail( "Exception expected!" );
		}
		catch (ArgumentParserException e) {
		}

		try {
			option = options.addOption( "-l", null, 1, 2, "First command" );
			Assert.assertNotNull( option);
			Assert.assertEquals( Option.T_OPTION, option.type );
			Assert.assertEquals( "l", option.shortName );
			Assert.assertEquals( null, option.longName );
			Assert.assertEquals( 1, option.id );
			Assert.assertEquals( 2, option.subId );
			Assert.assertEquals( "First command", option.desc );

			option = options.addOption( null, "--long", 2, 3, "Second command" );
			Assert.assertNotNull( option );
			Assert.assertEquals( Option.T_OPTION, option.type );
			Assert.assertEquals( null, option.shortName );
			Assert.assertEquals( "long", option.longName );
			Assert.assertEquals( 2, option.id );
			Assert.assertEquals( 3, option.subId );
			Assert.assertEquals( "Second command", option.desc );

			option = options.addOption( "-l", "--long", 3, 4, "Third command" );
			Assert.assertNotNull( option);
			Assert.assertEquals( Option.T_OPTION, option.type );
			Assert.assertEquals( "l", option.shortName );
			Assert.assertEquals( "long", option.longName );
			Assert.assertEquals( 3, option.id );
			Assert.assertEquals( 4, option.subId );
			Assert.assertEquals( "Third command", option.desc );

			option = options.addOption( null, "--sha256", 4, 5, "Fourth command" );
			Assert.assertNotNull( option );
			Assert.assertEquals( Option.T_OPTION, option.type );
			Assert.assertEquals( null, option.shortName );
			Assert.assertEquals( "sha256", option.longName );
			Assert.assertEquals( 4, option.id );
			Assert.assertEquals( 5, option.subId );
			Assert.assertEquals( "Fourth command", option.desc );

			option = options.addOption( null, "--fail-fast", 5, 6, "Sixth command" );
			Assert.assertNotNull( option );
			Assert.assertEquals( Option.T_OPTION, option.type );
			Assert.assertEquals( null, option.shortName );
			Assert.assertEquals( "fail-fast", option.longName );
			Assert.assertEquals( 5, option.id );
			Assert.assertEquals( 6, option.subId );
			Assert.assertEquals( "Sixth command", option.desc );
		}
		catch (ArgumentParserException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
	}

}
