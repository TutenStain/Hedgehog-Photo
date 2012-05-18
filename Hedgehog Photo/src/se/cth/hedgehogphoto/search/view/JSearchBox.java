package se.cth.hedgehogphoto.search.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;

import se.cth.hedgehogphoto.database.Files;
import se.cth.hedgehogphoto.database.PictureObject;
import se.cth.hedgehogphoto.search.model.SearchModel;

/**
 * @author Barnabas Sapan
 */

@SuppressWarnings("serial")
public class JSearchBox extends JPanel implements Observer{
	private final String searchButtonText = "Search";
	private final String placeHolderText = "Search...";
	private Dimension searchBoxSize = new Dimension(100, 30);
	private Dimension searchButtonSize = new Dimension(100, 30);
	private JTextField searchBox;
	private JButton searchButton;
	
	private JPopupPreview preview;

	public JSearchBox(JPopupPreview popup){
		setLayout(new FlowLayout());
		this.searchButton = new JButton(this.searchButtonText);
		this.searchButton.setPreferredSize(this.searchButtonSize);
		this.searchBox = new JTextField(this.placeHolderText);
		this.searchBox.setPreferredSize(this.searchBoxSize);
		add(this.searchBox);
		add(this.searchButton);
		if(popup != null){
			this.setSearchPreview(popup);
			//TODO: SHould listen to the searchModel
		}
	}

	public void setSearchBoxFocusListener(FocusListener fl){
		this.searchBox.addFocusListener(fl);
	}

	public void setSearchBoxDocumentListener(DocumentListener dl){
		this.searchBox.getDocument().addDocumentListener(dl);
	}

	public void setSearchBoxActionListener(ActionListener l){
		this.searchBox.addActionListener(l);
	}

	public void setSearchButtonListener(ActionListener ac){
		this.searchButton.addActionListener(ac);
	}

	public String getPlaceholderText(){
		return this.placeHolderText;
	}

	public Dimension getSearchBoxSize(){
		return this.searchBox.getSize();
	}

	public void setSearchBoxSize(Dimension d){
		this.searchBox.setPreferredSize(d);
	}

	public Dimension getSearchButtonSize(){
		return searchButton.getSize();
	}

	public void setSearchButtonSize(Dimension d){
		this.searchButton.setPreferredSize(d);
	}

	/**
	 * Specifies a popup search preview view. If not set no dynamic 
	 * search preview will be shown.
	 * @param spv The search preview model.
	 */
	//TODO Use interface argument instead.
	public void setSearchPreview(JPopupPreview preview){
		this.preview = preview;
		this.preview.setTextField(this.searchBox);
		add(this.preview);
	}

	public void setSearchBoxText(String txt){
		this.searchBox.setText(txt);
	}

	public String getSearchBoxText(){
		return this.searchBox.getText();
	}

	@Override
	public void update(Observable o, Object arg) {
		//If we dont have a spv specified, just make the search and update the view.
		if(this.preview == null){
			if (arg instanceof SearchModel) {
				SearchModel model = (SearchModel)arg;
				System.out.println("UPDATE @ VIEW: " + model.getSearchQueryText());

				List<PictureObject> pic = model.getSearchObjects();;
				Files.getInstance().setPictureList(pic);				
			}
		}
	}
}
