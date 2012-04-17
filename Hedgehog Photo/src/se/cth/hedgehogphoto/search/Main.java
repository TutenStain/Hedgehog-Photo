package se.cth.hedgehogphoto.search;

import java.awt.Dimension;

import javax.swing.JFrame;


/**
 * @author Barnabas Sapan
 */

public class Main {

	//TODO Just a skeleton of the main
	public static void main(String[] args) {
		JFrame frame = new JFrame("Search");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 600);
		frame.setVisible(true);
		
		SearchModel sm = new SearchModel();
		SearchView sv = new SearchView();
		sv.setPreferredSize(new Dimension(250, 30));
		sm.addObserver(sv);
		SearchPreviewView spv = new SearchPreviewView();
		sv.setSearchPreview(spv);
		sm.addObserver(spv);
		new SearchController(sm, sv);
		frame.add(sv);
		
		//Manually refresh the view as it for some reason wont do it automatically all the time.
		frame.revalidate();
	}

}
