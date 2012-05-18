import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author Barnabas Sapan
 */
public class TagComponentController implements MouseListener{
	private TagCloudModel model;
	private TagCloudView view;
	private TagComponent tag;
	private Font oldFont;
	private Cursor oldCursor;

	public TagComponentController(){}
	
	public TagComponentController(TagCloudModel model, TagCloudView view, TagComponent tag){
		this.model = model;
		this.view = view;
		this.tag = tag;
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
		model.updateSearchPicturesfromTags(tag.getText());
	}
}
