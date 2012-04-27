package se.cth.hedgehogphoto.map;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
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
public class MapView extends JPanel implements Observer {
	private JLayeredPane mainPane;
	private MapPanel map;
	private List<AbstractJOverlayLabel> locationMarkers;
	private final int WIDTH = se.cth.hedgehogphoto.Constants.PREFERRED_MODULE_WIDTH;
	private final int HEIGHT = se.cth.hedgehogphoto.Constants.PREFERRED_MODULE_HEIGHT;
	
	/** Default constructor. 
	 * @param locations the locations to be shown on the map. */
	public MapView() {
		setLayout(new BorderLayout());
		init();
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
		this.locationMarkers = new ArrayList<AbstractJOverlayLabel>();
		int nbrOfPictures = pictures.size();
		int index;
		for (index = 0; index < nbrOfPictures; index++) {
			Picture picture = pictures.get(index);
			if (picture.getLocation() != null) { //TODO: Do a better check than != null
				this.locationMarkers.add(new JOverlayMarker(picture));
				locations.add(picture.getLocation());
			}
		}
		
		calibrateMap(locations);
		int nbrOfMarkers = this.locationMarkers.size();
		for (index = 0; index < nbrOfMarkers; index++) {
			Location location = locations.get(index);
			Point pixelPosition = getMapPanel().getPixelPosition(location);
			this.locationMarkers.get(index).setPixelPosition(pixelPosition);
			
			AbstractJOverlayLabel marker = locationMarkers.get(index);
        	int layer = new Integer(marker.getYPosition());
        	map.addObserver(marker);
        	mainPane.add(marker, layer, 1);
		}
	}
	
	public void calibrateMap(List<Location> locations) {
		getMapPanel().calibrate(locations);
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
		for (AbstractJOverlayLabel marker : locationMarkers) {
			marker.addMouseListener(mouseAdapter);
		}
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO What happens when the shown Files are updated?
		
	}
}
