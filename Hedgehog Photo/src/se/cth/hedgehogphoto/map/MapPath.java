package se.cth.hedgehogphoto.map;

import java.awt.Dimension;
import java.util.List;

import se.cth.hedgehogphoto.Location;

public class MapPath extends URLBuilder {
	private final String zoomURL = "zoom=";
	private final String mapsizeURL = "size=";
	private final String centerURL = "center=";
	private final String markersURL = "markers=";
	private final String visibleLocationsURL = "visible=";
	private final String maptypeURL = "maptype=";
	private final String formatURL = "format=";
	
	private final int defaultZoom = 7;
	private final Location defaultCenter = new Location("Sweden");
	
	private final PixelFinder pixelFinder = new PixelFinder();
	
	public MapPath() {
		super("http://maps.googleapis.com/maps/api/staticmap?");
	}
	
	@Override
	public void validateURL() {
		if( !this.containsString(sensorURL) ) {
			urlBuilder.append('&');
			urlBuilder.append(sensorURL);
		} 
		if( !this.containsString(markersURL) && !this.containsString(visibleLocationsURL) ) {
			//TODO should check that zoom and center are not yet initialized
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
	
	private void setPixelCenter(List<Location> locations) {
		String center = pixelFinder.getMapCenterInLongLat(locations);
		addToURL(centerURL, center);
	}
	
	public void setMarkers(List<Location> locations) {
		String markers = getLocationsURL(locations);
		addToURL(markersURL, markers);
		setPixelCenter(locations);
	}
	
	public void setVisibleLocations(List<Location> locations) {
		String visibleLocations = getLocationsURL(locations);
		addToURL(visibleLocationsURL, visibleLocations);
		setPixelCenter(locations);
	}
	
	private String getLocationsURL(List<Location> locations) {
		StringBuilder locationsBuilder = new StringBuilder();
		for (Location location : locations) {
			locationsBuilder.append(location.toString());
			locationsBuilder.append("|");
		}
		int lastIndex = locationsBuilder.length() - 1;
		locationsBuilder.deleteCharAt(lastIndex); // one '|' too much
		String locationsURL = locationsBuilder.toString();
		return locationsURL;
	}
	
	public void setFormat(String format) {
		addToURL(formatURL, format);
	}

}
