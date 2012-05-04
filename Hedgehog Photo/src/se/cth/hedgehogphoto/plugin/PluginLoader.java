package se.cth.hedgehogphoto.plugin;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import se.cth.hedgehogphoto.view.MainView;

/**
 * A class that handles the plugin-loading. 
 * To see how to write plugins that will work with Headgehog Photo
 * please see the available annotations.
 * @author Barnabas Sapan
 */

public class PluginLoader {
	private MainView view;
	private FileClassLoader l;
	private String pluginRootDir;
		
	public PluginLoader(MainView view, File pluginRootDir){
		this(view, pluginRootDir.toPath().toString());
	}

	/**
	 * The one and only constructor.
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
			System.out.println("Setting plugin directory: " + urls[0].getPath());
			createPluginFolder(urls[0].getPath());
			l = new FileClassLoader(urls);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads all the available plugins in the plugin root folder specified by the constructor
	 */
	
	public void loadAllPlugins(){
		List<Class<?>> loadedClasses = new ArrayList<Class<?>>();
		File directory = new File(pluginRootDir);
		List<File> files = getFiles(directory, new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return (name.toString().endsWith(".java"));
			}
		});
		

		//Loop trough all files and load the classes.
		for(File file : files) {
			//Strip the forward slash and dot from the file name.
			String path = file.toString();
			int dividerIndex = path.lastIndexOf("/") + 1;
			int dotPath = path.lastIndexOf(".");
			String finalPath = path.substring(dividerIndex, dotPath);
			System.out.println("Loading class " + finalPath + "...");
			loadedClasses.add(l.loadClass(finalPath));
		}

		//TODO refactor out these so that the user can customize this (make setters and getters).
		List<Parsable> list = new ArrayList<Parsable>();
		Parsable a = new GetDatabaseParser();
		Parsable b = new InitializePluginParser();
		Parsable c = new PanelParser();
		Parsable d = new PluginParser();
		list.add(a);
		list.add(b);
		list.add(c);
		list.add(d);

		parseClasses(loadedClasses, list);
	}
	
	/**
	 * Lists all files including the ones in subfolders
	 * @param dir the root folder to list files from
	 * @param filter the filter to apply when searching for files
	 * @return all the files that match the filter in the directory and subfolders to it
	 */
	private List<File> getFiles(File dir, FilenameFilter filter) {
	    List<File> ret = new ArrayList<File>();
	    for (File f : dir.listFiles()) {
	        if (f.isDirectory()) {
	            ret.addAll(getFiles(f, filter));
	        } else if (filter.accept(dir, f.getName())) {
	            ret.add(f);
	        }
	    }
	    return ret;
	}
	
	/**
	 * Creates the plugin folder
	 * @param pluginRootDir the folder path to create
	 */
	private void createPluginFolder(String pluginRootDir){
		//Creates a plugin directory in home/plugin
		//If the folder already exist nothing will be created.
		File createDir = new File(pluginRootDir);
		if(createDir.exists() == false){
			System.out.println("Plugin directory not found, creating new directory...");
			if(createDir.mkdirs() == false){
				//TODO Throw an appropriate exception
				System.out.println("Creating plugin directory failed, fatal error");
			} else {
				System.out.println("Plugin directory succesfully created!");
			}
		} else {
			System.out.println("Plugin dir exists, skipping creation...");
		}
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
