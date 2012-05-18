package se.cth.hedgehogphoto.map;

import javax.swing.JPanel;

import se.cth.hedgehogphoto.map.controller.MapController;
import se.cth.hedgehogphoto.map.model.MapModel;
import se.cth.hedgehogphoto.map.view.MapView;
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

	@InitializePlugin
	public void initialize() {
		MapModel mapModel = new MapModel();
		this.map = new MapView(mapModel);
        new MapController(map);
	}
	
	@Panel(placement=PluginArea.LEFT_MIDDLE)
	public JPanel getJPanel() {
		return this.map;
	}
}
