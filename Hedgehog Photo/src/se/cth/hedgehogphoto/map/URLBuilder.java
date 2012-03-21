package se.cth.hedgehogphoto.map;

import java.awt.Dimension;
import java.util.List;

import se.cth.hedgehogphoto.Location;

public class URLBuilder {
	private final String baseURL = "http://maps.googleapis.com/maps/api/staticmap?";
	private final String zoomURL = "zoom=";
	private final String mapsizeURL = "size=";
	private final String centerURL = "center=";
	private final String markersURL = "markers=";
	private final String visibleLocationsURL = "visible=";
	private final String maptypeURL = "maptype=";
	private final String formatURL = "format=";
	private final String languageURL = "languages=";
	private final String sensorURL = "sensor=false";
	
	private final int defaultZoom = 7;
	private final Location defaultCenter = new Location("Sweden");
	private StringBuilder urlBuilder = new StringBuilder(baseURL);
	
	public URLBuilder() {
		//init
	}
	
	public String getPath() {
		validateURL();
		return urlBuilder.toString();
	}
	
	public void validateURL() {
		//TODO check for zoom & center OR markers
		if( !this.containsString(sensorURL) ) {
			urlBuilder.append('&');
			urlBuilder.append(sensorURL);
		} 
		if( !this.containsString(markersURL) ) {
			setZoomLevel(defaultZoom);
			setCenter(defaultCenter);
		}
	}
	
	public void setMapSize(Dimension mapSize) {
		int width = mapSize.width;
		int height = mapSize.height;
		String size = (width + "x" + height);
		addToURL(mapsizeURL, size);
	}
	
	public void setZoomLevel(int zoomLevel) {
		String zoom = String.valueOf(zoomLevel);
		addToURL(zoomURL, zoom);
	}
	
	public void setMaptype(String maptype) {
		addToURL(maptypeURL, maptype);
	}
	
	public void setCenter(Location location) {
		String center = location.toString();
		addToURL(centerURL, center);
	}
	
	public void setMarkers(List<Location> locations) {
		//TODO refactor? let setVisibleLocations() use same code,
		//they are the same apart from the last line
		StringBuilder markersBuilder = new StringBuilder();
		for(Location location : locations) {
			markersBuilder.append(location.toString());
			markersBuilder.append("|");
		}
		int lastIndex = markersBuilder.length() - 1;
		markersBuilder.deleteCharAt(lastIndex); //one '|' too much
		String markers = markersBuilder.toString();
		addToURL(markersURL, markers);
	}
	
	public void setVisibleLocations(List<Location> locations) {
		// TODO refactor? let setMarkers() use same code,
		// they are the same apart from the last line
		StringBuilder markersBuilder = new StringBuilder();
		for (Location location : locations) {
			markersBuilder.append(location.toString());
			markersBuilder.append("|");
		}
		int lastIndex = markersBuilder.capacity() - 1;
		markersBuilder.deleteCharAt(lastIndex); // one '|' too much
		String visibleLocations = markersBuilder.toString();
		addToURL(visibleLocationsURL, visibleLocations);
	}
	
	public void setFormat(String format) {
		addToURL(formatURL, format);
	}
	
	public void setLanguage(String language) {
		addToURL(languageURL, language);
	}
	
	private void addToURL(String typeURL, String value) {
		if( !containsString(typeURL) ) {
			append(typeURL, value);
		} else if( !containsString(typeURL + value) ) {
			replace(typeURL, value);
		} 
	}
	
	private void append(String typeURL, String value) {
		urlBuilder.append("&");
		urlBuilder.append(typeURL);
		urlBuilder.append(value);
	}
	
	private void replace(String typeURL, String value) {
		int typeStartIndex = urlBuilder.indexOf(typeURL);
		int valueStartIndex = urlBuilder.indexOf(value, typeStartIndex); 
		int endIndex = urlBuilder.indexOf("&", valueStartIndex); //TODO add handling of | (eg markers)
		urlBuilder.replace(valueStartIndex, endIndex, value);
	}
	
	private boolean containsString(String string) {
		String url = urlBuilder.toString();
		return url.contains(string);
	}

}
