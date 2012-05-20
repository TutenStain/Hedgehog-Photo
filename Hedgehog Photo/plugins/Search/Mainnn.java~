import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import se.cth.hedgehogphoto.database.Files;
import se.cth.hedgehogphoto.database.DatabaseAccess;
import se.cth.hedgehogphoto.plugin.GetDatabase;
import se.cth.hedgehogphoto.plugin.InitializePlugin;
import se.cth.hedgehogphoto.plugin.Panel;
import se.cth.hedgehogphoto.plugin.Plugin;
import se.cth.hedgehogphoto.view.PluginArea;
import se.cth.hedgehogphoto.plugin.GetVisibleFiles;


/**
 * @author Barnabas Sapan
 */

@Plugin(name="Search", version="1.0", 
author="Barnabas Sapan", description="N/A")
public class Mainnn {
	private SearchView sv;
	private DatabaseAccess db;
	private Files f;
 
	@InitializePlugin
	public void start() {
		SearchPreviewView spv = new SearchPreviewView(f);
		this.sv = new SearchView(spv, f);
		SearchModel sm = new SearchModel(sv, db);
		sv.setPreferredSize(new Dimension(250, 30));
		sm.addObserver(sv);
		sm.addObserver(spv);
		new SearchController(sm, sv, f);
	}

	@Panel(placement=PluginArea.SEARCH)
	public JPanel getPanel(){
		return sv;	
	}

	@GetDatabase
	public void setDB(DatabaseAccess db){
		this.db = db;
	}

	@GetVisibleFiles
	public void setFiles(Files f){
		this.f = f;
	}

}
