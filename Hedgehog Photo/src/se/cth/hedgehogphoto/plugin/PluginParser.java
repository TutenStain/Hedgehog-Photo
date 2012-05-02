package se.cth.hedgehogphoto.plugin;

import se.cth.hedgehogphoto.view.MainView;

public class PluginParser implements Parsable {

	@Override
	public Object parseMethods(Class<?> c, Object o, MainView view) {
		if(c.isAnnotationPresent(Plugin.class)){
			Plugin p = c.getAnnotation(Plugin.class);
			System.out.println("[Plugin: " + p.name() + ", Version: " + p.version() + 
				", Author: " + p.author() + ", Description: " + p.description() + "]");	
		}
		
		return o;
	}
	

}
