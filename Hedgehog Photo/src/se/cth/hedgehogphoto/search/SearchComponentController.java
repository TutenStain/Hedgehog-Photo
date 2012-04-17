package se.cth.hedgehogphoto.search;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import se.cth.hedgehogphoto.FileObject;
import se.cth.hedgehogphoto.Files;

/**
 * @author Barnabas Sapan
 */
public class SearchComponentController{
	private SearchComponentView view;
	private final FileObject fo;
	private Color oldColor;
	
	public SearchComponentController(SearchComponentView _view, final FileObject _fo){
		view = _view;
		fo = _fo;
		
		view.addMouseListener(new MouseListener(){
			//TODO This needs to be done.
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Clicked on: " + fo.getFilePath());
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
