package com.antiaction.common.cli;

public class CommandLineTest {

	public static final int A_DECOMPRESS = 1;
	public static final int A_COMPRESS = 2;
	public static final int A_CLVL_1 = 3;
	public static final int A_CLVL_2 = 4;
	public static final int A_CLVL_3 = 5;
	public static final int A_CLVL_4 = 6;
	public static final int A_CLVL_5 = 7;
	public static final int A_CLVL_6 = 8;
	public static final int A_CLVL_7 = 9;
	public static final int A_CLVL_8 = 10;
	public static final int A_CLVL_9 = 11;

	public static void main(String[] args) {
		Options options = new Options();
		/*
		options.addOption( "-d", A_DECOMPRESS );
		options.addOption( "-c", A_COMPRESS );
		options.addOption( "-1", A_CLVL_1 );
		options.addOption( "-2", A_CLVL_2 );
		options.addOption( "-3", A_CLVL_3 );
		options.addOption( "-4", A_CLVL_4 );
		options.addOption( "-5", A_CLVL_5 );
		options.addOption( "-6", A_CLVL_6 );
		options.addOption( "-7", A_CLVL_7 );
		options.addOption( "-8", A_CLVL_8 );
		options.addOption( "-9", A_CLVL_9 );
		*/
		try {
			//CommandLine.Arguments arguments = cmdLine.parse( "-d -c".split( " " ) );
			CommandLine cmdLine = ArgumentParser.parse( "-d C:\\*.gz".split( " " ), options, null );
			Argument argument;
			for ( int i=0; i<cmdLine.switchArgsList.size(); ++i) {
				argument = cmdLine.switchArgsList.get( i );
				System.out.println( argument.option.id + "=" + argument.value );
			}
		}
		catch (ArgumentParserException e) {
			e.printStackTrace();
		}
	}

}
