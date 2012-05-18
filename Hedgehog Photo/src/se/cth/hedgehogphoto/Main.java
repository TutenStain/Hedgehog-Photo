package se.cth.hedgehogphoto;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import se.cth.hedgehogphoto.controller.DefaultController;
import se.cth.hedgehogphoto.database.DatabaseHandler;
import se.cth.hedgehogphoto.log.Log;
import se.cth.hedgehogphoto.map.controller.MapController;
import se.cth.hedgehogphoto.map.model.MapModel;
import se.cth.hedgehogphoto.map.view.MapView;
import se.cth.hedgehogphoto.metadata.Metadata;
import se.cth.hedgehogphoto.model.MainModel;
import se.cth.hedgehogphoto.objects.FileObject;
import se.cth.hedgehogphoto.objects.LocationObjectOther;
import se.cth.hedgehogphoto.plugin.PluginLoader;
import se.cth.hedgehogphoto.search.SearchInitiator;
import se.cth.hedgehogphoto.view.MainView;
import se.cth.hedgehogphoto.view.PluginArea;
import se.cth.hedgehogphoto.view.StartUpView;




/**
 * @author Barnabas Sapan
 */

public class Main {

	public static void main(String[] args) {
		StartUpView start = new StartUpView();
		Thread startUpViewT = new Thread(start);
		startUpViewT.start();
		try {
			startUpViewT.join();
		} catch (InterruptedException e) {
			Log.getLogger().log(Level.WARNING, "Interrupted", e);
		}
		
		//TODO Uncomment these the first run if you need to recreate the database
		//DatabaseHandler.getInstance().deleteAll();
		//insertFileObjectsIntoDatabase();
		
		MainModel model = new MainModel();
		MainView view = new MainView(start);
		model.addObserver(view);
		new DefaultController(model, view);
		
		File pluginRooDir = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "plugin");
		PluginLoader p = new PluginLoader(view, pluginRooDir, true);
		Thread pluginLoaderT = new Thread(p);
		pluginLoaderT.start();
		//p.loadAllPlugins();
		//File tagCloudDir = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "plugin/TagCloud");
		//p.loadPluginFromDirectory(tagCloudDir);
				
		//map
		MapModel mapModel = new MapModel();
		MapView map = new MapView(mapModel);
        MapController mapController = new MapController(map);
		view.addPlugin(map, PluginArea.LEFT_TOP);
		
		//Search gets loaded from plugin dir now
		//SearchInitiator search = new SearchInitiator();

		se.cth.hedgehogphoto.database.Files.getInstance().addObserver(view);

		//view.addPlugin(search.getView(), PluginArea.SEARCH);

		model.testNotify();
	}
	
	private static void insertFileObjectsIntoDatabase() {
		
		File directory = new File("Pictures");
		File[] files = directory.listFiles();
		
		int i = 0;
		for(File file : files) {
			FileObject f = Metadata.getImageObject(file);
			f.setLocationObject(new LocationObjectOther("Japan"));
			f.setComment("Gutes bild");
			f.setFileName(file.getName());
			f.setDate("2012.12.02");
			//Just some random tags to test the TagCloud
			List<String> l = new ArrayList<String>();
			l.add("Snyggt");
			if(i == 0){
				l.add("Snyggt");
			}
			if(i == 3 || i == 4){
				l.add("Trevligt");
			}
			if(i % 2 == 0){
				l.add("Festligt");
			}
			f.setTags(l);
			
			try {
				f.setFilePath(file.getCanonicalPath());
				Log.getLogger().log(Level.INFO, "Found image:" + file.getCanonicalPath());
			} catch (IOException e) {
				Log.getLogger().log(Level.SEVERE, "IOException", e);
			}
			f.setCoverPath("blo");
		
			f.setAlbumName("Bra bilder");
		
			DatabaseHandler.getInstance().insertPicture(f);
			i++;
		}
	}

}
