package se.cth.hedgehogphoto.plugin;

import java.lang.reflect.Method;

import se.cth.hedgehogphoto.view.MainView;

public class InitializePluginParser implements Parsable{

	@Override
	public Object parseMethods(Class<?> c, Object o, MainView view) {
		Method m[] = c.getMethods();
		for(int i = 0; i < m.length; i++){
			try{
				if(m[i].isAnnotationPresent(InitializePlugin.class)){
					if(o == null){
						o = c.newInstance();					
						System.out.println("Initializing plugin with class: " + o.getClass().getSimpleName());
					}
					Method init = c.getMethod(m[i].getName(), null);
					init.invoke(o, (Object[])null);
				}
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return o;
	}

}
