package se.cth.hedgehogphoto.plugin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Barnabas Sapan
 */

/**
 * The method with this annotation will be used to set the Files to the plugin.
 * Files is the class that contains the currently visible files in the view. 
 * @param Files the pluginloader will set the Files using this parameter
 * @return void
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GetVisibleFiles{
}