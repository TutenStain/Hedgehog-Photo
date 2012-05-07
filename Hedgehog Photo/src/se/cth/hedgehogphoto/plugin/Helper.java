package se.cth.hedgehogphoto.plugin;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import se.cth.hedgehogphoto.log.Log;

/**
 * A helper class for plugin-related classes
 * @author Barnabas Sapan
 */

public final class Helper {

	/**
	 * Lists all files including the ones in subfolders
	 * @param dir the root folder to list files from
	 * @param filter the filter to apply when searching for files
	 * @return all the files that match the filter in the directory and subfolders to it
	 */
	public static List<File> getAllFilesInFolder(File dir, FilenameFilter filter) {
	    List<File> filesToReturn = new ArrayList<File>();
	    for (File f : dir.listFiles()) {
	        if (f.isDirectory()) {
	            filesToReturn.addAll(getAllFilesInFolder(f, filter));
	        } else if (filter.accept(dir, f.getName())) {
	            filesToReturn.add(f);
	        }
	    }
	    return filesToReturn;
	}
	
	/**
	 * Strips the last dot (.) and last forward-slash (/) from the String
	 * @param s the string to strip the last dot and last forward-slash from
	 * @return the stripped String
	 */
	public static String stripDotAndSlashFromString(String s){
		int dividerIndex = s.lastIndexOf("/") + 1;
		int dotPath = s.lastIndexOf(".");
		String finalString = s.substring(dividerIndex, dotPath);
		
		return finalString;
	}
	
	/**
	 * Return the default plugin parsers needed to parse plugins
	 * @return a list of the default plugin parsers needed to parse most plugins
	 * they gets returned to 
	 */
	public static List<Parsable> getDefaultPluginParsers(){
		List<Parsable> list = new ArrayList<Parsable>();
		Parsable a = new GetDatabaseParser();
		Parsable b = new GetVisibleFilesParser();
		Parsable c = new InitializePluginParser();
		Parsable d = new PanelParser();
		Parsable e = new PluginParser();
		list.add(a);
		list.add(b);
		list.add(c);
		list.add(d);
		list.add(e);
		
		return list;
	}
	
	/**
	 * Creates the plugin folder
	 * @param pluginRootDir the folder path to create
	 */
	public static void createPluginFolder(String pluginRootDir){
		//Creates a plugin directory in home/plugin
		//If the folder already exist nothing will be created.
		File createDir = new File(pluginRootDir);
		if(createDir.exists() == false){
			Log.getLogger().log(Level.INFO, "Plugin directory not found, creating new directory...");
			if(createDir.mkdirs() == false){
				//TODO Throw an appropriate exception
				Log.getLogger().log(Level.INFO, "Creating plugin directory failed, fatal error");
			} else {
				Log.getLogger().log(Level.INFO, "Plugin directory succesfully created!");
			}
		} else {
			Log.getLogger().log(Level.INFO, "Plugin dir exists, skipping creation...");
		}
	}
}
