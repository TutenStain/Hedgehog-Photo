package se.cth.hedgehogphoto.map;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MarkerController {
	private MapView mapView;
	
	public MarkerController(MapView mapView) {
		this.mapView = mapView;
		mapView.addMarkerListener(new MarkerListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//TODO make a search in the database
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				
			}
		});
		
	}

}
