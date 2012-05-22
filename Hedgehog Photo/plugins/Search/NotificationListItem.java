import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import se.cth.hedgehogphoto.database.PictureObject;

/**
 * Represents an item which is added to the popup-preview.
 * Displays a message only, and can be clickable etc, ie
 * behaves exactly as any other item in the preview.
 * @author Florian Minges
 */
@SuppressWarnings("serial")
public class NotificationListItem extends JPanel implements JPopupItemI {
	private JLabel notification;
	private List<PictureObject> pictures;
	
	public NotificationListItem() {
		initialize();
	}
	
	private void initialize() {
		setLayout(new FlowLayout());
		this.pictures = new ArrayList<PictureObject>();
		this.notification = new JLabel("default text");
		add(this.notification);
	}
	
	public void setMessage(String message) {
		this.notification.setText(message);
	}
	
	@Override
	public void addMouseListener(MouseAdapter listener) {
		super.addMouseListener(listener);
		this.notification.addMouseListener(listener);
	}
	
	public void setPictures(List<PictureObject> pictures) {
		this.pictures = pictures;
	}

	@Override
	public List<PictureObject> getPictures() {
		return this.pictures;
	}
}
