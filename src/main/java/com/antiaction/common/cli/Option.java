/*
 * Created on 06/02/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.antiaction.common.cli;

public class Option {

	public static final int AT_MMT = 1;
	public static final int AT_MC = 2;
	public static final int AT_TXT = 3;

	public static final int VT_NONE = 0;
	public static final int VT_OPTIONAL = 1;
	public static final int VT_REQUIRED = 2;
	
	public static final int SVT_NONE = 0;
	public static final int SVT_OPTIONAL_CHAR = 1;
	public static final int SVT_REQUIRED_CHAR = 2;
	public static final int SVT_TEXT = 3;

	int id;
	int subId;
	String desc;

	String longName;

	byte type;
	String name;
	byte valueType = VT_NONE;
	byte shortValueType = SVT_NONE;
	String shortValueOptions;
	int min;
	int max;

}
