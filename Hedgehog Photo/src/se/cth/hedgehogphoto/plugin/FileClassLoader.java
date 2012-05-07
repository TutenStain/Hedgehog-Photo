package se.cth.hedgehogphoto.plugin;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import se.cth.hedgehogphoto.log.Log;


/**
 * A custom class loader that loads classes and compiles them if necessary.
 * @author Barnabas Sapan
 */

public class FileClassLoader extends URLClassLoader {
	private String pluginRootDirectory;
	private Map<String, Class<?>> loadedClasses = new HashMap<String, Class<?>>();

	public FileClassLoader(URL[] urls) {
		super(urls);
		if(urls.length == 1){
			pluginRootDirectory = urls[0].getPath();
			addSubfolders();
		} else {
			Log.getLogger().log(Level.SEVERE, "Error, only one file path supported at a time");
		}
	}
	
	/**
	 * Appends the subfolders in pluginRootDirectory
	 * to the list of URLs to search for classes and resources.
	 */
	private void addSubfolders(){
		File file = new File(pluginRootDirectory);
		for(String dir : file.list()){
			if(file.isDirectory()){
				try {
					File subfolder = new File(file.getCanonicalPath().toString() + "/" + dir);
					Log.getLogger().log(Level.INFO, "Setting sub directoy: " + subfolder.toURI().toURL());
					addURL(subfolder.toURI().toURL());
				} catch (IOException e) {
					Log.getLogger().log(Level.SEVERE, "IOException", e);
				}
			}
		}
	}

	/**
	 * Loads a class from file. Location based on the root folder supplied in the constructor.
	 * Tries to compile the .java file if the .class is nonexistent or outdated.
	 * @param file the file name including the package like se.cth.hedgehogphoto.plugin.Tester
	 * excluding .java or .class at the end.
	 */
	@Override
	public Class<?> loadClass(String file){
		Class<?> c = null;
		
		//Replace packages to a proper folderstructure
		String fileStub = file.replace( '.', '/' );

		String javaFilenamePath = pluginRootDirectory + fileStub + ".java";
		String classFilenamePath = pluginRootDirectory + fileStub + ".class";

		File javaFile = new File(javaFilenamePath);
		File classFile = new File(classFilenamePath);


		//Only compile if necessary
		if (javaFile.exists() && (!classFile.exists() || javaFile.lastModified() > classFile.lastModified())) {
			try {
				Log.getLogger().log(Level.INFO, ".class is outdated/nonexistent, compilation needed...");

				if(compile(javaFilenamePath) == true){
					Log.getLogger().log(Level.INFO, "Compilation succesfull!");
				} else {
					Log.getLogger().log(Level.SEVERE, "Compilation failed!");
				}
			} catch (IOException e) {
				Log.getLogger().log(Level.SEVERE, "IOException", e);
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
			Log.getLogger().log(Level.SEVERE, "ClassNotFoundException: " + file + " not loaded!", e);
		}

		return c;
	}
	
	/**
	 * Compiles a specific file.
	 * @param path the filepath to the file to compile
	 * @return returns true if the compilation was successful, false if it failed due
	 * to compilation errors or if JDK is not installed on the system
	 * @throws IOException
	 */
	private boolean compile(String path) throws IOException {
		Log.getLogger().log(Level.INFO, "Compiling " + path + "...");

		//Copy our API to plugin dir
		Path target = Paths.get(pluginRootDirectory + "API.jar");
		if(Files.exists(target) == false) {
			Log.getLogger().log(Level.INFO, "Copying API.jar to: " + pluginRootDirectory + "...");
			Path source = Paths.get(System.getProperty("user.dir") + System.getProperty("file.separator") + "API.jar");
			Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
		} else {
			Log.getLogger().log(Level.INFO, "API.jar allready in place, skipping copying...");
		}
		
		//Handles the compiling
		boolean compilationResult = false;
		File fileToCompile = new File(path);
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		if(compiler != null){
			StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
			Iterable<? extends JavaFileObject> compUnits =  fileManager.getJavaFileObjects(fileToCompile);
			String classpath = pluginRootDirectory + "API.jar:" + pluginRootDirectory;
			final Iterable<String> options = Arrays.asList(new String[] { "-cp", classpath});
			compilationResult = compiler.getTask(null, fileManager, null, options, null, compUnits).call();        
		} else {
			Log.getLogger().log(Level.SEVERE, "No javacompiler found on system! ERROR");
		}
		
		return compilationResult;
	}
}