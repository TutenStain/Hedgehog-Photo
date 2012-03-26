package se.cth.hedgehogphoto.map;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import se.cth.hedgehogphoto.Location;

public class MapModel {
	//TODO move constants into proper class; URLBuilder?
	private final int maxZoom = 18;
	private final int minZoom = 0; 
	private final List<String> acceptedMaptypes = 
			Arrays.asList("roadmap", "satellite", "terrain", "hybrid");
	private final List<String> acceptedFormats =
			Arrays.asList("png", "png32", "gif", "jpg", "jpg-baseline");
	private final List<Integer> acceptedScales =
			Arrays.asList(1,2);
	
	private String maptype = "roadmap";
	private String format = "png32";
	private String language;
	
	private Location centerLocation;
	private List<Location> locations = new ArrayList<Location>(); //with markers
	private List<Location> visibleLocations = new ArrayList<Location>(); //without markers
	
	private Dimension mapsize = new Dimension(512,512);
	private int zoomLevel = 6; //don't neccesarily need this
	private int scale = 1;
	
	public MapModel() {
		locations.add(new Location("London"));
		locations.add(new Location("Los Angeles"));
		locations.add(new Location("Stockholm"));
		locations.add(new Location("Paris"));
		locations.add(new Location("Munich"));
		System.out.println(createURL());
	}
	
	public static void main(String [] args) {
		new MapModel();
	}
	
	private String createURL() {
		XMLPath url = new XMLPath();
//		url.setMaptype(maptype);
//		url.setMapSize(mapsize);
//		url.setMarkers(locations);
		url.setAddress("Berlin");
		
		return url.getPath();
	}
	
	public void setZoomLevel(int zoomLevel) {
		if(zoomLevel >= minZoom && zoomLevel <= maxZoom)
			this.zoomLevel = zoomLevel;
	}
	
	public void incrementZoomLevel() {
		if(zoomLevel < maxZoom) {
			zoomLevel++;
		}
	}
	
	public void decrementZoomLevel() {
		if(zoomLevel > minZoom) {
			zoomLevel--;
		}
	}
	
	public void setSize(Dimension size) {
		this.mapsize = size;
	}
	
	public List<String> getMarkedLocations() {
		return new ArrayList<String>();
	}
}
