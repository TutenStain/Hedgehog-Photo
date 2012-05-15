import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import se.cth.hedgehogphoto.plugin.*;
import se.cth.hedgehogphoto.database.DatabaseAccess;

/**
 * @author Barnabas Sapan
 */
public class TagComponentController implements MouseListener{
	private TagCloudView view;
	private TagComponent tag;
	private DatabaseAccess db;
	private Font oldFont;
	private Cursor oldCursor;

	public TagComponentController(){}
	
	public TagComponentController(TagCloudView _view, TagComponent _tag, DatabaseAccess _db){
		view = _view;
		tag = _tag;
		db = _db;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
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
		System.out.println("Clicked on: " + tag.getText());
		//Files.getInstance().setPictureList((DatabaseHandler.searchPicturesfromTags(tag.getText())));
		db.updateSearchPicturesfromTags(tag.getText());
	}
}