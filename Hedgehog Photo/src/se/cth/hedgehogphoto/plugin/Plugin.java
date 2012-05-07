package se.cth.hedgehogphoto.plugin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Barnabas Sapan
 */

/** 
 *	A class annotation for the plugin. This is useful 
 *	for providing information about the plugin.
 *	@param name the pluging name
 *	@param version the plugin version
 *	@param author the plugin author
 *	@param description the description of the plugin 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Plugin{
	public String name() default "";
	public String version() default "";
	public String author() default "";
	public String description() default "";	
}