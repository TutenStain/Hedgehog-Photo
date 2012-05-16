package se.cth.hedgehogphoto.search.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
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
public class SearchView extends JPanel implements Observer{
	private String searchButtonText = "Search";
	private String placeHolderText = "Search...";
	private Dimension searchBoxSize = new Dimension(100, 30);
	private Dimension searchButtonSize = new Dimension(100, 30);
	private JTextField searchBox;
	private JButton searchButton;
	private SearchPreviewView spv = null;

	public SearchView(SearchPreviewView spv){
		setLayout(new FlowLayout());
		searchButton = new JButton(searchButtonText);
		searchButton.setPreferredSize(searchButtonSize);
		searchBox = new JTextField(placeHolderText);
		searchBox.setPreferredSize(searchBoxSize);
		add(searchBox);
		add(searchButton);
		if(spv != null){
			this.setSearchPreview(spv);
		}
	}

	public void setSearchBoxFocusListener(FocusListener fl){
		searchBox.addFocusListener(fl);
	}

	public void setSearchBoxDocumentListener(DocumentListener dl){
		searchBox.getDocument().addDocumentListener(dl);
	}

	public void setSearchBoxActionListener(ActionListener l){
		searchBox.addActionListener(l);
	}

	public void setSearchButtonListener(ActionListener ac){
		searchButton.addActionListener(ac);
	}

	public String getPlaceholderText(){
		return placeHolderText;
	}

	public Dimension getSearchBoxSize(){
		return searchBox.getSize();
	}

	public void setSearchBoxSize(Dimension d){
		searchBox.setPreferredSize(d);
	}

	public Dimension getSearchButtonSize(){
		return searchButton.getSize();
	}

	public void setSearchButtonSize(Dimension d){
		searchButton.setPreferredSize(d);
	}

	/**
	 * Specifies a popup search preview view. If not set no dynamic 
	 * search preview will be shown.
	 * @param spv The search preview model.
	 */
	//TODO Use interface argument instead.
	public void setSearchPreview(SearchPreviewView spv){
		this.spv = spv;
		this.spv.setTextField(searchBox);
		add(this.spv);
	}

	public void setSearchBoxText(String txt){
		searchBox.setText(txt);
	}

	public String getSearchBoxText(){
		return searchBox.getText();
	}

	@Override
	public void update(Observable o, Object arg) {
		//If we dont have a spv specified, just make the search and update the view.
		if(spv == null){
			if (arg instanceof SearchModel) {
				SearchModel model = (SearchModel)arg;
				System.out.println("UPDATE @ VIEW: " + model.getSearchQueryText());

				List<PictureObject> pic = model.getSearchObjects();;
				Files.getInstance().setPictureList(pic);				
			}
		}
	}
}
