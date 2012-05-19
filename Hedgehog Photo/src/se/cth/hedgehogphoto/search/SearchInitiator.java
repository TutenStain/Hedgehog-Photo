package se.cth.hedgehogphoto.search;

import javax.swing.JPanel;

import se.cth.hedgehogphoto.database.DatabaseAccess;
import se.cth.hedgehogphoto.database.Files;
import se.cth.hedgehogphoto.plugin.GetDatabase;
import se.cth.hedgehogphoto.plugin.GetVisibleFiles;
import se.cth.hedgehogphoto.plugin.Panel;
import se.cth.hedgehogphoto.plugin.Plugin;
import se.cth.hedgehogphoto.search.controller.SearchController;
import se.cth.hedgehogphoto.search.model.SearchModel;
import se.cth.hedgehogphoto.search.view.JPopupPreview;
import se.cth.hedgehogphoto.search.view.JSearchBox;
import se.cth.hedgehogphoto.view.PluginArea;

@Plugin(name="Search", version="1.0", 
author="Barnabas Sapan", description="N/A")
public class SearchInitiator {
	private JSearchBox view;
	private Files files;
	private DatabaseAccess db;
	
	public SearchInitiator() {
		SearchModel model = new SearchModel();
		JPopupPreview previewView = new JPopupPreview();
		this.view = new JSearchBox(model, previewView);
		new SearchController(model, this.view);
	}
	
	@Panel(placement=PluginArea.SEARCH)
	public JPanel getView(){
		return view;	
	}

	@GetDatabase
	public void setDB(DatabaseAccess db){
		this.db = db;
	}

	@GetVisibleFiles
	public void setFiles(Files files){
		this.files = files;
	}
}
