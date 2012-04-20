package se.cth.hedgehogphoto.plugin;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Tester {
	public Tester(){
		System.out.println("TESTER");
	}
	
	public String getSomething(){
		return "HEJ";
	}
	
	@InitializePlugin
	public String init(){
		return "INIT";
	}
}
