package se.cth.hedgehogphoto.map.controller;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import se.cth.hedgehogphoto.map.model.Global;
import se.cth.hedgehogphoto.map.view.AbstractJOverlayPanel;
import se.cth.hedgehogphoto.map.view.MapView;

/**
 * MapController - handles mouseevents on the markers and
 * calls the appropriate methods.
 * @author Florian Minges
 */
public class MapController implements PropertyChangeListener {
	private MapView mapView;
	
	public MapController(MapView mapView) {
		this.mapView = mapView;
		mapView.addListener(new MarkerListener());
		mapView.addPropertyChangeListener(this);
	}
	

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getPropertyName().equals(Global.MARKERS_UPDATE)) {
			mapView.addListener(new MarkerListener());
		}
		
	}
	
	/** 
	 * MouseListener-class for the markers.
	 * @author Florian
	 */
	public class MarkerListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent arg0) {}
		@Override
		public void mouseClicked(MouseEvent arg0) {
			//TODO: Make a search in the database
			
		}
		@Override
		public void mousePressed(MouseEvent arg0) {
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			arg0.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			if (arg0.getSource() instanceof AbstractJOverlayPanel) {
				AbstractJOverlayPanel marker = (AbstractJOverlayPanel) arg0.getSource();
			}
			//TODO: don't check instanceof here
			//call mapView.showInfoPanel(MouseEvent arg0)
			//Create class called LocationInfoPanel (or something like that)
			//	let it extend AbstractJOverlayLabel (which has to change name ending to Component?)
			//Create new class AbstractMarker, which extends AbstractJOverlayComponent and implements a method getPictures()
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			arg0.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
    }
}
