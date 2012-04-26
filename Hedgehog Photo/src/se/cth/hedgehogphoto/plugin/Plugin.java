package se.cth.hedgehogphoto.plugin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* 
 *	A class annotation for the plugin. This is useful 
 *	for providing information about the plugin.
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Plugin{
	public String name() default "";
	public String version() default "";
	public String author() default "";
	public String description() default "";	
}