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
			Plugin plugin = c.getAnnotation(Plugin.class);
			Log.getLogger().log(Level.INFO, "[Plugin: " + plugin.name() + ", Version: " + plugin.version() + 
				", Author: " + plugin.author() + ", Description: " + plugin.description() + "]");	
		}
		
		return o;
	}
}
