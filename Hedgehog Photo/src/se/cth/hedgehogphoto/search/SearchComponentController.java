package se.cth.hedgehogphoto.search;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import se.cth.hedgehogphoto.FileObject;
import se.cth.hedgehogphoto.Files;
import se.cth.hedgehogphoto.database.DatabaseHandler;

/**
 * @author Barnabas Sapan
 */
public class SearchComponentController{
	private SearchComponentView view;
	private SearchModel model;
	private final FileObject fo;
	private Color oldColor;
	
	public SearchComponentController(SearchComponentView _view, final FileObject _fo, final SearchModel _model){
		view = _view;
		fo = _fo;
		model = _model;
		
		view.addMouseListener(new MouseListener(){
			//TODO This needs to be done.
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Clicked on: " + fo.getFilePath());
				//Files.getInstance().setList(DatabaseHandler.searchPicturesfromTags(fo.getTags().get(0)));
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
}
