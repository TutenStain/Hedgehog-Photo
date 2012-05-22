package se.cth.hedgehogphoto.plugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;

import se.cth.hedgehogphoto.log.Log;
import se.cth.hedgehogphoto.view.MainView;

/**
 * @author Barnabas Sapan
 */

public class InitializePluginParser implements Parsable{

	@Override
	public Object parseClass(Class<?> c, Object o, MainView view) {
		Method methods[] = c.getMethods();
		for(int i = 0; i < methods.length; i++){
			try{
				if(methods[i].isAnnotationPresent(InitializePlugin.class)){
					if(o == null){
						o = c.newInstance();					
						Log.getLogger().log(Level.INFO, "Initializing plugin with class: " + o.getClass().getSimpleName());
					}
					Method init = c.getMethod(methods[i].getName(), null);
					init.invoke(o, (Object[])null);
					break;
				}
			}catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e){
				Log.getLogger().log(Level.SEVERE, "Exception", e);
			}
		}

		return o;
	}

}
