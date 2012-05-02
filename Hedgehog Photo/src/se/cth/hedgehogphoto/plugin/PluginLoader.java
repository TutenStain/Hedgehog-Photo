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

import se.cth.hedgehogphoto.view.MainView;

/**
 * A class that handles the plugin-loading. 
 * To see how to write plugins that will work with Headgehog Photo
 * please see the available annotations.
 * @author Barnabas Sapan
 */

public class PluginLoader {
	private MainView view;
	
	public PluginLoader(MainView view) {
		this.view = view;
		List<Class<?>> map = new ArrayList<Class<?>>();
		
		try {	
			String pluginRootDir = System.getProperty("user.home") + System.getProperty("file.separator") + "plugin";
			File f = new File(pluginRootDir);
			URL url = f.toURI().toURL(); 
			URL[] urls = new URL[]{url}; 
			FileClassLoader l = new FileClassLoader(urls);
			
			File directory = new File(pluginRootDir);
			File[] files = directory.listFiles(new FileFilter() {
				
				//Only accept .java files
				@Override
				public boolean accept(File pathname) {
					return (pathname.toString().endsWith(".java"));
				}
			});
	
			for(File file : files) {
				String path = file.toString();
				int dividerIndex = path.lastIndexOf("/") + 1;
				int dotPath = path.lastIndexOf(".");
				String finalPath = path.substring(dividerIndex, dotPath);
				System.out.println("Loading class " + finalPath + "...");
				map.add(l.loadClass(finalPath));			
			}
			
			parseClasses(map);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method parses all the classes in the list supplied to this method.
	 * Currently accepting the following annotations:
	 * @InitializePlugin, @Panel, @Plugin
	 * @param list the list of classes to parse
	 */
	private void parseClasses(List<Class<?>> list){
		try{
			for(Class<?> c : list) {
				Object o = null;
				//Get all available methods and find the one with 
				//@InitializePlugin annotation and run it.
				Method mm[] = c.getMethods();
				
				//This gets loaded FIRST (1)
				//3 loops needed to load them in correct order
				for(int i = 0; i < mm.length; i++){
					if(mm[i].isAnnotationPresent(GetDatabase.class)){
						if(o == null){
							o = c.newInstance();
							System.out.println("Initializing plugin with class: " + o.getClass().getSimpleName());
						}
						System.out.println("Setting DB...");
						Method panel = c.getMethod(mm[i].getName(), mm[i].getParameterTypes());
						panel.invoke(o, DatabaseHandler.getInstance());
					}
				}
				
				//This gets loaded SECOND (2) or FIRST (1) depending on
				//if the plugin needs access to the database or not.
				//needed to loop trough it again so that this would get loaded 
				for(int i = 0; i < mm.length; i++){
					if(mm[i].isAnnotationPresent(InitializePlugin.class)){
						if(o == null){
							o = c.newInstance();
							System.out.println("Initializing plugin with class: " + o.getClass().getSimpleName());
						}
						Method init = c.getMethod(mm[i].getName(), null);
						init.invoke(o, (Object[])null);
					}
				}
				
				//This gets loaded THIRD (3)
				//This way @Panel annotation will get loaded last.
				for(int i = 0; i < mm.length; i++){
					if(mm[i].isAnnotationPresent(Panel.class)){
						Method panel = c.getMethod(mm[i].getName(), null);
						if(panel.getReturnType() == JPanel.class){
							PluginArea pa = panel.getAnnotation(Panel.class).placement();
							System.out.println("Panel placement: " + pa );
							if(o != null){
								JPanel p = (JPanel) panel.invoke(o, (Object[])null);
								System.out.println("PANEL: " + p);
								view.addPlugin(p, pa);
								view.addToLeftPanel(p);
							} else {
								System.out.println("Class not Initialized, do you have @InitializePlugin annotations?");
							}
						} else {
							System.out.println("@Panel invalid return type");
						}			
					}
				}
				
				if(c.isAnnotationPresent(Plugin.class)){
					Plugin p = c.getAnnotation(Plugin.class);
					System.out.println("[Plugin: " + p.name() + ", Version: " + p.version() + 
						", Author: " + p.author() + ", Description: " + p.description() + "]");	
				}
			}
		} catch (Exception e) {
			System.out.println("Something went wrong...");
			System.out.println(e);
		}
	}
}
