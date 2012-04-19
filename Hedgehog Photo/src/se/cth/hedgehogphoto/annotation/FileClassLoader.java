package se.cth.hedgehogphoto.annotation;

import java.io.*;
import java.net.*;
import java.util.*;

public class FileClassLoader extends ClassLoader {

	private String root;

	public FileClassLoader (String rootDir) {
		root = rootDir;
	}

	@Override
	protected Class<?> loadClass (String name, boolean resolve) throws ClassNotFoundException {

		// Since all support classes of loaded class use same class loader
		// must check subclass cache of classes for things like Object
		
		//TODO SecurityManager
		//SecurityManager sm = System.getSecurityManager();
		
		Class<?> c = findLoadedClass (name);
		if (c == null) {
			try {
				c = findSystemClass (name);
			} catch (Exception e) {
			}
		}

		if (c == null) {
			// Convert class name argument to filename
			// Convert package names into subdirectories
			String filename = name.replace ('.', File.separatorChar) + ".class";

			try {
				byte data[] = loadClassData(filename);
				c = defineClass (name, data, 0, data.length);
				if (c == null)
					throw new ClassNotFoundException (name);
			} catch (IOException e) {
				throw new ClassNotFoundException ("Error reading file: " + filename);
			}
		}
		if (resolve)
			resolveClass (c);
		return c;
	}
	
	private byte[] loadClassData (String filename) throws IOException {

		//File object relative to directory provided in constructor
		File f = new File (root, filename);
		int size = (int)f.length();
		byte buff[] = new byte[size];

		FileInputStream fis = new FileInputStream(f);
		DataInputStream dis = new DataInputStream (fis);

		dis.readFully (buff);

		dis.close();

		return buff;
	}
}
