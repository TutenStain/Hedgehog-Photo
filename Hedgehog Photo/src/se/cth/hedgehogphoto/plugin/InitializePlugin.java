package se.cth.hedgehogphoto.plugin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Barnabas Sapan
 */

/**
 * The method containing this annotation will be runned
 * once to initialize the plugin. This is useful for setting
 * everything up and adding observers to the model etc if the plugin is using MVC.
 * @param null
 * @return void
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InitializePlugin{
}