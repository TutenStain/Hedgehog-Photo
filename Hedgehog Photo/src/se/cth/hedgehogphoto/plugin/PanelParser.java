package se.cth.hedgehogphoto.plugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;

import javax.swing.JPanel;

import se.cth.hedgehogphoto.log.Log;
import se.cth.hedgehogphoto.view.MainView;

/**
 * @author Barnabas Sapan
 */

public class PanelParser implements Parsable {

	@Override
	public Object parseClass(Class<?> c, Object o, MainView view){
		Method m[] = c.getMethods();
		for(int i = 0; i < m.length; i++){
			try{
				if(m[i].isAnnotationPresent(Panel.class)){
					Method panel = c.getMethod(m[i].getName(), null);
					if(panel.getReturnType() == JPanel.class){
						PluginArea pa = panel.getAnnotation(Panel.class).placement();
						Log.getLogger().log(Level.INFO, "Panel placement: " + pa );
						if(o != null){
							JPanel p = (JPanel) panel.invoke(o, (Object[])null);
							view.addPlugin(p, pa);
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
