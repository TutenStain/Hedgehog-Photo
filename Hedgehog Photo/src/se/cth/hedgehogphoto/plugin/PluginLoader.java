package se.cth.hedgehogphoto.plugin;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import se.cth.hedgehogphoto.Util;
import se.cth.hedgehogphoto.log.Log;
import se.cth.hedgehogphoto.view.MainView;

/**
 * A class that handles the plugin-loading. 
 * To see how to write plugins that will work with Headgehog Photo
 * please see the available annotations.
 * @author Barnabas Sapan
 */

public class PluginLoader {
	private MainView view;
	private FileClassLoader classLoader;
	private String pluginRootDir;
		
	public PluginLoader(MainView view, File pluginRootDir){
		this(view, pluginRootDir.toPath().toString());
	}

	/**
	 * The one and only constructor. The main entry point for this class.
	 * @param view the view the plugins will get placed onto
	 * @param pluginFolderName the plugin folder name, the folder will be
	 * created in the users home folder.
	 */
	public PluginLoader(MainView view, String pluginRootDir) {
		this.view = view;
		this.pluginRootDir = pluginRootDir;
		
		try {		
			File f = new File(pluginRootDir);
			URL url = f.toURI().toURL(); 
			URL[] urls = new URL[]{url};
			Log.getLogger().log(Level.INFO, "Setting plugin directory: " + urls[0].getPath());
			Helper.createPluginFolder(urls[0].getPath());
			classLoader = new FileClassLoader(urls);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads all the available plugins in the plugin root folder specified by the constructor
	 */
	public void loadAllPlugins(){
		loadPluginFromDirectory(new File(pluginRootDir));
	}
		

	/**
	 * Loads a specific (or all if more plugins exist in same dir) plugin to the program
	 * @param dir the absolute path of the directory to the plugin
	 */
	
	public void loadPluginFromDirectory(File dir){
		List<Class<?>> loadedClasses = new ArrayList<Class<?>>();
		List<File> files = Helper.getAllFilesInFolder(dir, new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return (name.toString().endsWith(".java"));
			}
		});
		
		//Loop trough all files and load the classes.
		for(File file : files) {
			String className = Helper.stripDotAndSlashFromString(file.toString());
			Log.getLogger().log(Level.INFO, "Loading class " + className + "...");
			loadedClasses.add(classLoader.loadClass(className));
		}

		parseClasses(loadedClasses, Helper.getDefaultPluginParsers());
	}

	/**
	 * This method parses all the classes in the list supplied to this method.
	 * @param list the list of classes to parse
	 * @param parsableAnnotations classes that implements Parsable to handle the parsing.
	 * The classes get parsed according to the order of the list. 
	 */
	private void parseClasses(List<Class<?>> list, List<Parsable> parsableAnnotations){
		for(Class<?> c : list) {
			Object o = null;
			for(Parsable p : parsableAnnotations){
				o = p.parseClass(c, o, view);
			}
		}
	}
}