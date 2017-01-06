package com.antiaction.common.cli;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestCommandLine {

	@Test
	public void test_commandline() {
		Option option;
		Options options;
		CommandLine cmdLine;
		Argument argument;

		try {
			cmdLine = ArgumentParser.parse( "-d C:\\*.gz".split( " " ), null, null );
			Assert.fail( "Exception expected!" );
		}
		catch (ArgumentParserException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
		catch (IllegalArgumentException e) {
		}

		try {
			options = new Options();
			options.addOption( "-d", null, 1, 100, "decompress" );

			cmdLine = ArgumentParser.parse( null, options, null );
			Assert.assertNull( cmdLine );

			cmdLine = ArgumentParser.parse( new String[ 0 ], options, null );
			Assert.assertNotNull( cmdLine );
			Assert.assertEquals( 0, cmdLine.idMap.size() );
			Assert.assertEquals( 0, cmdLine.switchArgsList.size() );
			Assert.assertEquals( 0, cmdLine.unnamedArgsList.size() );

			cmdLine = ArgumentParser.parse( null, options, cmdLine );
			Assert.assertNotNull( cmdLine );
			Assert.assertEquals( 0, cmdLine.idMap.size() );
			Assert.assertEquals( 0, cmdLine.switchArgsList.size() );
			Assert.assertEquals( 0, cmdLine.unnamedArgsList.size() );

			//

			cmdLine = ArgumentParser.parse( "-d C:\\*.gz".split( " " ), options, null );
			Assert.assertNotNull( cmdLine );
			Assert.assertEquals( 1, cmdLine.idMap.size() );
			Assert.assertEquals( 1, cmdLine.switchArgsList.size() );
			Assert.assertEquals( 0, cmdLine.unnamedArgsList.size() );

			argument = cmdLine.idMap.get( 1 );
			Assert.assertNotNull( argument );
			Assert.assertNull( argument.value );
			Assert.assertEquals( 0, argument.values.size() );
			option = argument.option;
			Assert.assertNotNull( option );
			Assert.assertEquals( 1, option.id  );

			//

			options = new Options();
			options.addOption( null, "--decompress", 1, 100, "decompress" );

			cmdLine = ArgumentParser.parse( "--decompress C:\\*.gz".split( " " ), options, null );
			Assert.assertNotNull( cmdLine );
			Assert.assertEquals( 1, cmdLine.idMap.size() );
			Assert.assertEquals( 1, cmdLine.switchArgsList.size() );
			Assert.assertEquals( 0, cmdLine.unnamedArgsList.size() );

			argument = cmdLine.idMap.get( 1 );
			Assert.assertNotNull( argument );
			Assert.assertNull( argument.value );
			Assert.assertEquals( 0, argument.values.size() );
			option = argument.option;
			Assert.assertNotNull( option );
			Assert.assertEquals( 1, option.id  );

			//

			options = new Options();
			options.addOption( "-w", "--workers", 1, 100, "workers" ).setValueRequired();

			cmdLine = ArgumentParser.parse( "-w 4".split( " " ), options, null );
			Assert.assertNotNull( cmdLine );
			Assert.assertEquals( 1, cmdLine.idMap.size() );
			Assert.assertEquals( 1, cmdLine.switchArgsList.size() );
			Assert.assertEquals( 0, cmdLine.unnamedArgsList.size() );

			argument = cmdLine.idMap.get( 1 );
			Assert.assertNotNull( argument );
			Assert.assertEquals( "4", argument.value );

			//

			options = new Options();
			options.addOption( "-w", "--workers", 1, 100, "workers" ).setValueRequired();

			cmdLine = ArgumentParser.parse( "--workers 4".split( " " ), options, null );
			Assert.assertNotNull( cmdLine );
			Assert.assertEquals( 1, cmdLine.idMap.size() );
			Assert.assertEquals( 1, cmdLine.switchArgsList.size() );
			Assert.assertEquals( 0, cmdLine.unnamedArgsList.size() );

			argument = cmdLine.idMap.get( 1 );
			Assert.assertNotNull( argument );
			Assert.assertEquals( "4", argument.value );

			//

			options = new Options();
			options.addOption( "-w", "--workers", 1, 100, "workers" ).setValueRequired();

			cmdLine = ArgumentParser.parse( "--workers=4".split( " " ), options, null );
			Assert.assertNotNull( cmdLine );
			Assert.assertEquals( 1, cmdLine.idMap.size() );
			Assert.assertEquals( 1, cmdLine.switchArgsList.size() );
			Assert.assertEquals( 0, cmdLine.unnamedArgsList.size() );

			argument = cmdLine.idMap.get( 1 );
			Assert.assertNotNull( argument );
			Assert.assertEquals( "4", argument.value );

			//

			options = new Options();
			options.addOption( "-w", "--workers", 1, 100, "workers" ).setValueRequired();

			cmdLine = ArgumentParser.parse( "--workers=\"4\"".split( " " ), options, null );
			Assert.assertNotNull( cmdLine );
			Assert.assertEquals( 1, cmdLine.idMap.size() );
			Assert.assertEquals( 1, cmdLine.switchArgsList.size() );
			Assert.assertEquals( 0, cmdLine.unnamedArgsList.size() );

			argument = cmdLine.idMap.get( 1 );
			Assert.assertNotNull( argument );
			Assert.assertEquals( "4", argument.value );

			//

			options = new Options();
			options.addOption( "-w", "--workers", 1, 100, "workers" ).setValueRequired();

			cmdLine = ArgumentParser.parse( "--workers='4'".split( " " ), options, null );
			Assert.assertNotNull( cmdLine );
			Assert.assertEquals( 1, cmdLine.idMap.size() );
			Assert.assertEquals( 1, cmdLine.switchArgsList.size() );
			Assert.assertEquals( 0, cmdLine.unnamedArgsList.size() );

			argument = cmdLine.idMap.get( 1 );
			Assert.assertNotNull( argument );
			Assert.assertEquals( "4", argument.value );

			//

			options = new Options();
			options.addNamedArgument( "command", 101, 1, 1);
			options.addNamedArgument("files", 102, 1, Integer.MAX_VALUE);

		}
		catch (ArgumentParserException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
	}

	@Test
	public void test_commandline_stopat() {
		//Option option;
		Options options;
		CommandLine cmdLine;
		Argument argument;

		try {
			options = new Options();
			options.addOption( "-w", "--workers", 1, 100, "workers" ).setValueRequired();
			options.addNamedArgument( "command", 101, 1, 1).setStopParsing();

			cmdLine = ArgumentParser.parse( "--workers=4 read -w8 write".split( " " ), options, null );
			Assert.assertNotNull( cmdLine );
			Assert.assertEquals( 2, cmdLine.idMap.size() );
			Assert.assertEquals( 1, cmdLine.switchArgsList.size() );
			Assert.assertEquals( 0, cmdLine.unnamedArgsList.size() );

			argument = cmdLine.idMap.get( 1 );
			Assert.assertNotNull( argument );
			Assert.assertEquals( "4", argument.value );
			argument = cmdLine.idMap.get( 101 );
			Assert.assertNotNull( argument );
			Assert.assertEquals( "read", argument.value );

			Assert.assertNotNull( cmdLine.argsArray );
			Assert.assertEquals( 2, cmdLine.argsArray.length );
			Assert.assertEquals( "-w8", cmdLine.argsArray[ 0 ] );
			Assert.assertEquals( "write", cmdLine.argsArray[ 1 ] );
		}
		catch (ArgumentParserException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
	}
}
