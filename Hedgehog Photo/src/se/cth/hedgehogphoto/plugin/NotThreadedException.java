package se.cth.hedgehogphoto.plugin;

/**
 * A custom exception that is for the developers. 
 * This should be thrown if we specify that we do not want to thread
 * the class in the constructor and we try to do it anyway.
 * @author Barnabas Sapan
 */
@SuppressWarnings("serial")
public class NotThreadedException extends RuntimeException {
	public NotThreadedException(){
		super("Accoring to constructor we should not thread.");
	}
}
