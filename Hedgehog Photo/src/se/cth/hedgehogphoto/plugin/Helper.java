package se.cth.hedgehogphoto.plugin;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
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
	 * Strips the dot (.) and forward-slash (/) from the String, resulting in
	 * just the class name. For example "home//plugin/Main.java" becomes Main
	 * @param s the string to strip the dot and forward-slash from
	 * @return the stripped String
	 */
	public static String stripDotAndSlashFromString(String s){
		int dividerIndex = s.lastIndexOf(System.getProperty("file.separator")) + 1;
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
	 * @param createDir the folder path to create
	 * @return returns true upon sucesfully folder creation, otherwise false
	 */
	public static boolean createPluginFolder(final File createDir){
		//Creates a plugin directory in home/plugin
		//If the folder already exist nothing will be created.
		if(createDir.exists() == false){
			Log.getLogger().log(Level.INFO, "Plugin directory not found, creating new directory...");
			if(createDir.mkdirs() == false){
				Log.getLogger().log(Level.SEVERE, "Creating plugin directory failed, fatal error");
				return false;
			} else {
				Log.getLogger().log(Level.INFO, "Plugin directory succesfully created!");
				return true;
			}
		} else {
			Log.getLogger().log(Level.INFO, "Plugin dir exists, skipping creation...");
			return true;
		}
	}
	
	/**
	 * This method checks if the file exist and if it does not exist it searches 
	 * for the file in the subfolders. Appends filestub and suffix to the found file.
	 * @param f the file to find
	 * @param filestub the filestub to append at the new file
	 * @param suffix the suffix to append at the new file
	 * @param subFolders the subfolders to search the file in
	 * @return a File object with the absolute path with filestub and suffic appended.
	 */
	public static File findFileInSubfolder(File f, String filestub, String suffix, URL[] subFolders){
		if(f.exists() == false){
			for(URL u : subFolders){
				f = new File(u.getPath() + filestub + suffix);
				if(f.exists()){
					break;
				}
			}
		}
		
		return f;
	}
	
	/**
	 * Finds the folder the supplied file is in. 
	 * This is just a lazy method striping the last forward-slash (/) or backward-slash (\)
	 * depending on system from the path.
	 * @param f the file to get folder to
	 * @return a File object representing the folder the supplied file is in.
	 */
	public static File findFolderForFile(final File f){
		String d = f.getAbsolutePath();
		return new File(d.substring(0, d.lastIndexOf(System.getProperty("file.separator"))));
	}
	
	public static boolean copyPluginsToFolder(final File folder){
		if(folder.isDirectory()){
			final Path targetPath = Paths.get(folder.getAbsolutePath());
			final Path sourcePath =  Paths.get(System.getProperty("user.dir") + System.getProperty("file.separator") + "plugins");;
			try {
				Files.walkFileTree(sourcePath, new SimpleFileVisitor<Path>() {
					@Override
					public FileVisitResult preVisitDirectory(final Path dir,
							final BasicFileAttributes attrs) throws IOException {
						Files.createDirectories(targetPath.resolve(sourcePath
								.relativize(dir)));
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFile(final Path file,
							final BasicFileAttributes attrs) throws IOException {
						Files.copy(file,
								targetPath.resolve(sourcePath.relativize(file)));
						return FileVisitResult.CONTINUE;
					}
				});
			} catch (IOException e) {
				Log.getLogger().log(Level.SEVERE, "IOException", e);
				return false;
			}
			return true;
		} else {
			return false;
		}
	}
	
	@Deprecated /*Does not work */
	public static List<File> removeDuplicateClasses(List<File> files){
		List<File> ret = new ArrayList<File>();
		for(File f : files){
			for(File ff : files){
				if(stripDotAndSlashFromString(f.getAbsolutePath()).equals(stripDotAndSlashFromString(ff.getAbsolutePath())) == false){
					if(f.getName().endsWith(".class")){
						System.out.println("name: " + f.getName());
						ret.add(f);
					} 
					
				}
			}
		}
		
		Log.getLogger().log(Level.SEVERE, ret.toString());
		return null;
	}
}
