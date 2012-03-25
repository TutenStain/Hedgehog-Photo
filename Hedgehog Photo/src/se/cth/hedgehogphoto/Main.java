package se.cth.hedgehogphoto;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextField;

import com.sun.org.apache.xerces.internal.impl.RevalidationHandler;

import se.cth.hedgehogphoto.search.SearchController;
import se.cth.hedgehogphoto.search.SearchModel;
import se.cth.hedgehogphoto.search.SearchView;
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
		
		//TagCloud
		/*List<String> l = new ArrayList<String>();
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
		frame.add(tgv);*/
		
		SearchModel sm = new SearchModel();
		sm.setSearchBoxSize(new Dimension(100, 30));
		sm.setSearchButtonSize(new Dimension(100, 30));
		SearchView sv = new SearchView(sm);
		sv.setPreferredSize(new Dimension(250, 30));
		sm.addObserver(sv);
		SearchController sc = new SearchController(sm, sv);
		frame.add(sv);	
		
		//Manually refresh the view as it for some reason wont do it automatically all the time.
		frame.revalidate();
	}

}