/*
 * Created on 07/02/2012
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.antiaction.common.cli;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CommandLine {

	public List<Argument> switchArgsList = new LinkedList<Argument>();

	public Map<Integer, Argument> idMap = new TreeMap<Integer, Argument>();

	public List<String> unnamedArgsList = new LinkedList<String>();

}
