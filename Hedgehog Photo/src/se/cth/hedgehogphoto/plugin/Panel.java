package se.cth.hedgehogphoto.plugin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * The method containing this annotation will be called
 * to get the view representation of the plugin.
 * This method will get called every time the
 * system feels it will want to refresh the view of the plugin.
 * @param placement the placement of this plugin.
 * @return JPanel
 */ 

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Panel{
	public PluginArea placement();
}