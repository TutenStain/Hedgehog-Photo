package se.cth.hedgehogphoto.map;

import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.util.List;

import javax.swing.ImageIcon;

public class MultipleLocationMarker extends LocationMarker {
	private List<LocationMarker> markers;
	private ImageIcon icon = new ImageIcon("marker.png");
	
	public MultipleLocationMarker(List<LocationMarker> markers, Point p) {
		this(p);
		this.markers = markers;
		
	}
	
	public MultipleLocationMarker(Point p) {
		super(p);
		setIcon(icon);
	}
	
	public static Point computePosition(List<LocationMarker> markers) {
		int xSum = 0;
		int ySum = 0;
		for (LocationMarker marker: markers) {
			xSum += marker.getXCoordinate();
			ySum += marker.getYCoordinate();
		}
		int nbrOfMarkers = markers.size();
		return new Point(xSum / nbrOfMarkers, ySum / nbrOfMarkers);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		String property = event.getPropertyName();
		if (property.startsWith("zoom")){
			/* TODO: Add handling for what happens when it zooms. */
		}
		
	}
}
