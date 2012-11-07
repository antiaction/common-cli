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

	protected Map<String, Option> longOptions = new TreeMap<String, Option>();

	protected Map<String, Option> shortOption = new TreeMap<String, Option>();

	protected Option[] singleOptions = new Option[ 256 ];

	protected Map<String, Option> textOptions = new TreeMap<String, Option>();

	protected List<Option> namedArguments = new LinkedList<Option>();

	public Options() {
	}

	/*
	public Option addOption(String shortName, int id, String desc) {
		return addOption( shortName, id, -1 );
	}
	*/

	public Option addOption(String shortName, String longName, int id, int subId, String desc) {
		Option option = new Option();
		option.id = id;
		option.subId = subId;
		option.desc = desc;
		int idx;
		int pIdx;
		if ( longName != null && longName.length() > 0 ) {
			idx = longName.indexOf( ':' );
			if ( idx != -1 ) {
				option.longName.substring( 0, idx++ );
				pIdx = idx;
			}
			idx = longName.indexOf( '=' );
			if ( idx == -1 ) {
				option.longName = longName.substring( 2 );
			}
			else if ( idx > 2 ) {
				option.name = longName.substring( 2, idx );
				option.valueType = Option.VT_REQUIRED;
			}
			else {
				throw new IllegalArgumentException( "Incomplete argument definition: " + longName );
			}
			longOptions.put( option.name, option );
		}
		else if ( shortName.startsWith( "-" ) ) {
			if ( shortName.length() > 1 ) {
				option = new Option();
				option.type = Option.AT_MC;
				option.id = id;
				option.subId = subId;
				option.name = shortName.substring( 1, 2 );
				if ( shortName.length() > 2 ) {
					if ( shortName.charAt( 2 ) == '=' ) {
						option.shortValueType = Option.SVT_TEXT;
					}
					else if ( shortName.charAt( 2 ) == '[' ) {
						idx = shortName.indexOf( ']', 3 );
						if ( idx > 3 ) {
							option.shortValueType = Option.SVT_OPTIONAL_CHAR;
							option.shortValueOptions = shortName.substring( 3, idx );
						}
						if ( idx == -1 ) {
							throw new IllegalArgumentException( "Missing ']': " + shortName );
						}
					}
					else if ( shortName.charAt( 2 ) == '<' ) {
						idx = shortName.indexOf( '>', 3 );
						if ( idx > 3 ) {
							option.shortValueType = Option.SVT_REQUIRED_CHAR;
							option.shortValueOptions = shortName.substring( 3, idx );
						}
						if ( idx == -1 ) {
							throw new IllegalArgumentException( "Missing '>': " + shortName );
						}
					}
					else {
						throw new IllegalArgumentException( "Invalid argument definition: " + shortName );
					}
				}
				if ( option.name.charAt( 0 ) < 256 ) {
					singleOptions[ option.name.charAt( 0 ) ] = option;
				}
				else {
					throw new IllegalArgumentException( "Invalid chargument definition: " + shortName );
				}
			}
			else {
				throw new IllegalArgumentException( "Incomplete argument definition: " + shortName );
			}
		}
		else {
			option = new Option();
			option.type = Option.AT_TXT;
			option.id = id;
			option.subId = subId;
			idx = shortName.indexOf( '=' );
			if ( idx == -1 ) {
				option.name = shortName.substring( 2 );
			}
			else if ( idx > 0 ) {
				option.name = shortName.substring( 2, idx );
				option.valueType = Option.VT_REQUIRED;
			}
			else {
				throw new IllegalArgumentException( "Incomplete argument definition: " + shortName );
			}
			textOptions.put( option.name, option );
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
		option.name = name;
		option.min = min;
		option.max = max;
		namedArguments.add( option );
		return option;
	}

}
