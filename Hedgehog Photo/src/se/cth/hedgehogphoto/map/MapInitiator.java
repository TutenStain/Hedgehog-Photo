package se.cth.hedgehogphoto.map;

import javax.swing.JPanel;

import se.cth.hedgehogphoto.database.DatabaseAccess;
import se.cth.hedgehogphoto.database.Files;
import se.cth.hedgehogphoto.map.controller.MapController;
import se.cth.hedgehogphoto.map.model.MapModel;
import se.cth.hedgehogphoto.map.view.MapView;
import se.cth.hedgehogphoto.plugin.GetDatabase;
import se.cth.hedgehogphoto.plugin.GetVisibleFiles;
import se.cth.hedgehogphoto.plugin.InitializePlugin;
import se.cth.hedgehogphoto.plugin.Panel;
import se.cth.hedgehogphoto.plugin.Plugin;
import se.cth.hedgehogphoto.view.PluginArea;

/**
 * Initiates the map-plugin.
 * @author Florian Minges
 */

@Plugin(name="Map", version="1.0", 
author="Florian Minges", description="N/A")
public class MapInitiator {
	private MapView map;
	private DatabaseAccess db;
	private Files files;
	
	@InitializePlugin
	public void initialize() {
		MapModel mapModel = new MapModel(files);
		this.map = new MapView(mapModel);
        new MapController(map);
	}
	
	@Panel(placement=PluginArea.LEFT_TOP)
	public JPanel getJPanel() {
		return this.map;
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
