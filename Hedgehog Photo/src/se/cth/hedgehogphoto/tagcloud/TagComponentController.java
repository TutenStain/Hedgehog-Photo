package se.cth.hedgehogphoto.tagcloud;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

/**
 * @author Barnabas Sapan
 */

public class TagComponentController implements MouseListener{
	private TagCloudView view;
	private TagComponent tag;
	private Font oldFont;
	private Cursor oldCursor;
	
	public TagComponentController(TagCloudView _view, TagComponent _tag){
		view = _view;
		tag = _tag;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("Clicked on: " + tag.getText());
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		tag.setFont(oldFont);		
		view.setCursor(oldCursor);
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		oldCursor = view.getCursor();
		oldFont = tag.getFont();
		tag.setFont(tag.getFont().deriveFont(Font.BOLD));
		view.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}
}