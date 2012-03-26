package se.cth.hedgehogphoto.search;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

public class SearchPreviewView extends JPanel implements Observer{
	public SearchPreviewView(){
		setPreferredSize(new Dimension(200, 140));
		setBackground(Color.RED);
	}

	@Override
	public void update(Observable o, Object arg) {
		SearchModel model = (SearchModel)arg;
		System.out.println("UPDATE @ SEARCH_PREVIEW_VIEW: " + model.getSearchQueryText());
	}
}
