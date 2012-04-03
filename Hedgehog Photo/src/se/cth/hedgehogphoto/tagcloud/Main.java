package se.cth.hedgehogphoto.tagcloud;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

/**
 * @author Barnabas Sapan
 */

public class Main {
	public static void main(String[] args) {
		JFrame frame = new JFrame("TagCloud");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 600);
		frame.setVisible(true);
		
		//TagCloud
		List<String> l = new ArrayList<String>();
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

		//TagCloud
		TagCloudModel tgm = new TagCloudModel();
		TagCloudView tgv = new TagCloudView();
		tgm.addObserver(tgv);
		tgm.setTags(l);
		frame.add(tgv);
		
		//Manually refresh the view as it for some reason wont do it automatically all the time.
		frame.revalidate();
	}
}
