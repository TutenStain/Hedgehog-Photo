package se.cth.hedgehogphoto.search;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;

import sun.print.resources.serviceui;

/**
 * 
 * @author Barnabas Sapan
 *
 */

public class SearchView extends JPanel implements Observer{
	private SearchPreviewView svp;
	private String searchButtonText = "Search";
	private String placeHolderText = "Search...";
	private Dimension searchBoxSize = new Dimension(150, 30);
	private Dimension searchButtonSize = new Dimension(100, 30);
	private JTextField searchBox;
	private JButton searchButton;
	private SearchPreviewView spv;
	
	public SearchView(){
		setLayout(new FlowLayout());
		searchButton = new JButton(searchButtonText);
		searchButton.setPreferredSize(searchButtonSize);
		searchBox = new JTextField(placeHolderText);
		searchBox.setPreferredSize(searchBoxSize);
		add(searchBox);
		add(searchButton);
	}
	
	public void setSearchBoxFocusListener(FocusListener fl){
		searchBox.addFocusListener(fl);
	}
	
	public void setSeachBoxDocumentListener(DocumentListener dl){
		searchBox.getDocument().addDocumentListener(dl);
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
	 * @param _spv The search preview model.
	 */
	//TODO Use interface argument instead.
	public void setSearchPreview(SearchPreviewView _spv){
		spv = _spv;
		spv.setTextField(searchBox);
		add(spv);
	}
	
	public void setSearchBoxText(String txt){
		searchBox.setText(txt);
	}
	
	public String getSearchBoxText(){
		return searchBox.getText();
	}

	@Override
	public void update(Observable o, Object arg) {
		if(spv == null){
			SearchModel model = (SearchModel)arg;
			System.out.println("UPDATE @ VIEW: " + model.getSearchQueryText());
			//System.out.println(model.getSearchObjects());
		}
	}
}
