package se.cth.hedgehogphoto.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import se.cth.hedgehogphoto.database.DaoFactory;
import se.cth.hedgehogphoto.database.JpaCommentDao;
import se.cth.hedgehogphoto.database.JpaLocationDao;
import se.cth.hedgehogphoto.database.JpaPictureDao;
import se.cth.hedgehogphoto.geocoding.GeocodingInitiator;
import se.cth.hedgehogphoto.model.PhotoPanelConstantsI;
import se.cth.hedgehogphoto.view.PhotoPanel;

public class PhotoPanelActionListener implements ActionListener {
	static DaoFactory daoFactory = DaoFactory.getInstance();
	JpaPictureDao pictureDao = daoFactory.getJpaPictureDao();

	public PhotoPanelActionListener(){
	}
	@Override
	public void actionPerformed(ActionEvent a) {
		if (a.getSource() instanceof JTextField) {
			JTextField cell = (JTextField) a.getSource();

			if(cell.getName().equals(PhotoPanelConstantsI.COMMENT)){
				if (cell.getParent() instanceof PhotoPanel) {
					String path = ((PhotoPanel)cell.getParent()).getPath();
					pictureDao.addComment( cell.getText(), path);
					System.out.println("JTF" + cell.getText() +" " +  path);
					System.out.print(new JpaCommentDao().getAll());
				}
			} else if(cell.getName().equals(PhotoPanelConstantsI.LOCATION)){
				if (cell.getParent() instanceof PhotoPanel) {
					String path = ((PhotoPanel)cell.getParent()).getPath();	
					new GeocodingInitiator(cell.getText(), path);
					System.out.println("JTF" +cell.getText());
					System.out.print(new JpaLocationDao().getAll());
				}
			} else if(cell.getName().equals(PhotoPanelConstantsI.TAGS)){
				if (cell.getParent() instanceof PhotoPanel) {
					String path = ((PhotoPanel)cell.getParent()).getPath();
					pictureDao.deleteTags(path);
					String[] tags = cell.getText().split(";");
					for(int i = 0; i < tags.length; i++){
						pictureDao.addTag(tags[i], path);
						System.out.println("JTF" +cell.getText());
					}
					System.out.print("ALL"+DaoFactory.getInstance().getJpaTagDao().getAll());
				}
			} else if(cell.getName().equals(PhotoPanelConstantsI.NAME)){
				if (cell.getParent() instanceof PhotoPanel) {
					String path = ((PhotoPanel)cell.getParent()).getPath();
					pictureDao.setName(cell.getText(), path);
					System.out.println("JTF" +cell.getText());
					System.out.print(pictureDao.findById(path));

				}
			}
		}
	}




}

