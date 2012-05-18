package se.cth.hedgehogphoto.plugin;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import se.cth.hedgehogphoto.log.Log;
import se.cth.hedgehogphoto.view.MainView;

/**
 * A class that handles the plugin-loading. 
 * To see how to write plugins that will work with Headgehog Photo
 * please see the available annotations.
 * @author Barnabas Sapan
 */

public class PluginLoader implements Runnable{
	private MainView view;
	private FileClassLoader classLoader;
	private String pluginRootDir;
	private boolean threaded = false;
	private List<Class<?>> loadedClasses = new LinkedList<Class<?>>();
	
	
	@Override
	public void run(){
		if(threaded == true){
			threaded = true;
			this.loadAllPlugins();
			this.parseClasses();
		} else {
			throw new NotThreadedException();
		}
	}
		
	public PluginLoader(MainView view, File pluginRootDir, boolean preLoad){
		this(view, pluginRootDir.getAbsolutePath(), preLoad);
	}
	
	/**
	 * The one and only constructor. The main entry point for this class.
	 * @param view the view the plugins will get placed onto
	 * @param pluginFolderName the plugin folder name, the folder will be
	 * created in the users home folder.
	 */
	public PluginLoader(MainView view, String pluginRootDir, boolean preLoad) {
		this.view = view;
		this.pluginRootDir = pluginRootDir;
		this.threaded = preLoad;
		
		try {		
			File f = new File(pluginRootDir);
			URL url = f.toURI().toURL(); 
			URL[] urls = new URL[]{url};
			Log.getLogger().log(Level.INFO, "Setting plugin directory: " + urls[0].getPath());
			Helper.createPluginFolder(new File(urls[0].getPath()));
			Helper.copyPluginsToFolder(new File(urls[0].getPath()));
			classLoader = new FileClassLoader(urls);
		} catch (MalformedURLException e) {
			Log.getLogger().log(Level.SEVERE, "MalformedURLException", e);
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
		List<File> files = Helper.getAllFilesInFolder(dir, new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return (name.toString().endsWith(".java"));// || name.toString().endsWith(".class"));
			}
		});
		
		//Helper.removeDuplicateClasses(files);
		
		//Loop trough all files and load the classes.
		//Only add classes the the loadedClasses list if they were loaded (!=null)
		for(File file : files) {
			String className = Helper.stripDotAndSlashFromString(file.toString());
			Log.getLogger().log(Level.INFO, "Loading class " + className + "...");
			Class<?> c = classLoader.loadClass(className);
			
			if(c != null){
				loadedClasses.add(c);
			}	
		}
		Log.getLogger().log(Level.INFO, "Loaded classes: " + loadedClasses.toString());
		
		if(threaded == false){
			parseClasses(loadedClasses, Helper.getDefaultPluginParsers());
		} else {
			threaded = false;
		}
	}
	
	/**
	 * Lazy method that just calls 
	 * parseClasses(List<Class<?>> list, List<Parsable> parsableAnnotations)
	 * with the appropriate parameters
	 */
	private void parseClasses(){
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
