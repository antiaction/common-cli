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
			cmdLine = ArgumentParser.parse( null, "-d C:\\*.gz".split( " " ) );
			Assert.fail( "Exception expected!" );
		}
		catch (ParseException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
		catch (IllegalArgumentException e) {
		}

		try {
			options = new Options();
			options.addOption( "-d", null, 1, 100, "decompress" );

			cmdLine = ArgumentParser.parse( options, null );
			Assert.assertNull( cmdLine );

			cmdLine = ArgumentParser.parse( options, new String[ 0 ] );
			Assert.assertNull( cmdLine );

			//

			cmdLine = ArgumentParser.parse( options, "-d C:\\*.gz".split( " " ) );
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

			cmdLine = ArgumentParser.parse( options, "--decompress C:\\*.gz".split( " " ) );
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

			cmdLine = ArgumentParser.parse( options, "-w 4".split( " " ) );
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

			cmdLine = ArgumentParser.parse( options, "--workers 4".split( " " ) );
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

			cmdLine = ArgumentParser.parse( options, "--workers=4".split( " " ) );
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

			cmdLine = ArgumentParser.parse( options, "--workers=\"4\"".split( " " ) );
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

			cmdLine = ArgumentParser.parse( options, "--workers='4'".split( " " ) );
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
		catch (ParseException e) {
			e.printStackTrace();
			Assert.fail( "Unexpected exception!" );
		}
	}

}
