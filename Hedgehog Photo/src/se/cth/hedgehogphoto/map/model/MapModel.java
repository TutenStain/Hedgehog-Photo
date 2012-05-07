package se.cth.hedgehogphoto.map.model;

import java.awt.Dimension;
import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import se.cth.hedgehogphoto.database.Files;
import se.cth.hedgehogphoto.database.Location;
import se.cth.hedgehogphoto.database.Picture;

public class MapModel extends Observable implements Observer, PropertyChangeListener {
	private List<AbstractMarkerModel> markerModels;
	private MapPanel map;
//	TODO: Make it listen to leftPanel/window-resize
	private int width; 
	private int height;

	public MapModel() {
		Files.getInstance().addObserver(this);
		initialize();
	}
	
	/**
	 * Fetches pictures, and assigns a marker to each one of it.
	 * Creates the map, calibrates it to show all markers and
	 * then sets the relative pixel position of each marker.
	 * Overlapping markers combine to "multiple"-markers,
	 * ie no overlapping markers will ever be shown.
	 */
	protected void initialize() {
		this.markerModels = new LinkedList<AbstractMarkerModel>();
		List<Picture> pictures = Files.getInstance().getPictureList(); //fetch pictures
		List<Location> locations = new LinkedList<Location>();
		int nbrOfPictures = pictures.size();
		int index;
		for (index = 0; index < nbrOfPictures; index++) {
			Picture picture = pictures.get(index);
			Location location = picture.getLocation();
		
			if (location != null) { 
				this.markerModels.add(new MarkerModel(picture));
				locations.add(picture.getLocation());
			}
		}
		
		calibrateMap(locations); //make every location fit on the screen
		int nbrOfMarkers = this.markerModels.size();
		for (index = 0; index < nbrOfMarkers; index++) {
			Location location = locations.get(index);
			Point pixelPosition = getMapPanel().getPixelPosition(location);
			this.markerModels.get(index).setPointerPosition(pixelPosition);
		}

		do {
			clearChanged();
			organizeMarkers();
		} while (hasChanged());
		setChanged();
		notifyObservers(Global.MARKERS_UPDATE);
	}
	
	/* TODO: Add comment on complex flow. */
	/**
	 * Goes through all the markers and merges intersecting ones
	 * into MultipleMarkerModels. 
	 */
	public void organizeMarkers() {
		for (int index = 0; index < markerModels.size() - 1; index++) {
			AbstractMarkerModel marker = markerModels.get(index);
			int nbrOfMarkers = markerModels.size();
			for (int indexToCheckAgainst = index + 1; indexToCheckAgainst < nbrOfMarkers; indexToCheckAgainst++) {
				AbstractMarkerModel markerTwo = markerModels.get(indexToCheckAgainst);
				if (marker.intersects(markerTwo)) {
					markerModels.add(new MultipleMarkerModel(marker, markerTwo));
					markerModels.remove(markerTwo);
					markerModels.remove(marker);
					index--;
					setChanged();
					break;
				}
			}
		}
		System.out.println("------------");
	}
	
	public void calibrateMap(List<Location> locations) {
		getMapPanel().calibrate(locations);
		getMapPanel().addPropertyChangeListener(this);
	}
	
	/** Returns the map, OR if it doesn't exist yet, creates it. */
	public MapPanel getMapPanel() {
		if (map == null) {
			map = new MapPanel();
		}
		return map;
	}
	
	public List<AbstractMarkerModel> getMarkerModels() {
		return markerModels;
	}
	
	public Dimension getSize() {
		return new Dimension(getWidth(), getHeight());
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		getMapPanel().removePropertyChangeListener(this); //remove listener during calibration
		initialize();
		setChanged();
		notifyObservers(Global.FILES_UPDATE);
	}

	/**
	 * IMPORTANT: We want the markers to update themself,
	 * and after that we want to check if there are any overlapping
	 * markers, and combine them. So that's why this class listens
	 * to the mapPanel and sends the event on to the markers. 
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		for (AbstractMarkerModel model: markerModels) {
			model.propertyChange(event);
		}
		
		String property = event.getPropertyName();
		if (property.startsWith(Global.ZOOM)) { 
			do {
				this.clearChanged();
				organizeMarkers();
			} while (hasChanged());
			setChanged();
			notifyObservers(Global.MARKERS_UPDATE); //setChanged called by organizeMarkers()
			print(markerModels);
		}
	}
	
	public void print(List<AbstractMarkerModel> models) {
		for (AbstractMarkerModel model: models) {
			model.print();
			if (model instanceof MultipleMarkerModel) {
				List<AbstractMarkerModel> list = ((MultipleMarkerModel)model).getMarkerModels();
				print(list);
			}
		}
		System.out.println("\n *************************** \n");
	}

}
