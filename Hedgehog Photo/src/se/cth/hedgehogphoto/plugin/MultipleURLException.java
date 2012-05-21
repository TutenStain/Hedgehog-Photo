package se.cth.hedgehogphoto.plugin;

/**
 * A custom exception that is for the developers.
 * This should be thrown if we supplied multiple URLs
 *  but only one (1) is supported.
 * @author Barnabas Sapan
 */
@SuppressWarnings("serial")
public class MultipleURLException extends RuntimeException {
	
	public MultipleURLException(){
		super("Multiple URLs supplied, only one supported.");
	}
	
	public MultipleURLException(String message){
		super("Multiple URLs supplied, only one supported. Reason: " + message);
	}
}
