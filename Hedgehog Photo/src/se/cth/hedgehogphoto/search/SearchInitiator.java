package se.cth.hedgehogphoto.search;

import java.awt.Dimension;

import javax.swing.JPanel;

import se.cth.hedgehogphoto.search.controller.SearchController;
import se.cth.hedgehogphoto.search.model.SearchModel;
import se.cth.hedgehogphoto.search.view.SearchPreviewView;
import se.cth.hedgehogphoto.search.view.SearchView;

public class SearchInitiator {
	private SearchView view;
	
	//TODO: Add plugin-annotations here?
	public SearchInitiator() {
		SearchPreviewView previewView = new SearchPreviewView();
		this.view = new SearchView(previewView);
		SearchModel model = new SearchModel();
		model.addObserver(this.view);
		model.addObserver(previewView);
		this.view.setPreferredSize(new Dimension(250, 30));
		new SearchController(model, this.view);
	}
	
	public JPanel getView() {
		return this.view;
	}
}
