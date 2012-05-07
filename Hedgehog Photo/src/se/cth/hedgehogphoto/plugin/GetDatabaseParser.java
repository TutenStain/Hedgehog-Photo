package se.cth.hedgehogphoto.plugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;

import se.cth.hedgehogphoto.database.DatabaseHandler;
import se.cth.hedgehogphoto.log.Log;
import se.cth.hedgehogphoto.view.MainView;

/**
 * @author Barnabas Sapan
 */

public class GetDatabaseParser implements Parsable {

	@Override
	public Object parseClass(Class<?> c, Object o, MainView view) {
		Method m[] = c.getMethods();
		for(int i = 0; i < m.length; i++){
			try{
			if(m[i].isAnnotationPresent(GetDatabase.class)){
				if(o == null){
					o = c.newInstance();
					Log.getLogger().log(Level.INFO, "Initializing plugin with class: " + o.getClass().getSimpleName());
				}
				Log.getLogger().log(Level.INFO, "Setting DB...");
				Method panel = c.getMethod(m[i].getName(), m[i].getParameterTypes());
				panel.invoke(o, DatabaseHandler.getInstance());
				break;
			}
			}catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e){
				Log.getLogger().log(Level.SEVERE, "Exception", e);
			}
		}
		
		return o;
	}

}
