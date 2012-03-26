package se.cth.hedgehogphoto.map;

import java.awt.Dimension;
import java.util.List;

import se.cth.hedgehogphoto.Location;

//TODO Split up in two classes; use abstract class? or some other kind of inheritance
public abstract class URLBuilder {
	protected final StringBuilder urlBuilder;
	private final String baseURL;
	protected final String languageURL = "languages=";
	protected final String sensorURL = "sensor=false";
	
	public URLBuilder(String baseURL) {
		this.baseURL = baseURL;
		this.urlBuilder = new StringBuilder(baseURL);
	}
	
	public final String getPath() {
		validateURL();
		return urlBuilder.toString();
	}
	
	public abstract void validateURL(); 
	
	public final void setLanguage(String language) {
		addToURL(languageURL, language);
	}
	
	protected final void addToURL(String typeURL, String value) {
		if( !containsString(typeURL) ) {
			append(typeURL, value);
		} else if( !containsString(typeURL + value) ) {
			replace(typeURL, value);
		} 
	}
	
	private final void append(String typeURL, String value) {
		urlBuilder.append("&");
		urlBuilder.append(typeURL);
		urlBuilder.append(value);
	}
	
	private void replace(String typeURL, String value) {
		//TODO add handling of | (eg markers), if not, make method final
		int typeStartIndex = urlBuilder.indexOf(typeURL);
		int valueStartIndex = urlBuilder.indexOf(value, typeStartIndex); 
		int endIndex = urlBuilder.indexOf("&", valueStartIndex); 
		urlBuilder.replace(valueStartIndex, endIndex, value);
	}
	
	protected final boolean containsString(String string) {
		String url = urlBuilder.toString();
		return url.contains(string);
	}

}
