package se.cth.hedgehogphoto.map;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import se.cth.hedgehogphoto.database.Files;
import se.cth.hedgehogphoto.database.Location;
import se.cth.hedgehogphoto.database.Picture;

/**
 * Combines the map and its' markers to one pane.
 * THIS is the class to instantiate if one wants a map.
 * @author Florian
 */
public class MapView extends JPanel implements Observer, PropertyChangeListener {
	private JLayeredPane mainPane;
	private MapPanel map;
	private List<AbstractJOverlayMarker> locationMarkers;
	private final int WIDTH = se.cth.hedgehogphoto.Constants.PREFERRED_MODULE_WIDTH;
	private final int HEIGHT = se.cth.hedgehogphoto.Constants.PREFERRED_MODULE_HEIGHT;
	
	/** Default constructor. 
	 * @param locations the locations to be shown on the map. */
	public MapView() {
		setLayout(new BorderLayout());
		init();
		Files.getInstance().addObserver(this);
		map.addObserver(this); //listens to map via PropertyChangeListener
	}
	
	public MapView(List<Location> locations) {
		List<Picture> pictures = new ArrayList<Picture>();
		int nbrOfLocations = locations.size();
		for (int index = 0; index < nbrOfLocations; index++) {
			Picture pic = new Picture();
			pic.setLocation(locations.get(index));
			pictures.add(pic);
		}
		cheat(pictures);
		init();
	}
	
	/** Temporary method to use when there are no pictures in the database. */
	private void cheat(List<Picture> pictures) {
		Files.getInstance().setPictureList(pictures);
	}
	
	/** The initialization of this class. Called from constructor. */
	private void init() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		addRootJLayeredPane();
		addMap();
		addLocationMarkers();
	}
	
	public void addLocationMarkers() {
		List<Picture> pictures = Files.getInstance().getPictureList();
		List<Location> locations = new ArrayList<Location>();
		this.locationMarkers = new ArrayList<AbstractJOverlayMarker>();
		int nbrOfPictures = pictures.size();
		int index;
		for (index = 0; index < nbrOfPictures; index++) {
			Picture picture = pictures.get(index);
			Location location = picture.getLocation();
			
			//TODO: location == null will never happen, 
			//but if it does, it will cause a NullPointerException
			if (location != null && location.getLongitude() != 0.0 || 
					location.getLatitude() != 0.0) { 
				this.locationMarkers.add(new JMarker(picture));
				locations.add(picture.getLocation());
			}
		}
		
		calibrateMap(locations);
		int nbrOfMarkers = this.locationMarkers.size();
		for (index = 0; index < nbrOfMarkers; index++) {
			Location location = locations.get(index);
			Point pixelPosition = getMapPanel().getPixelPosition(location);
			this.locationMarkers.get(index).setPixelPosition(pixelPosition);
		}
		organizeMarkers();
	}
	
	public void calibrateMap(List<Location> locations) {
		getMapPanel().calibrate(locations);
	}
	
	/* TODO: Add comment on complex flow. */
	public void organizeMarkers() {
		int index;
		for (index = 0; index < locationMarkers.size() - 1; index++) {
			int nbrOfMarkers = locationMarkers.size();
			AbstractJOverlayMarker marker = locationMarkers.get(index);
			for (int indexToCheckAgainst = index + 1; indexToCheckAgainst < nbrOfMarkers; indexToCheckAgainst++) {
				AbstractJOverlayMarker markerTwo = locationMarkers.get(indexToCheckAgainst);
				if (marker.intersects(markerTwo)) {
					locationMarkers.add(new JMultipleMarker(marker, markerTwo));
					int layer = new Integer(marker.getYPosition());
					mainPane.add(marker, layer, 1);
					layer = new Integer(markerTwo.getYPosition());
					mainPane.add(markerTwo, layer, 1);
					locationMarkers.remove(indexToCheckAgainst);
					locationMarkers.remove(index);
					index--;
					break;
				}
			}
		}
		
		addMarkerListeners();
	}
	
	private void addMarkerListeners() {
		for (int index = 0; index < locationMarkers.size(); index++) {
			AbstractJOverlayPanel marker = locationMarkers.get(index);
        	int layer = new Integer(marker.getYPosition());
        	map.addObserver(marker);
        	mainPane.add(marker, layer, 1);
		}
	}
	
	private void addRootJLayeredPane() {
		mainPane = new JLayeredPane();
		mainPane.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		add(mainPane, BorderLayout.CENTER);
	}
	
	/** Returns the map, OR if it doesn't exist yet, creates it. */
	public MapPanel getMapPanel() {
		if (map == null) {
			map = new MapPanel();
		}
		return map;
	}
	
	/** Adds the map to the pane. */
	private void addMap() {
		if (mainPane == null) {
			addRootJLayeredPane(); //unneccessary check? goto: line 38
		}
		mainPane.add(getMapPanel(), new Integer(0), 0);
	}
	
	public void addListener(MouseAdapter mouseAdapter) {
		for (AbstractJOverlayPanel marker : locationMarkers) {
			marker.addMouseListener(mouseAdapter);
		}
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO What happens when the shown Files are updated?
		mainPane.removeAll();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		addMap();
		addLocationMarkers();
		
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		// TODO handle zoom-event
		if (arg0.getPropertyName().startsWith("zoom")) {
			organizeMarkers();
		}
	}
}
