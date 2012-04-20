package se.cth.hedgehogphoto.plugin;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * A class that handles the plugin-loading. 
 * To see how to write plugins that will work with Headgehog Photo
 * please see the available annotations.
 * @author Barnabas
 */

/*
 * The method containing this annotation will be runned
 * once to initialize the plugin. This is useful for setting
 * everything up and adding observers to the model etc if the plugin is using MVC.
 * @param null
 * @return void
 */
@Retention(RetentionPolicy.RUNTIME)
@interface InitializePlugin{
}

/*
 * The method containing this annotation will be called
 * to get the view representation of the plugin.
 * This method will get called every time the
 * system feels it will want to refresh the view of the plugin.
 * @param null
 * @return JComponent
 */ 
@Retention(RetentionPolicy.RUNTIME)
@interface getPanel{
}

public class Main {

	public static void main(String[] args) {
		//FileClassLoader f = new FileClassLoader("/home/tutenstain/git/Hedgehog-Photo/Hedgehog%20Photo/bin/se/cth/hedgehogphoto/annotation/");
	/*	String pluginDirectory = System.getProperty("user.home") + "/plugin";
		System.out.println(pluginDirectory);
		FileClassLoader f = new FileClassLoader(pluginDirectory);
	
		
		try {
			Object o = f.loadClass("se.cth.hedgehogphoto.annotation.Tester", true).newInstance();
			System.out.println(((Tester)o).getSomething());
			o = Class.forName("se.cth.hedgehogphoto.annotation.Tester").cast(o);

		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		//TODO search for @InitializePlugin to initialize everything (DONE), then @getPanel to get the JPanel back.
		try {
			//Creates a plugin directory in home/plugin
			//If the folder allready exist nothing will be created.
			//TODO Creating the correct folder structure for packages and copying .class files to this folder.
			String pluginDir = System.getProperty("user.home") + "/plugin";
			File createDir = new File(pluginDir);
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
		
			File f = new File(pluginDir);
			URL url = f.toURI().toURL(); 
			URL[] urls = new URL[]{url}; 
			ClassLoader l = new URLClassLoader(urls);
			Class<?> c = l.loadClass("se.cth.hedgehogphoto.plugin.Tester");
			Object o = c.newInstance();
			//Get all available methods and find the one with 
			//@InitializePlugin annotation and run it.
			Method mm[] = c.getMethods();
			for(int i = 0; i < mm.length; i++){
				if(mm[i].isAnnotationPresent(InitializePlugin.class)){
					Method init = c.getMethod(mm[i].getName(), null);
					System.out.println(init.invoke(o, null));
				}
			}
	

		} catch (ClassNotFoundException | MalformedURLException |  IllegalArgumentException| NoSuchMethodException | SecurityException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
			e.printStackTrace();
		}
		
		

	
	}
}
