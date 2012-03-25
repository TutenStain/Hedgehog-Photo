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
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;

import sun.print.resources.serviceui;

public class SearchView extends JPanel implements Observer{
	private SearchModel sm;
	private JTextField searchBox;
	private JButton searchButton;
	
	public SearchView(SearchModel _sm){
		sm = _sm;
		setLayout(new FlowLayout());
		searchButton = new JButton(sm.getSearchButtonText());
		searchButton.setPreferredSize(sm.getSearchButtonSize());
		searchBox = new JTextField(sm.getPlaceholderText());
		searchBox.setPreferredSize(sm.getSearchBoxSize());
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
	
	public void setSearchBoxText(String txt){
		searchBox.setText(txt);
	}
	
	public String getSearchBoxText(){
		return searchBox.getText();
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("UPDATE @ VIEW");
	}
}
