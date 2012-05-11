package se.cth.hedgehogphoto.map.geocoding.model;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.swing.SwingUtilities;

public class URLCreator {
	private static URLCreator urlCreator;
	private StringBuilder builder;
	private final String NAMEFINDER_URL = "http://nominatim.openstreetmap.org/search?";
	private final String XML_FORMAT = "format=xml";
	private final String ADDRESS_DETAILS = "addressdetails=1";
	private final String QUERY = "q=";
	private final String EMAIL = "email=";
	private final char AMPERSAND = '&';
	
	
	private URLCreator() {
	}
	
	public static URLCreator getInstance() {
		if (urlCreator == null)
			urlCreator = new URLCreator();
		return urlCreator;
	}
	
	public synchronized void invokeLater() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					Thread.sleep(1000); //makes the thread sleep 1 sec before it returns a string
				} catch (InterruptedException e) {
					
				}
				
			}
		});
	}
      
	
	public URL queryURL(String query) {
		initializeStringBuilder();
		if (query == null) {
			query = "";
		}
		query = encodeString(query);
		builder.append(query);
		try {
			invokeLater();
			return new URL(builder.toString());
		} catch (MalformedURLException e) {
			System.out.println(this.builder.toString() + "error"); //TODO: log it?
			return null;
		}
	}
	
	private String encodeString(String string) {
		try {
			return URLEncoder.encode(string, "UTF-8");
		} catch(UnsupportedEncodingException e) {
			return string; //TODO: log it?
		}
	}

	
	/**
	 * Initializes the string builder so that it contains 
	 * all the necessary info that comes apart from the query
	 * itself. Only the query-string has to be added now. 
	 */
	private void initializeStringBuilder() {
		builder = new StringBuilder(this.NAMEFINDER_URL);
		builder.append(this.XML_FORMAT);
		builder.append(this.AMPERSAND);
		builder.append(this.ADDRESS_DETAILS);
		builder.append(this.AMPERSAND);
		builder.append(this.QUERY);
		//DO NOT ENCODE
	}
	
}
