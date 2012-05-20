import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.util.List;
import java.util.Observable;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import se.cth.hedgehogphoto.database.PictureObject;

/**
 * @author Barnabas Sapan
 */

@SuppressWarnings("serial")
public class JPopupPreview extends JPopupMenu implements PreviewI {
	/** Better to add everything to a JPanel first
	 *  instead of adding directly to Showa JPopup to prevent some rendering issues. */
	private JPanel panel;
	private JTextField textField;
	private SearchModel model;
	
	private final NotificationListItem messageItem = new NotificationListItem();
	private final JPopupListItem [] listItems;
	private final int MAX_LIST_ITEMS = 5;

	public JPopupPreview(){
		this.listItems = new JPopupListItem[this.MAX_LIST_ITEMS];
		for (int index = 0; index < this.MAX_LIST_ITEMS; index++) {
			this.listItems[index] = new JPopupListItem();
		}
		
		this.panel = new JPanel();
		this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.PAGE_AXIS));
		add(this.panel);
		setFocusable(false);
	}
	
	/**
	 * Adds the same listener to all the PopupPreview's
	 * items.
	 * @param listener the PopupItem-listener
	 */
	@Override
	public void addMouseListener(MouseAdapter listener) {
		this.messageItem.addMouseListener(listener);
		for (JPopupListItem item : listItems) {
			item.addMouseListener(listener);
		}
	}

	@Override
	public void setTextField(JTextField t){
		this.textField = t;
	}
	
	public void setListItems(List<PictureObject> pictures) {
		this.messageItem.setPictures(pictures);
		
		for (int index = 0; index < this.MAX_LIST_ITEMS; index++) {
			if (pictures.size() > index)
				this.listItems[index].setPicture(pictures.get(index));
			else
				this.listItems[index].setPicture(null);
		}
	}
	
	@Override
	public void setModel(SearchModel model) {
		this.model = model;
	}
	
	@Override
	public JPopupMenu getPopupView() {
		return this;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof SearchModel) {
			this.model = (SearchModel) arg;
		}
		
		/**-50 to count for the offset of the textbox*/
		show(this.textField, -50, this.textField.getHeight());
		List<PictureObject> pictures = this.model.getPictures();
		setListItems(pictures);
		
		this.panel.removeAll();

		/**Adds the search results to the popup, if result resulted in no matches, add
		a no result label.*/
		if (!pictures.isEmpty()) {
			if (pictures.size() > 2) {
				this.messageItem.setMessage(SearchConstants.SEE_MORE);
				this.panel.add(this.messageItem);
				this.panel.add(new JSeparator());
			}

			int nbrOfItems = 0;
			for (JPopupListItem item : listItems) {
				if (item.hasPicture()) {
					this.panel.add(item);
					nbrOfItems++;
				}
			}

			setPopupSize(250, (nbrOfItems * 70));
			
		} else {
			this.messageItem.setBackground(Color.GRAY);
			this.messageItem.setMessage(SearchConstants.NO_ITEMS);
			this.panel.add(this.messageItem);
			setPopupSize(250, 40);
		}

		this.panel.revalidate();
	}
}
