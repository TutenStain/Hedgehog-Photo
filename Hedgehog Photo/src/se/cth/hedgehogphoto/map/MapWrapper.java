package se.cth.hedgehogphoto.map;

import java.awt.Point;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class MapWrapper extends JLayeredPane {
	
	private JPanel map = Main.getMapPanel();

	public static void main(String[] args) {
		
	}
	
	public MapWrapper(List<Point> locationPoints) {
		add(map, new Integer(0), 0);
		addLocationMarkers(locationPoints);
	}
	
	private void addLocationMarkers(List<Point> locationPoints) {
		int nbrOfLocations = locationPoints.size();
        JLabel [] markers = new JLabel[nbrOfLocations];
        for(int i = 0; i < nbrOfLocations; i++) {
        	markers[i] = new LocationMarker(locationPoints.get(i));
        	add(markers[i], new Integer(2), 0);
        }
	}

}
