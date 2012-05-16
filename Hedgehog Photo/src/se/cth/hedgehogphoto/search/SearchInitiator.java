package se.cth.hedgehogphoto.search;

import java.awt.Dimension;

import se.cth.hedgehogphoto.search.controller.SearchController;
import se.cth.hedgehogphoto.search.model.SearchModel;
import se.cth.hedgehogphoto.search.view.SearchPreviewView;
import se.cth.hedgehogphoto.search.view.SearchView;

public class SearchInitiator {
	
	//TODO: Add plugin-annotations here?
	public SearchInitiator() {
		SearchPreviewView previewView = new SearchPreviewView();
		SearchView view = new SearchView(previewView);
		SearchModel model = new SearchModel(view, previewView);
		view.setPreferredSize(new Dimension(250, 30));
		new SearchController(model, view);
	}
}
