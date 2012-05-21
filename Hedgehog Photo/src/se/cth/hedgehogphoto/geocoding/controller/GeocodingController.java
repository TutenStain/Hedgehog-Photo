package se.cth.hedgehogphoto.geocoding.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import se.cth.hedgehogphoto.database.DaoFactory;
import se.cth.hedgehogphoto.database.JpaPictureDao;
import se.cth.hedgehogphoto.database.Picture;
import se.cth.hedgehogphoto.geocoding.view.GeoLocationPanel;
import se.cth.hedgehogphoto.geocoding.view.GeoSearchPanel;
import se.cth.hedgehogphoto.objects.LocationObjectOther;

/**
 * Controller-class for the geocoding-system.
 * @author Florian Minges
 */
public class GeocodingController {
	private static GeocodingController instance;
	private GeoSearchPanel searchPanel;
	private String imagePath;
	
	public static GeocodingController getInstance(GeoSearchPanel searchPanel) {
		if (instance == null)
			instance = new GeocodingController(searchPanel);
		return instance;
	}
	
	public GeocodingController(GeoSearchPanel searchPanel) {
		this.searchPanel = searchPanel;
		this.searchPanel.addResultPanelMouseListener(new ResultPanelListener());
		this.searchPanel.addOkButtonListener(new OkButtonListener());
		this.searchPanel.addCancelButtonListener(new CancelButtonListener());
	}
	
	public void setImagePath(String path) {
		this.imagePath = path;
	}
	
	public class ResultPanelListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent arg0) {}
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			if (arg0.getSource() instanceof GeoLocationPanel) {
				GeoLocationPanel resultPanel = (GeoLocationPanel) arg0.getSource();
				resultPanel.toggleSelection();
				resultPanel.mouseEntered();
				
				searchPanel.enableOkButton(GeoLocationPanel.selectedPanel != null);
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
	
	public class OkButtonListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (GeoLocationPanel.selectedPanel != null && imagePath != null) {
				
				JpaPictureDao pictureDao = DaoFactory.getInstance().getJpaPictureDao();
				Picture picture = pictureDao.findById(imagePath);
				LocationObjectOther location = GeoLocationPanel.selectedPanel.getLocationObjectOther();
				
				pictureDao.setLocation(location, picture);
				searchPanel.setVisible(false);
			}
		}
	}
	
	public class CancelButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			searchPanel.setVisible(false);
		}
	}
}
