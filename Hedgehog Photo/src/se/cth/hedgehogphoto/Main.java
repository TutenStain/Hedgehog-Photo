package se.cth.hedgehogphoto;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import se.cth.hedgehogphoto.tagcloud.TagCloudModel;
import se.cth.hedgehogphoto.tagcloud.TagCloudView;

/**
 * @author Barnabas Sapan
 */

public class Main {
	

	
	//TODO Just a skeleton of the main
	public static void main(String[] args) {
		JFrame frame = new JFrame("TagCloud");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 600);
		frame.setVisible(true);
		
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
		tgm.setTags(l);
		TagCloudView tgv = new TagCloudView(tgm);
		tgv.update();
		
		frame.add(tgv);
	}

}
