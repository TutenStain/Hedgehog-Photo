package se.cth.hedgehogphoto.plugin;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;


/**
 * A custom class loader that loads classes and compiles them if necessary.
 * @author Barnabas
 */

public class FileClassLoader extends URLClassLoader{
	private String pluginRootDirectory;
	private Map<String, Class<?>> loadedClasses = new HashMap<String, Class<?>>();
	
	public FileClassLoader(URL[] urls) {
		super(urls);
		
		if(urls.length == 1){
			pluginRootDirectory = urls[0].getPath();
	
			System.out.println("Setting plugin directory: " + pluginRootDirectory);
			createPluginFolder(urls[0].getPath());
		} else {
			System.out.println("Error, only one file path supported at a time");
		}
		
	}
	
	private void createPluginFolder(String pluginRootDir){
		//Creates a plugin directory in home/plugin
		//If the folder allready exist nothing will be created.
		//TODO Creating the correct folder structure for packages and copying .class files to this folder.
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
	 * Loads a class from file. Location based on the root folder supplied in the constructor.
	 * Tries to compile the .java file if the .class is nonexistent or outdated.
	 * @param file the file name including the package like se.cth.hedgehogphoto.plugin.Tester
	 */
	
	@Override
	public Class<?> loadClass(String file){
		Class<?> c = null;
		boolean b = true;
		
		//Replace packages to a proper folderstructure
		String fileStub = file.replace( '.', '/' );
		
		String javaFilenamePath = pluginRootDirectory + fileStub + ".java";
		String classFilenamePath = pluginRootDirectory + fileStub + ".class";
		
		File javaFile = new File(javaFilenamePath);
		File classFile = new File(classFilenamePath);
		
		
		//Only compile if necessary
		if (javaFile.exists() && (!classFile.exists() || javaFile.lastModified() > classFile.lastModified())) {
			try {
				System.out.println(".class is outdated/nonexistend, compilation needed...");
				
				b = compile(javaFilenamePath);
				
				if(b == true){
					System.out.println("Compilation succesfull!");
				} else {
					System.out.println("Compilation failed!");
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		 }
			
		 //Try to...Return it if we already have loaded it.
		 c = (Class<?>)loadedClasses.get(file);
         if (c != null) {
               return c;
         }
         
         //Try to...Find it via loadclass
    	 try {
    		c = super.loadClass(file, true);
    		loadedClasses.put(file, c);
    	 } catch (ClassNotFoundException e) {
    		System.out.println(file + " not loaded!");
    		e.printStackTrace();
    	 }
         
		return c;
	}
	
	public boolean compile(String path) throws IOException {
		    System.out.println("Compiling " + path + "...");
		    int ret = -1;

		    //Copy our API to plugin dir
		 	Path target = Paths.get(pluginRootDirectory + "API.jar");
		    if(Files.exists(target) == false) {
		    	System.out.println("Copying API.jar to: " + pluginRootDirectory + "...");
		    	Path source = Paths.get(System.getProperty("user.dir") + System.getProperty("file.separator") + "API.jar");
				Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
		    } else {
		    	System.out.println("API.jar allready in place, skipping copying...");
		    }
		 
		    //TODO fix compiling on classes that rely on other classes.
		    Process p = Runtime.getRuntime().exec("javac -cp " + pluginRootDirectory + "API.jar " + path);
		    //Process p = Runtime.getRuntime().exec("javac -cp " + pluginRootDirectory + "API.jar /home/tutenstain/plugin/*.java");
		    //System.out.println("String: " + "javac -cp " + pluginRootDirectory + "API.jar /home/tutenstain/plugin/*.java");
		    //Process q = Runtime.getRuntime().exec("javac -cp " + pluginRootDirectory + "API.jar" + path);
		    
		    try {
		    	ret = p.waitFor();
		    } catch(InterruptedException ie) {
		    	System.out.println("We got interrupted, compilation failed!");
		    	System.out.println(ie);
		    }

		    System.out.println("Compiled with code: " + ret);
		    
		    if(ret == 1){
		    	System.out.println("Could not resolve compilation dependencies!");
		    }

		    //0 = no compilation errors
		    return ret==0;
	}
}