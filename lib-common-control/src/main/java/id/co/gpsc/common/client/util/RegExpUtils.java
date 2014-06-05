/**
 * 
 */
package id.co.gpsc.common.client.util;

import java.util.ArrayList;

import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;

/**
 * @author Agus Gede Adipartha Wibawa
 * @since Apr 4, 2013, 10:51:31 AM
 * @version $Id
 */
public class RegExpUtils {

	private static RegExpUtils instance;
	
	public static RegExpUtils getInstance() {
		if (instance == null) {
			instance = new RegExpUtils();
		}
		return instance;
	}
	
	/**
	 * matching string dengan regex tertentu
	 * ini dibuat karena regex di gwt agak sulit untuk mendapatkan semua string yang match
	 * @param stringPattern pattern regex
	 * @param stringToMatch string yang ingin dicek dengan regex
	 * @return string2 yang matching dengan rege
	 */
	public ArrayList<String> matching(String stringPattern, String stringToMatch) {
		ArrayList<String> matchResult = new ArrayList<String>();
		RegExp pattern = RegExp.compile(stringPattern);
		while (stringToMatch != null) {
			MatchResult matcher = pattern.exec(stringToMatch);
			if (matcher != null) {
				String result = matcher.getGroup(0);
				matchResult.add(result);
				String[] tempArrayNewinput = stringToMatch.split(result);
				if (tempArrayNewinput.length > 1)
					stringToMatch = tempArrayNewinput[1];
				else 
					break ;
			} else {
				break ;
			}
		}
		return matchResult;
	}
}
