package com.exhibition.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ListUtil {
	/**数组去除重复元素*/
	public static List<String> removeDuplicateWithOrder(List<String> list) {
	     Set<String> set = new HashSet<String>();
	      List<String> newList = new ArrayList<String>();
	   for (Iterator<String> iter = list.iterator(); iter.hasNext();) {
	          String element = iter.next();
	          if (set.add(element))
	             newList.add(element);
	       } 
	      list.clear();
		return newList;
	}
}
