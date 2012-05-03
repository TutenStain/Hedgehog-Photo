package se.cth.hedgehogphoto.plugin;

import java.lang.reflect.Method;

import se.cth.hedgehogphoto.database.DatabaseHandler;
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
					System.out.println("Initializing plugin with class: " + o.getClass().getSimpleName());
				}
				System.out.println("Setting DB...");
				Method panel = c.getMethod(m[i].getName(), m[i].getParameterTypes());
				panel.invoke(o, DatabaseHandler.getInstance());
				break;
			}
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		
		return o;
	}

}
