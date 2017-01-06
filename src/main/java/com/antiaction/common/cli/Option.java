/*
 * Created on 06/02/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.antiaction.common.cli;

public class Option {

	/** Named argument. */
	public static final int T_COMMAND = 0;
	/** Short/Long named argument. */
	public static final int T_OPTION = 1;

	// Variable Type.
	//public static final int VT_NONE = 0;
	//public static final int VT_OPTIONAL = 1;
	//public static final int VT_REQUIRED = 2;
	
	//public static final int SVT_NONE = 0;
	//public static final int SVT_OPTIONAL_CHAR = 1;
	//public static final int SVT_REQUIRED_CHAR = 2;
	//public static final int SVT_TEXT = 3;

	/** Named argument or short/long option. */
	public int type;

	/** Named argument command. */
	public String command;
	/** Short name option. */
	public String shortName;
	/** Long name option. */
	public String longName;

	/** Primary argument identifier. */
	public int id;
	/** Secondary argument identifier. */
	public int subId;
	/** Argument/Option description. */
	public String desc;

	public boolean bValueRequired = false;
	public String shortValueOptions;
	public Boolean bShortValueOptional = null;

	//byte valueType = VT_NONE;
	//byte shortValueType = SVT_NONE;
	public int min;
	public int max;

	public boolean bStopParsing = false;

	public Option() {
	}

	public Option setValueRequired() {
		bValueRequired = true;
		return this;
	}

	public Option setTrailingValueChar(String shortValueOptions, boolean bShortValueOptional) {
		this.shortValueOptions = shortValueOptions;
		this.bShortValueOptional = bShortValueOptional;
		return this;
	}

	public Option setStopParsing() {
		bStopParsing = true;
		return this;
	}

	/*
	int idx;
	int pIdx;
	if ( shortName.length() > 1 ) {
		//option.type = Option.AT_MC;
		option.id = id;
		option.subId = subId;
		option.shortName = shortName.substring( 1, 2 );
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
		if ( option.shortName.charAt( 0 ) < 256 ) {
			singleOptions[ option.shortName.charAt( 0 ) ] = option;
		}
		else {
			throw new IllegalArgumentException( "Invalid chargument definition: " + shortName );
		}
	}
	else {
		throw new IllegalArgumentException( "Incomplete argument definition: " + shortName );
	}
	*/

}
