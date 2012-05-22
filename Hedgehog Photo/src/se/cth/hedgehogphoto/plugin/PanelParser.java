package se.cth.hedgehogphoto.plugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;

import javax.swing.JPanel;

import se.cth.hedgehogphoto.log.Log;
import se.cth.hedgehogphoto.view.MainView;
import se.cth.hedgehogphoto.view.PluginArea;

/**
 * @author Barnabas Sapan
 */

public class PanelParser implements Parsable {

	@Override
	public Object parseClass(Class<?> c, Object o, MainView view){
		Method methods[] = c.getMethods();
		for(int i = 0; i < methods.length; i++){
			try{
				if(methods[i].isAnnotationPresent(Panel.class)){
					Method method = c.getMethod(methods[i].getName(), null);
					if(method.getReturnType() == JPanel.class){
						PluginArea pluginArea = method.getAnnotation(Panel.class).placement();
						Log.getLogger().log(Level.INFO, "Panel placement: " + pluginArea );
						if(o != null){
							JPanel panel = (JPanel) method.invoke(o, (Object[])null);
							view.addPlugin(panel, pluginArea);
						} else {
							Log.getLogger().log(Level.SEVERE, "Class not Initialized, do you have @InitializePlugin annotation?");
						}
					} else {
						Log.getLogger().log(Level.SEVERE, "@Panel invalid return type!");
					}			
				}
			}catch (IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e){
				Log.getLogger().log(Level.SEVERE, "Exception", e);
			}
		}
		return o;
	}

}
