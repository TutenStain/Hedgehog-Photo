package se.cth.hedgehogphoto.plugin;

import se.cth.hedgehogphoto.view.MainView;

/**
 * @author Barnabas Sapan
 */

public interface Parsable {
	
	/**
	 * @param c the class to parse
	 * @param o	 the object to invoke the methods on. If null a new Object will be instantiated
	 * @param view the view the plugins should be added to
	 * @return Object the resulting object after invoking methods defined by this function, 
	 * probably a (new)instance of the class if the class had to be instantiated
	 */
	public Object parseMethods(Class<?> c, Object o, MainView view);
}
