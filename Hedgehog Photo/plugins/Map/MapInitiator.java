import javax.swing.JPanel;

import se.cth.hedgehogphoto.database.Files;
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
	private Files files;
	
	@InitializePlugin
	public void initialize() {
		MapModel mapModel = new MapModel(files);
		this.map = new MapView(mapModel);
        new MapController(map, files);
	}
	
	@Panel(placement=PluginArea.LEFT_TOP)
	public JPanel getJPanel() {
		return this.map;
	}
	
	@GetVisibleFiles
	public void setFiles(Files files){
		this.files = files;
	}
}
