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

	public Option addTextOption(String command, int id, int subId, String desc) {
		Option option = new Option();
		option.type = Option.T_COMMAND;
		option.id = id;
		option.subId = subId;
		option.desc = desc;
		if ( command != null && command.length() > 0 && !command.startsWith( "-" ) ) {
			option.command = command.toLowerCase();
			textOptions.put( option.command, option );
		}
		else {
			throw new IllegalArgumentException( "Invalid text option definition: " + command );
		}
		return option;
	}

	public Option addOption(String shortName, String longName, int id, int subId, String desc) {
		if ( (shortName == null || shortName.length() == 0) && (longName == null || longName.length() == 0)) {
			throw new IllegalArgumentException( "Invalid argument definition - missing both short and long name." );
		}
		Option option = new Option();
		option.type = Option.T_OPTION;
		option.id = id;
		option.subId = subId;
		option.desc = desc;
		if ( longName != null ) {
			if ( longName.startsWith( "--" ) ) {
				longName = longName.substring( "--".length() );
				if ( longName.length() > 0 ) {
					option.longName = longName.toLowerCase();
					longOptions.put( option.longName, option );
				}
				else {
					throw new IllegalArgumentException( "Empty argument definition: --" + longName );
				}
			}
			else {
				throw new IllegalArgumentException( "Invalid argument definition: " + longName );
			}
		}
		if ( shortName != null ) {
			if ( shortName.startsWith( "-" ) ) {
				shortName = shortName.substring( "-".length() );
				if ( shortName.length() > 0 ) {
					if ( bSingleCharDashOptions && shortName.length() > 1 ) {
						throw new IllegalArgumentException( "Invalid argument definition length: -" + shortName );
					}
					option.shortName = shortName;
					shortOptions.put( option.shortName.charAt( 0 ), option );
				}
				else {
					throw new IllegalArgumentException( "Empty argument definition length: -" + shortName );
				}
			}
			else {
				throw new IllegalArgumentException( "Invalid argument definition: " + shortName );
			}
		}
		return option;
	}

	public Option addNamedArgument(String name, int id) {
		return addNamedArgument( name, id, 1, 1 );
	}

	public Option addNamedArgument(String name, int id, int min, int max) {
		if ( max <= 0 && max < min ) {
			throw new IllegalArgumentException( "Invalid argument number interval: " + min + ", " + max );
		}
		Option option = new Option();
		option.id = id;
		option.shortName = name;
		option.min = min;
		option.max = max;
		namedArguments.add( option );
		return option;
	}

}
