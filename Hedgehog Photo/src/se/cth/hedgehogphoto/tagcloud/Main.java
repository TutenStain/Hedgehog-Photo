package se.cth.hedgehogphoto.tagcloud;

import java.util.ArrayList;
import java.util.List;

import se.cth.hedgehogphoto.plugin.InitializePlugin;

/**
 * @author Barnabas Sapan
 */

public class Main {
	@InitializePlugin
	public void start() {
		List<String> l = new ArrayList<String>();
	//	l = DatabaseHandler.getTags();
		TagCloudModel tgm = new TagCloudModel();
		TagCloudView tgv = new TagCloudView();
		tgm.addObserver(tgv);
		
		l.add("hej");
		
		l.add("whiii");
		l.add("sfsdf");
		l.add("hej");
		l.add("whiii");
		l.add("whiii");
		l.add("Yay");
		l.add("Sweden");
		l.add("Sweden");
		l.add("Sweden");
		l.add("Sweden");
		
		tgm.setTags(l);
	}
}
