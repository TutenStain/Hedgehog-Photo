package se.cth.hedgehogphoto.search.view;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Represents an item which is added to the popup-preview.
 * Displays a message only, and can be clickable etc, ie
 * behaves exactly as any other item in the preview.
 * @author Florian Minges
 */
public class NotificationListItem extends JPanel implements JPopupItemI {
	private JLabel notification;

	public NotificationListItem() {
		this("default text");
	}
	
	public NotificationListItem(String message) {
		setLayout(new FlowLayout());
		this.notification = new JLabel(message);
		add(this.notification);
	}
	
	public void setMessage(String message) {
		this.notification.setText(message);
	}
	
	public void addMouseListener(MouseAdapter listener) {
		super.addMouseListener(listener);
		this.notification.addMouseListener(listener);
	}
}
