package com.antiaction.common.cli;

import java.util.LinkedList;
import java.util.Queue;

public class ArgumentParser {

	private static final int SAS_ARGCHAR = 0;
	private static final int SAS_OPTIONAL_CHAR = 1;
	private static final int SAS_REQUIRED_CHAR = 2;
	private static final int SAS_EQU_OR_TEXT = 3;
	private static final int SAS_QUOTED_OR_TEXT = 4;
	private static final int SAS_QUOTED_TEXT = 5;
	private static final int SAS_TEXT = 6;

	public static CommandLine parse(Options options, String[] argsArray) throws ParseException {
		if ( options == null ) {
			throw new IllegalArgumentException( "Options can not be null!" );
		}
		if ( argsArray == null || argsArray.length == 0 ) {
			return null;
		}
		CommandLine args = new CommandLine();
		Queue<Argument> argStack = new LinkedList<Argument>();
		String argStr;
		Option option;
		Argument arg;
		Argument nArg = null;
		String tmpStr;
		StringBuffer sb = new StringBuffer();
		int aIdx = 0;
		int cIdx;
		int pIdx;
		int nIdx = 0;
		char c;
		int state;
		while ( aIdx < argsArray.length ) {
			argStr = argsArray[ aIdx++ ];
			if ( argStr.startsWith( "--" ) ) {
				cIdx = 2;
				pIdx = cIdx;
				option = null;
				arg = null;
				while ( cIdx < argStr.length() && Character.isLetter( argStr.charAt( cIdx ) ) ) {
					++cIdx;
				}
				if ( pIdx == cIdx ) {
					throw new IllegalArgumentException( "Empty long name option!" );
				}
				tmpStr = argStr.substring( pIdx, cIdx ).toLowerCase();
				option = options.longOptions.get(tmpStr);
				if ( option == null ) {
					throw new IllegalArgumentException( "Unknown option '--" + tmpStr + "'." );
				}
				arg = new Argument();
				arg.name = "--" + tmpStr;
				arg.option = option;
				args.switchArgsList.add( arg );
				args.idMap.put( option.id, arg );
				if ( cIdx == argStr.length() ) {
					if ( option.bValueRequired ) {
						argStack.add( arg );
					}
				}
				else {
					if ( !option.bValueRequired ) {
						throw new IllegalArgumentException( "Option '" + arg.name + "' does not expect a value." );
					}
					if ( argStr.charAt( cIdx ) != '=') {
						throw new IllegalArgumentException( "Option '" + arg.name + "' expected a trailing '='." );
					}
					tmpStr = argStr.substring( ++cIdx );
					if ( tmpStr.length() > 0 ) {
						c = tmpStr.charAt( 0 );
						if ( c == '"' ) {
							if ( tmpStr.length() == 1 || tmpStr.charAt( tmpStr.length() - 1 ) != '"' ) {
								throw new IllegalArgumentException( "Argument value missing end quote (\")." );
							}
							tmpStr = tmpStr.substring( 1, tmpStr.length() - 1 );
						}
						else if ( c == '\'' ) {
							if ( tmpStr.length() == 1 || tmpStr.charAt( tmpStr.length() - 1 ) != '\'' ) {
								throw new IllegalArgumentException( "Argument value missing end quote (')." );
							}
							tmpStr = tmpStr.substring( 1, tmpStr.length() - 1 );
						}
					}
					arg.value = tmpStr;
				}
			}
			else if ( argStr.startsWith("-") ) {
				cIdx = 1;
				option = null;
				arg = null;
				state = SAS_ARGCHAR;
				while ( cIdx < argStr.length() ) {
					switch ( state ) {
					case SAS_ARGCHAR:
						c = argStr.charAt( cIdx++ );
						option = options.shortOptions.get(c);
						if ( option != null ) {
							arg = new Argument();
							arg.name = "-" + c;
							arg.option = option;
							args.switchArgsList.add( arg );
							args.idMap.put( option.id, arg );
							if ( option.bValueRequired ) {
								state = SAS_EQU_OR_TEXT;
							}
							else if ( option.bShortValueOptional != null ) {
								if ( option.bShortValueOptional == false) {
									state = SAS_OPTIONAL_CHAR;
								}
								else {
									state = SAS_REQUIRED_CHAR;
								}
							}
						}
						else {
							throw new ParseException( "Unknown option '-" + c + "'." );
						}
						break;
					case SAS_OPTIONAL_CHAR:
						c = argStr.charAt( cIdx );
						if ( option.shortValueOptions.indexOf( c ) != -1 ) {
							arg.value = "" + c;
							++cIdx;
						}
						else {
							state = SAS_ARGCHAR;
						}
						break;
					case SAS_REQUIRED_CHAR:
						c = argStr.charAt( cIdx++ );
						if ( option.shortValueOptions.indexOf( c ) != -1 ) {
							arg.value = "" + c;
						}
						else {
							throw new ParseException( "invalid argument value '" + c + "' for option -- " + arg.option.shortName );
						}
						break;
					case SAS_EQU_OR_TEXT:
						c = argStr.charAt( cIdx++ );
						sb.setLength( 0 );
						if ( c == '=' ) {
							state = SAS_QUOTED_OR_TEXT;
						}
						else if ( c == '"' ) {
							state = SAS_QUOTED_TEXT;
						}
						else {
							sb.append( c );
							state = SAS_TEXT;
						}
						break;
					case SAS_QUOTED_OR_TEXT:
						c = argStr.charAt( cIdx++ );
						if ( c == '"' ) {
							state = SAS_QUOTED_TEXT;
						}
						else {
						}
						break;
					case SAS_QUOTED_TEXT:
						c = argStr.charAt( cIdx++ );
						if ( c != '"' ) {
							sb.append( c );
						}
						else {
							if ( cIdx < argStr.length() ) {
								throw new ParseException( "argument value beyond end quote" );
							}
						}
						break;
					case SAS_TEXT:
						c = argStr.charAt( cIdx++ );
						sb.append( c );
						break;
					}
				}
				switch ( state ) {
				case SAS_REQUIRED_CHAR:
				case SAS_EQU_OR_TEXT:
					argStack.add( arg );
					break;
				case SAS_QUOTED_TEXT:
				case SAS_QUOTED_OR_TEXT:
				case SAS_TEXT:
					arg.value = sb.toString();
					break;
				}
			}
			else {
				option = options.textOptions.get( argStr );
				if ( option != null ) {
					arg = new Argument();
					arg.option = option;
					args.switchArgsList.add( arg );
					args.idMap.put( option.id, arg );
				}
				else {
					if ( argStr.startsWith( "\"" ) || argStr.endsWith( "\"" ) ) {
						if ( !argStr.startsWith( "\"" ) ) {
							throw new ParseException( "argument value missing start quote" );
						}
						if ( !argStr.endsWith( "\"" ) ) {
							throw new ParseException( "argument value missing end quote" );
						}
						argStr = argStr.substring( 1, argStr.length() - 1 );
					}
					if ( !argStack.isEmpty() ) {
						arg = argStack.remove();
						arg.value = argStr;
					}
					else {
						if ( nIdx < options.namedArguments.size() ) {
							option = options.namedArguments.get( nIdx );
							if ( option.min == 1 && option.max == 1 ) {
								arg = new Argument();
								arg.option = option;
								arg.value = argStr;
								//args.switchArgsList.add( arg );
								args.idMap.put( option.id, arg );
								++nIdx;
							}
							else {
								if ( nArg == null) {
									nArg = new Argument();
									nArg.option = option;
									//args.switchArgsList.add( nArg );
									args.idMap.put( option.id, nArg );
								}
								nArg.values.add( argStr );
								if ( nArg.values.size() >= option.max ) {
									nArg = null;
									++nIdx;
								}
							}
						}
						else {
						}
					}
				}
			}
		}
		if ( !argStack.isEmpty() ) {
			arg = argStack.remove();
			throw new ParseException( "option " + arg.name + " requires an argument." );
		}
		if ( nArg != null ) {
			if ( nArg.values.size() < nArg.option.min ) {
				throw new ParseException( "argument(s) required -- " + nArg.option.shortName );
			}
			nArg = null;
			++nIdx;
		}
		while ( nIdx < options.namedArguments.size() ) {
			option = options.namedArguments.get( nIdx++ );
			if ( option.min > 0 ) {
				throw new ParseException( "argument(s) required -- " + option.shortName );
			}
		}
		return args;
	}

}
