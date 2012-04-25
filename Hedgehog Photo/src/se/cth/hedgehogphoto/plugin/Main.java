package se.cth.hedgehogphoto.plugin;


import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JComponent;

/**
 * A class that handles the plugin-loading. 
 * To see how to write plugins that will work with Headgehog Photo
 * please see the available annotations.
 * @author Barnabas
 */

public class Main {

	public static void main(String[] args) {
		//Searches for @InitializePlugin to initialize everything, 
		//hen @Panel to get the JPanel (JComponent) back.
		try {	
			String pluginRootDir = System.getProperty("user.home") + "/plugin";
			File f = new File(pluginRootDir);
			URL url = f.toURI().toURL(); 
			URL[] urls = new URL[]{url}; 
			FileClassLoader l = new FileClassLoader(urls);
			
			File directory = new File(pluginRootDir);
			File[] files = directory.listFiles(new FileFilter() {
				
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
			
				Class<?> c = l.loadClass(finalPath);
				Object o = c.newInstance();
				//Get all available methods and find the one with 
				//@InitializePlugin annotation and run it.
				Method mm[] = c.getMethods();
				for(int i = 0; i < mm.length; i++){
					if(mm[i].isAnnotationPresent(InitializePlugin.class)){
						Method init = c.getMethod(mm[i].getName(), null);
						System.out.println(init.invoke(o, null));
					}
					
					if(mm[i].isAnnotationPresent(Panel.class)){
						Method panel = c.getMethod(mm[i].getName(), null);
						if(panel.getReturnType() == JComponent.class){
							System.out.println(panel.invoke(o, null));
						} else {
							System.out.println("@Panel invalid return type");
						}				
					}
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
