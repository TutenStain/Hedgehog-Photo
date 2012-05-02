package se.cth.hedgehogphoto.plugin;

import java.lang.reflect.Method;

import javax.swing.JPanel;

import se.cth.hedgehogphoto.view.MainView;

public class PanelParser implements Parsable {

	@Override
	public Object parseMethods(Class<?> c, Object o, MainView view){
		Method m[] = c.getMethods();
		for(int i = 0; i < m.length; i++){
			try{
				if(m[i].isAnnotationPresent(Panel.class)){
					Method panel = c.getMethod(m[i].getName(), null);
					if(panel.getReturnType() == JPanel.class){
						PluginArea pa = panel.getAnnotation(Panel.class).placement();
						System.out.println("Panel placement: " + pa );
						if(o != null){
							JPanel p = (JPanel) panel.invoke(o, (Object[])null);
							System.out.println("PANEL: " + p);
							view.addPlugin(p, pa);
							view.addToLeftPanel(p);
						} else {
							System.out.println("Class not Initialized, do you have @InitializePlugin annotation?");
						}
					} else {
						System.out.println("@Panel invalid return type");
					}			
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		return o;
	}
	
}
