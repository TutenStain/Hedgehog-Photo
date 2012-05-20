package se.cth.hedgehogphoto.map.geocoding.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import se.cth.hedgehogphoto.map.geocoding.view.GeoLocationPanel;
import se.cth.hedgehogphoto.map.geocoding.view.GeoSearchPanel;

/**
 * Controller-class for the geocoding-system.
 * @author Florian Minges
 */
public class GeocodingController {
	private GeoSearchPanel searchPanel;
	
	public GeocodingController(GeoSearchPanel searchPanel) {
		this.searchPanel = searchPanel;
		this.searchPanel.addResultPanelMouseListener(new ResultPanelListener());
	}
	
	public static class ResultPanelListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent arg0) {}
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			if (arg0.getSource() instanceof GeoLocationPanel) {
				GeoLocationPanel resultPanel = (GeoLocationPanel) arg0.getSource();
				resultPanel.toggleSelection();
				resultPanel.mouseEntered();
			}
		}
		@Override
		public void mousePressed(MouseEvent arg0) {}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			if (arg0.getSource() instanceof GeoLocationPanel) {
				GeoLocationPanel resultPanel = (GeoLocationPanel) arg0.getSource();
				resultPanel.mouseEntered();
			}
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			//set default-color again, works for selectedPanels also
			if (arg0.getSource() instanceof GeoLocationPanel) {
				GeoLocationPanel resultPanel = (GeoLocationPanel) arg0.getSource();
				resultPanel.mouseExited();
			}
		}
		
	}
}
