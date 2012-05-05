package se.cth.hedgehogphoto.plugin;

import java.util.logging.Level;

import se.cth.hedgehogphoto.log.Log;
import se.cth.hedgehogphoto.view.MainView;

/**
 * @author Barnabas Sapan
 */

public class PluginParser implements Parsable {

	@Override
	public Object parseClass(Class<?> c, Object o, MainView view) {
		if(c.isAnnotationPresent(Plugin.class)){
			Plugin p = c.getAnnotation(Plugin.class);
			Log.getLogger().log(Level.INFO, "[Plugin: " + p.name() + ", Version: " + p.version() + 
				", Author: " + p.author() + ", Description: " + p.description() + "]");	
		}
		
		return o;
	}
}
