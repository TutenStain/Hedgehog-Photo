package se.cth.hedgehogphoto.plugin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Barnabas Sapan
 */

/**
 * The method with this annotation will be used to set the database to the plugin.
 * @param DatabaseAcess the pluginloader will set the db using this parameter
 * @return void
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GetDatabase{
}