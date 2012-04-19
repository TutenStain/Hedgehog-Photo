package se.cth.hedgehogphoto.annotation;

import java.lang.reflect.Method;

public class Main {

	public static void main(String[] args) {
		
		FileClassLoader f = new FileClassLoader("/home/tutenstain/git/Hedgehog-Photo/Hedgehog%20Photo/bin/se/cth/hedgehogphoto/annotation/");
		Class<?> c = null;
		Object o = null;
		
		try {
			c = f.loadClass("se.cth.hedgehogphoto.annotation.Tester", true);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			o = c.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
