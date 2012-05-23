package se.cth.hedgehogphoto;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import se.cth.hedgehogphoto.controller.MainViewInitiator;
import se.cth.hedgehogphoto.database.DatabaseHandler;
import se.cth.hedgehogphoto.log.Log;
import se.cth.hedgehogphoto.metadata.Metadata;
import se.cth.hedgehogphoto.objects.FileObject;
import se.cth.hedgehogphoto.objects.LocationGPSObject;
import se.cth.hedgehogphoto.plugin.PluginLoader;
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
		insertTestFileObjectsIntoDatabase();

		MainViewInitiator mainView = new MainViewInitiator(start);
		mainView.getMainModel().startNotify();
		se.cth.hedgehogphoto.database.Files.getInstance().addObserver(mainView.getMainView());

		File pluginRooDir = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "plugin");
		PluginLoader pluginLoader = new PluginLoader(mainView.getMainView(), pluginRooDir, true);
		Thread pluginLoaderT = new Thread(pluginLoader);
		pluginLoaderT.start();	
	}
	
	/**
	 * Just adds the pictures in the supplied Pictures folder to the program
	 * with some "random" tags, comment, location and date.
	 */
	private static void insertTestFileObjectsIntoDatabase() {
		
		File directory = new File("Pictures");
		File[] files = directory.listFiles();
		
		int i = 0;
		for(File file : files) {
			FileObject f = Metadata.getImageObject(file);
			f.setLocationObject(new LocationGPSObject("Japan"));
			f.setComment("Gutes bild");
			f.setFileName(file.getName());
			f.setDate("2012-05-19");
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
