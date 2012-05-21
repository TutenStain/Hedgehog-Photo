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
	private Font oldFont;
	private Cursor oldCursor;

	public TagComponentController(){}
	
	public TagComponentController(TagCloudModel model, TagCloudView view){
		this.model = model;
		this.view = view;
		this.view.setMouseListener(this);
		this.model.addObserver(this.view);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource() instanceof TagComponent){
			TagComponent tag = (TagComponent)e.getSource();
			tag.setFont(this.oldFont);		
			this.view.setCursor(this.oldCursor);
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource() instanceof TagComponent){
			TagComponent tag = (TagComponent)e.getSource();
			this.oldCursor = this.view.getCursor();
			this.oldFont = tag.getFont();
			tag.setFont(tag.getFont().deriveFont(Font.BOLD));	
			this.view.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() instanceof TagComponent){
			TagComponent tag = (TagComponent)e.getSource();
			this.model.updateSearchPicturesfromTags(tag.getText());	
		}
	}
}
