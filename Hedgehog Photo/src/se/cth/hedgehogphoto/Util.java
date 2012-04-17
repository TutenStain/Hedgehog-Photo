package se.cth.hedgehogphoto;

import java.util.ArrayList;
import java.util.List;

public final class Util {
	public static List<String> convertStringToList(String text, String separationSign) {
		List<String> list = new ArrayList<String>();
		int separationSignIndex = text.indexOf(separationSign);
		int tempIndex = 0;

		while (separationSignIndex != -1) {
			String listItem = text.substring(tempIndex, separationSignIndex + 1);
			listItem = listItem.trim();
			list.add(listItem);
			
			tempIndex = separationSignIndex + 1;
			separationSignIndex = text.indexOf(separationSign, tempIndex);
		}

		int lastIndex = text.length();
		if ( !text.endsWith(separationSign) ) {
			String lastItem = text.substring(tempIndex, lastIndex);
			list.add(lastItem);
		}
		
		return list;
	}
}
