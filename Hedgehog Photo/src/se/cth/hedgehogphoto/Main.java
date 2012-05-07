package se.cth.hedgehogphoto;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import se.cth.hedgehogphoto.database.DatabaseHandler;
import se.cth.hedgehogphoto.database.Files;
import se.cth.hedgehogphoto.log.Log;
import se.cth.hedgehogphoto.map.controller.MapController;
import se.cth.hedgehogphoto.map.model.MapModel;
import se.cth.hedgehogphoto.map.view.MapView;
import se.cth.hedgehogphoto.metadata.Metadata;
import se.cth.hedgehogphoto.plugin.PluginLoader;
import se.cth.hedgehogphoto.search.SearchController;
import se.cth.hedgehogphoto.search.SearchModel;
import se.cth.hedgehogphoto.search.SearchPreviewView;
import se.cth.hedgehogphoto.search.SearchView;
import se.cth.hedgehogphoto.view.MainView;


/**
 * @author Barnabas Sapan
 */

public class Main {

	//TODO Just a skeleton of the main
	public static void main(String[] args) {
		DatabaseHandler.getInstance().deleteAll();
		//Main
		insertFileObjectsIntoDatabase();
		MainModel model = new MainModel();
		MainView view = new MainView();
		model.addObserver(view);
		
		File pluginRootDir = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "plugin");
		PluginLoader p = new PluginLoader(view, pluginRootDir);
		p.loadAllPlugins();
		//File tagCloudDir = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "plugin/TagCloud");
		//p.loadPluginFromDirectory(tagCloudDir);
		
		//TagCloud
		/*List<String> l = new ArrayList<String>();
		l = new DatabaseHandler().getTags();
		TagCloudModel tgm = new TagCloudModel();
		TagCloudView tgv = new TagCloudView();
		tgm.addObserver(tgv);
		tgm.setTags(l);
		view.addToLeftPanel(tgv);*/
		
		//map
		MapModel mapModel = new MapModel();
		MapView map = new MapView(mapModel);
        MapController mapController = new MapController(map);
		view.addToLeftPanel(map);
		
		//Search
		SearchPreviewView spv = new SearchPreviewView();
		SearchView sv = new SearchView(spv);
		SearchModel sm = new SearchModel(sv, spv);
		sv.setPreferredSize(new Dimension(250, 30));
		new SearchController(sm, sv);

		Files.getInstance().addObserver(view);

		view.addToTopPanel(sv, BorderLayout.EAST);

		model.testNotify();
	}
	
	private static void insertFileObjectsIntoDatabase() {
		
		File directory = new File("Pictures");
		File[] files = directory.listFiles();
		
		int i = 0;
		for(File file : files) {
			FileObject f = Metadata.getImageObject(file);
			
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
			if (f.getLocation() == null) {
				f.setLocation(new LocationObject("Japan"));
			}
			f.setAlbumName("Bra bilder");
			System.out.println(f.getLocation().getLongitude() + " and " + f.getLocation().getLatitude());
			DatabaseHandler.getInstance().insertPicture(f);
			i++;
		}
	}

}
