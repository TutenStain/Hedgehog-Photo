package se.cth.hedgehogphoto.search.controller;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import se.cth.hedgehogphoto.database.Files;
import se.cth.hedgehogphoto.database.Picture;
import se.cth.hedgehogphoto.database.PictureObject;
import se.cth.hedgehogphoto.search.view.SearchComponentView;

/**
 * @author Barnabas Sapan
 */
public class SearchComponentController{
	private SearchComponentView view;
	private final PictureObject fo;
	private Color oldColor;

	public SearchComponentController(SearchComponentView _view, final PictureObject _fo){
		view = _view;
		fo = _fo;

		view.addMouseListener(new MouseListener(){
			//TODO This needs to be done.
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Clicked on: " + fo.getPath());
				List<PictureObject> pic = new ArrayList<PictureObject>();
				pic.add(fo);
				Files.getInstance().setPictureList(pic);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				oldColor = view.getBackground();
				view.setBackground(new Color(210, 210, 210));
			}

			@Override
			public void mouseExited(MouseEvent e) {	
				view.setBackground(oldColor);
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
	}
	
	/*
	public class ComponentMouseListener extends MouseAdapter {
		//TODO This needs to be done.
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() instanceof SearchComponentView) {
				SearchComponentView view = (SearchComponentView) e.getSource();
				System.out.println("Clicked on: " + view.getPicture().getPath());
				List<Picture> pic = new ArrayList<Picture>();
				pic.add(view.getPicture());
				Files.getInstance().setPictureList(pic);
			}
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			if (e.getSource() instanceof SearchComponentView) {
				SearchComponentView view = (SearchComponentView) e.getSource();
				oldColor = view.getBackground();
				view.setBackground(new Color(210, 210, 210));
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (e.getSource() instanceof SearchComponentView) {
				SearchComponentView view = (SearchComponentView) e.getSource();
				view.setBackground(oldColor);
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}
	}*/
}
