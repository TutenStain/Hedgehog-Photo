package se.cth.hedgehogphoto.controller;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import se.cth.hedgehogphoto.database.DaoFactory;
import se.cth.hedgehogphoto.database.JpaPictureDao;
import se.cth.hedgehogphoto.geocoding.GeocodingInitiator;
import se.cth.hedgehogphoto.model.PhotoPanelConstantsI;
import se.cth.hedgehogphoto.view.PhotoPanel;

public class PhotoPanelFocusListener implements FocusListener {
	private static DaoFactory daoFactory = DaoFactory.getInstance();
	private JpaPictureDao pictureDao = daoFactory.getJpaPictureDao();
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
				//string did not change, do not update
			}/* else if(cell.getName().equals(PhotoPanelConstantsI.COMMENT)){
				if (cell.getParent() instanceof PhotoPanel) {
					String path = ((PhotoPanel)cell.getParent()).getPath();			
					pictureDao.addComment(cell.getText(), path);
					System.out.println("JTF" +cell.getText());
					System.out.println(pictureDao.findById(path));
				}

			}*/ else if(cell.getName().equals(PhotoPanelConstantsI.LOCATION)){
				if (cell.getParent() instanceof PhotoPanel) {
					PhotoPanel panel = (PhotoPanel) cell.getParent();	
					new GeocodingInitiator(cell.getText(), panel);
				}
			} else if(cell.getName().equals(PhotoPanelConstantsI.NAME)){
				if (cell.getParent() instanceof PhotoPanel) {
					String path = ((PhotoPanel)cell.getParent()).getPath();	
					pictureDao.setName(cell.getText(), path);
					System.out.println("JTF" +cell.getText());
					System.out.println(pictureDao.findById(path));
				}
			} else if(cell.getName().equals(PhotoPanelConstantsI.TAGS)){
				if (cell.getParent() instanceof PhotoPanel) {
					String path = ((PhotoPanel)cell.getParent()).getPath();	
					pictureDao.deleteTags(path);
					JTextField jtf = (JTextField)e.getSource();
					String[] tags = jtf.getText().split(";");
					for(int i = 0; i < tags.length; i++){
						pictureDao.addTag(tags[i], path);
						System.out.println("JTF" +jtf.getText());
					}
					System.out.print("ALL"+DaoFactory.getInstance().getJpaTagDao().getAll());
				}
			}
		}
		if (e.getSource() instanceof JTextArea) {
			JTextArea cell = (JTextArea) e.getSource();

		if(cell.getName().equals(PhotoPanelConstantsI.COMMENT)){
			if (cell.getParent() instanceof PhotoPanel) {
				String path = ((PhotoPanel)cell.getParent()).getPath();			
				pictureDao.addComment(cell.getText(), path);
				System.out.println("JTF" +cell.getText());
				System.out.println(pictureDao.findById(path));
			}
		}
	}
}
}
