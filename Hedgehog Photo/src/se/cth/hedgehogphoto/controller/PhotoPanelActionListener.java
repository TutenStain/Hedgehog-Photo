package se.cth.hedgehogphoto.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import se.cth.hedgehogphoto.database.DatabaseHandler;
import se.cth.hedgehogphoto.geocoding.GeocodingInitiator;
import se.cth.hedgehogphoto.model.PhotoPanelConstantsI;
import se.cth.hedgehogphoto.view.PhotoPanel;

public class PhotoPanelActionListener implements ActionListener {

	public PhotoPanelActionListener(){
	}

	@Override
	public void actionPerformed(ActionEvent a) {
		if (a.getSource() instanceof JTextField) {
			JTextField cell = (JTextField) a.getSource();

			if(cell.getName().equals(PhotoPanelConstantsI.COMMENT)){
				if (cell.getParent() instanceof PhotoPanel) {
					String path = ((PhotoPanel)cell.getParent()).getPath();
					DatabaseHandler.getInstance().addCommenttoPicture( cell.getText(), path);
				}
			} else if(cell.getName().equals(PhotoPanelConstantsI.LOCATION)){
				if (cell.getParent() instanceof PhotoPanel) {
					PhotoPanel panel = (PhotoPanel) cell.getParent();	
					new GeocodingInitiator(cell.getText(), panel);
				}
			} else if(cell.getName().equals(PhotoPanelConstantsI.TAGS)){
				if (cell.getParent() instanceof PhotoPanel) {
					String path = ((PhotoPanel)cell.getParent()).getPath();
					DatabaseHandler.getInstance().deleteTagsfromPicture(path);
					String[] tags = cell.getText().split(";");
					for(int i = 0; i < tags.length; i++){
						DatabaseHandler.getInstance().addTagtoPicture(tags[i], path);
					}
				}
			} else if(cell.getName().equals(PhotoPanelConstantsI.NAME)){
				if (cell.getParent() instanceof PhotoPanel) {
					String path = ((PhotoPanel)cell.getParent()).getPath();
					DatabaseHandler.getInstance().setPictureName(cell.getText(), path);;
				}
			}
		}
	}
}

