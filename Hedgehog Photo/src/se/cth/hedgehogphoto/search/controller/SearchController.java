package se.cth.hedgehogphoto.search.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import se.cth.hedgehogphoto.database.Files;
import se.cth.hedgehogphoto.database.PictureObject;
import se.cth.hedgehogphoto.search.model.SearchModel;
import se.cth.hedgehogphoto.search.model.SearchThread;
import se.cth.hedgehogphoto.search.view.JSearchBox;

/**
 * @author Barnabas Sapan
 */

public class SearchController {
	private SearchModel model;
	private JSearchBox view;
	
	public SearchController(SearchModel model, JSearchBox view){
		this.model = model;
		this.view = view;
		
		//Enter is pressed from the textfield, do and display search!
		this.view.setSearchBoxActionListener(new SearchBoxActionListener());
		
		//Changes between the standby text (no focus) and allowing the user to enter text (focus).
		this.view.setSearchBoxFocusListener(new SearchBoxFocusListener());
		
		//Calls update() on each keystroke by the user.
		this.view.setSearchBoxDocumentListener(new SearchBoxDocumentListener());
		
		this.view.setSearchButtonListener(new SearchButtonListener());
	}
	
	public class SearchBoxActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!view.getSearchBoxText().equals(view.getPlaceholderText())){
				List<PictureObject> fo = model.getSearchObjects();
				Files.getInstance().setPictureList(fo);
			}	
		}
	}
	
	public class SearchBoxFocusListener implements FocusListener {
        @Override
        public void focusGained(FocusEvent e) {
        	if (view.getSearchBoxText().equals(view.getPlaceholderText())) {   
            	view.setSearchBoxText("");
            }
        }
        
        @Override
        public void focusLost(FocusEvent e) {
        	if(view.getSearchBoxText().isEmpty()){
        		view.setSearchBoxText(view.getPlaceholderText());
        	}
        }

	}
	
	public class SearchBoxDocumentListener implements DocumentListener {
		private Thread t = new SearchThread(model, 500);
		
        @Override
        public void changedUpdate(DocumentEvent e) {
            update();
        }
        
        @Override
        public void removeUpdate(DocumentEvent e) {
            update();
        }
        
        @Override
        public void insertUpdate(DocumentEvent e) {
            update();
        }
        
        private void update(){
        	if(!view.getSearchBoxText().isEmpty() && !view.getSearchBoxText().equals(view.getPlaceholderText())){
            	//Updates our model to always contain the latest and most recent search query.
        		model.setSearchQueryText(view.getSearchBoxText());
        		if(t.getState() == Thread.State.NEW){
            		t.start();
            	} else {
            		t.interrupt();
            		t = new SearchThread(model, 500);
            		t.start();
            	}
        	}
        }
	}
	
	public class SearchButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!view.getSearchBoxText().equals(view.getPlaceholderText())){
				new SearchThread(model, 0).start();
			}
		}
	}
}
