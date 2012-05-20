package se.cth.hedgehogphoto.map.model;

import java.awt.Dimension;
import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

import se.cth.hedgehogphoto.database.Files;
import se.cth.hedgehogphoto.database.LocationObject;
import se.cth.hedgehogphoto.database.PictureObject;
import se.cth.hedgehogphoto.log.Log;

/**
 * The model-warpper-class in the map-subsystem.
 * @author Florian Minges
 */
public class MapModel extends Observable implements Observer, PropertyChangeListener {
	private List<AbstractMarkerModel> markerModels;
	private MapPanel map;
	private Files files;
//	TODO: Make it listen to leftPanel/window-resize
	private int width; 
	private int height;

	public MapModel(Files files) {
		this.files = files;
		this.files.addObserver(this);
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
		List<PictureObject> pictures = this.files.getPictureList(); //fetch pictures
		List<LocationObject> locations = new LinkedList<LocationObject>();
		int nbrOfPictures = pictures.size();
		Log.getLogger().log(Level.INFO, nbrOfPictures + " pictures were found in the Files-class.");
		int index;
		for (index = 0; index < nbrOfPictures; index++) {
			PictureObject picture = pictures.get(index);
			LocationObject location = picture.getLocation();
			if (location != null) {
				if (location.validPosition()) { 
					this.markerModels.add(new MarkerModel(picture));
					locations.add(picture.getLocation());
					Log.getLogger().info(location.getLocation() + "\n" + location.getLongitude() + ", " + location.getLatitude() + "\n");
					Log.getLogger().log(Level.INFO, "en location!");
				}
			}
		}
		
		calibrateMap(locations); //make every location fit on the screen
		int nbrOfMarkers = this.markerModels.size();
		for (index = 0; index < nbrOfMarkers; index++) {
			LocationObject location = locations.get(index);
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
		for (int index = 0; index < this.markerModels.size() - 1; index++) { //TODO: Use this.getMarkerModels() to access models instead?
			AbstractMarkerModel marker = this.markerModels.get(index);
			int nbrOfMarkers = this.markerModels.size();
			for (int indexToCheckAgainst = index + 1; indexToCheckAgainst < nbrOfMarkers; indexToCheckAgainst++) {
				AbstractMarkerModel markerTwo = this.markerModels.get(indexToCheckAgainst);
				if (marker.intersects(markerTwo)) {
					this.markerModels.add(new MultipleMarkerModel(marker, markerTwo));
					this.markerModels.remove(markerTwo);
					this.markerModels.remove(marker);
					index--;
					setChanged();
					break;
				}
			}
		}
	}
	
	public void calibrateMap(List<LocationObject> locations) {
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
		return this.markerModels;
	}
	
	public Dimension getSize() {
		return new Dimension(getWidth(), getHeight());
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
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
		for (AbstractMarkerModel model: this.markerModels) {
			model.propertyChange(event);
		}
		
		String property = event.getPropertyName();
		if (property.startsWith(Global.ZOOM)) { 
			do {
				this.clearChanged();
				organizeMarkers();
			} while (hasChanged());
			setChanged();
			notifyObservers(Global.MARKERS_UPDATE); 
		}
	}
}
