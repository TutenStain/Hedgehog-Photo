package se.cth.hedgehogphoto.map.controller;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import se.cth.hedgehogphoto.database.Files;
import se.cth.hedgehogphoto.database.PictureObject;
import se.cth.hedgehogphoto.map.model.Global;
import se.cth.hedgehogphoto.map.view.AbstractJOverlayMarker;
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
			if (arg0.getSource() instanceof AbstractJOverlayMarker) {
				AbstractJOverlayMarker marker = (AbstractJOverlayMarker) arg0.getSource();
				List<PictureObject> pictures = new ArrayList<PictureObject>();
				marker.getModel().getPictures(pictures);
				Files.getInstance().setPictureList(pictures);
			}
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
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			arg0.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
    }
}
