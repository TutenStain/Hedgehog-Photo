package se.cth.hedgehogphoto.plugin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import se.cth.hedgehogphoto.view.PluginArea;

/**
 * @author Barnabas Sapan
 */

/**
 * The method containing this annotation will be called
 * to get the view representation of the plugin.
 * This method will get called once to get the panel and 
 * attach it to the main view.
 * @param PluginArea the placement the placement of this plugin.
 * @return JPanel the view that will get added to program
 */ 
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Panel{
	public PluginArea placement();
}