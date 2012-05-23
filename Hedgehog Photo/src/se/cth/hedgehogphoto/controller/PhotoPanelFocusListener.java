package se.cth.hedgehogphoto.controller;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextArea;
import javax.swing.JTextField;


import se.cth.hedgehogphoto.database.DatabaseHandler;
import se.cth.hedgehogphoto.geocoding.GeocodingInitiator;
import se.cth.hedgehogphoto.model.PhotoPanelConstantsI;
import se.cth.hedgehogphoto.view.PhotoPanel;

public class PhotoPanelFocusListener implements FocusListener {

	private String oldString;

	public PhotoPanelFocusListener(){

	}

	@Override
	public void focusGained(FocusEvent e) {	
		if (e.getSource() instanceof JTextField) {
			JTextField cell = (JTextField) e.getSource();
			this.oldString = cell.getText();
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		if (e.getSource() instanceof JTextField) {
			JTextField cell = (JTextField) e.getSource();

			if (this.oldString.equals(cell.getText())) {
			} else if(cell.getName().equals(PhotoPanelConstantsI.LOCATION)){
				if (cell.getParent() instanceof PhotoPanel) {
					PhotoPanel panel = (PhotoPanel) cell.getParent();	
					new GeocodingInitiator(cell.getText(), panel);
				}
			} else if(cell.getName().equals(PhotoPanelConstantsI.NAME)){
				if (cell.getParent() instanceof PhotoPanel) {
					String path = ((PhotoPanel)cell.getParent()).getPath();	
					DatabaseHandler.getInstance().setPictureName(cell.getText(), path);
				}
			} else if(cell.getName().equals(PhotoPanelConstantsI.TAGS)){
				if (cell.getParent() instanceof PhotoPanel) {
					String path = ((PhotoPanel)cell.getParent()).getPath();	
					DatabaseHandler.getInstance().deleteTagsfromPicture(path);
					JTextField jtf = (JTextField)e.getSource();
					String[] tags = jtf.getText().split(";");
					for(int i = 0; i < tags.length; i++){
						DatabaseHandler.getInstance().addTagtoPicture(tags[i], path);
					}
				}
			}
		}
		if (e.getSource() instanceof JTextArea) {
			JTextArea cell = (JTextArea) e.getSource();

			if(cell.getName().equals(PhotoPanelConstantsI.COMMENT)){
				if (cell.getParent() instanceof PhotoPanel) {
					String path = ((PhotoPanel)cell.getParent()).getPath();			
					DatabaseHandler.getInstance().addCommenttoPicture(cell.getText(), path);
				}
			}
		}
	}
}
