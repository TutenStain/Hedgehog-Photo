package se.cth.hedgehogphoto.search.view;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import se.cth.hedgehogphoto.database.Files;
import se.cth.hedgehogphoto.database.PictureObject;
import se.cth.hedgehogphoto.search.model.SearchModel;

/**
 * @author Barnabas Sapan
 */

@SuppressWarnings("serial")
public class JPopupPreview extends JPopupMenu implements Observer, PreviewI {
	/** Better to add everything to a JPanel first
	 *  instead of adding directly to a JPopup to prevent some rendering issues. */
	private JPanel panel;
	private JTextField textField;
	
	private final NotificationListItem messageItem = new NotificationListItem();
	private final JPopupListItem [] listItems;
	private final int MAX_LIST_ITEMS = 5;

	public JPopupPreview(){
		this.listItems = new JPopupListItem[this.MAX_LIST_ITEMS];
		this.panel = new JPanel();
		this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.PAGE_AXIS));
		add(this.panel);
		setFocusable(false);
	}

	@Override
	public void setTextField(JTextField t){
		this.textField = t;
	}
	
	public void updateGUI() {
		
	}

	@Override
	public void update(Observable o, Object arg) {
		final SearchModel model = (SearchModel)arg;
		System.out.println("UPDATE @ SEARCH_PREVIEW_VIEW: " + model.getSearchQueryText());
		show(this.textField, -50, this.textField.getHeight()); //-50 to count for the offset of the textbox
		List<PictureObject> pictures = model.getSearchObjects();
		Iterator<PictureObject> itr = pictures.iterator();
		this.panel.removeAll();

		//Adds the search results to the popup, if result resulted in no matches, add
		//a no result label.
		if (!pictures.isEmpty()) {
			if (pictures.size() > 2) {
				this.messageItem.setMessage("More results for \"" + model.getSearchQueryText() + "\"");
				this.messageItem.addMouseListener(new MouseAdapter() {     
					@Override
					public void mouseClicked(MouseEvent e) {
						Files.getInstance().setPictureList(model.getSearchObjects());
					}
				});
				this.panel.add(this.messageItem);
				this.panel.add(new JSeparator());
			}

			int i = 0;
			while(itr.hasNext() && i < 5){
				PictureObject pic = itr.next();
				JPopupListItem item = new JPopupListItem(pic);
				this.panel.add(item);
				i++;	
			}

			setPopupSize(250, (i * 70));
		} else {
			this.messageItem.setBackground(Color.GRAY);
			this.messageItem.setMessage("No results for '" + model.getSearchQueryText() + "'. Try again!");
			this.panel.add(this.messageItem);
			setPopupSize(250, 40);
		}

		this.panel.revalidate();
	}
}
