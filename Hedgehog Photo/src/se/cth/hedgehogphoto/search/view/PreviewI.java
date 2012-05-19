package se.cth.hedgehogphoto.search.view;

import java.awt.event.MouseAdapter;
import java.util.Observer;

import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import se.cth.hedgehogphoto.search.model.SearchModel;

/**
 * This interface should be implemented by all
 * popup previews to the search.
 */
public interface PreviewI extends Observer {
	/**
	 * @param t The textfield this popup-window should appear above
	 */
	public void setTextField(JTextField t);
	
	/**
	 * The search backend will be set by this method.
	 * @param model The search model
	 */
	public void setModel(SearchModel model);
	
	public void addMouseListener(MouseAdapter ma);
	
	/**
	 * This method gets called once to add the popupview
	 * @return The view that will be representing the popup
	 */
	public JPopupMenu getPopupView();
}
