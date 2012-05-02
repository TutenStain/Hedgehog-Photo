package se.cth.hedgehogphoto.plugin;

import se.cth.hedgehogphoto.database.DatabaseHandler;
import se.cth.hedgehogphoto.view.MainView;
import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;


/**
 * A class that handles the plugin-loading. 
 * To see how to write plugins that will work with Headgehog Photo
 * please see the available annotations.
 * @author Barnabas Sapan
 */

public class PluginLoader {
	private MainView view;
	FileClassLoader l;
	
	/**
	 * The one and only constructor.
	 * @param view the view the plugins will get placed onto
	 * @param pluginFolderName the plugin folder name, the folder will be
	 * created in the users home folder.
	 */
	public PluginLoader(MainView view, String pluginFolderName) {
		this.view = view;
		List<Class<?>> map = new ArrayList<Class<?>>();
		
		try {	
			String pluginRootDir = System.getProperty("user.home") + System.getProperty("file.separator") + pluginFolderName;
			File f = new File(pluginRootDir);
			URL url = f.toURI().toURL(); 
			URL[] urls = new URL[]{url}; 
			l = new FileClassLoader(urls);
			
			File directory = new File(pluginRootDir);
			File[] files = directory.listFiles(new FileFilter() {
				
				//Only accept .java files
				@Override
				public boolean accept(File pathname) {
					return (pathname.toString().endsWith(".java"));
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
				map.add(l.loadClass(finalPath));
			}
			
			List<Parsable> list = new ArrayList<Parsable>();
			Parsable a = new GetDatabaseParser();
			Parsable b = new InitializePluginParser();
			Parsable c = new PanelParser();
			Parsable d = new PluginParser();
			list.add(a);
			list.add(b);
			list.add(c);
			list.add(d);
			
			parseClasses(map, list);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method parses all the classes in the list supplied to this method.
	 * Currently parsing the following annotations:
	 * @InitializePlugin, @Panel, @Plugin
	 * @param list the list of classes to parse
	 */
	private void parseClasses(List<Class<?>> list, List<Parsable> parsableMethods){
			for(Class<?> c : list) {
				Object o = null;
				for(Parsable p : parsableMethods){
					o = p.parseMethods(c, o, view);
				}
			}
	}
}
