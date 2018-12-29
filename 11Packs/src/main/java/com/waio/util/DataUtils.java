/**
 * 
 */
package com.waio.util;

/**
 * @author Viramm
 * 
 * will contain reusable methods
 *
 */
public class DataUtils {

	/**
	 * @param matches
	 */
	public static String getShortForm(String teamName) {
		String[] words = teamName.split("\\W+");
		StringBuffer sb = new StringBuffer();
		if(words.length == 1) {
			return teamName.substring(0,  3).toUpperCase();
		}
		for(String str : words) {
			//if(sb.length()<1) {
				//sb.append(str.substring(0,3));
			//}else {
				sb.append(str.substring(0,1));	
			//}
		}
		return sb.toString().toUpperCase();
	}
}
