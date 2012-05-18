package se.cth.hedgehogphoto.search.view;

import java.util.Observable;

import javax.swing.JTextField;

public interface PreviewI {
	
	//TODO: Add interface for JPopupPreview
	public abstract void setTextField(JTextField t);

	public abstract void update(Observable o, Object arg);
}
