/*
 * Created on 06/02/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.antiaction.common.cli;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Options {

	public boolean bSingleCharDashOptions = true;

	protected Map<String, Option> longOptions = new TreeMap<String, Option>();

	protected Map<Character, Option> shortOptions = new TreeMap<Character, Option>();

	protected Map<String, Option> textOptions = new TreeMap<String, Option>();

	protected List<Option> namedArguments = new LinkedList<Option>();

	public Options() {
	}

	public void setSingleCharDashOptions(boolean bSingleCharDashOptions) {
		this.bSingleCharDashOptions = bSingleCharDashOptions;
	}

	public Option addOption(String shortName, String longName, int id, int subId, String desc) throws ArgumentParserException {
		if ( (shortName == null || shortName.length() == 0) && (longName == null || longName.length() == 0) ) {
			throw new ArgumentParserException( "Invalid argument definition - missing both short and long name." );
		}
		Option option = Option.option( id, subId, desc );
		if ( longName != null ) {
			if ( longName.startsWith( "--" ) ) {
				longName = longName.substring( "--".length() );
				if ( longName.length() > 0 ) {
					int pc = '-';
					int c;
					for (int i=0; i<longName.length(); ++i) {
						c = longName.charAt( i );
						if (!(Character.isLetterOrDigit(c) || c == '-') || (c == '-' && pc == '-')) {
							throw new ArgumentParserException( "Invalid character('" + (char)c + "') in argument definition: --" + longName );
						}
						pc = c;
					}
					option.longName = longName.toLowerCase();
					longOptions.put( option.longName, option );
				}
				else {
					throw new ArgumentParserException( "Empty argument definition: --" + longName );
				}
			}
			else {
				throw new ArgumentParserException( "Invalid argument definition: " + longName );
			}
		}
		if ( shortName != null ) {
			if ( shortName.startsWith( "-" ) ) {
				shortName = shortName.substring( "-".length() );
				if ( shortName.length() > 0 ) {
					if ( bSingleCharDashOptions && shortName.length() > 1 ) {
						throw new ArgumentParserException( "Invalid argument definition length: -" + shortName );
					}
					option.shortName = shortName;
					shortOptions.put( option.shortName.charAt( 0 ), option );
				}
				else {
					throw new ArgumentParserException( "Empty argument definition length: -" + shortName );
				}
			}
			else {
				throw new ArgumentParserException( "Invalid argument definition: " + shortName );
			}
		}
		return option;
	}

	public Option addNamedArgument(String name, int id) throws ArgumentParserException {
		return addNamedArgument( name, id, 1, 1 );
	}

	public Option addNamedArgument(String name, int id, int min, int max) throws ArgumentParserException {
		if ( max <= 0 && max < min ) {
			throw new ArgumentParserException( "Invalid argument number interval: " + min + ", " + max );
		}
		Option option = new Option();
		option.id = id;
		option.shortName = name;
		option.min = min;
		option.max = max;
		namedArguments.add( option );
		return option;
	}

	public Option addTextOption(String command, int id, int subId, String desc) throws ArgumentParserException {
		if ( command == null || command.length() == 0 || command.startsWith( "-" ) ) {
			throw new ArgumentParserException( "Invalid text option definition: " + command );
		}
		Option option = Option.command( id, subId, desc );
		option.command = command.toLowerCase();
		textOptions.put( option.command, option );
		return option;
	}

}
