package se.cth.hedgehogphoto.search;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import se.cth.hedgehogphoto.database.Files;
import se.cth.hedgehogphoto.database.Picture;

/**
 * @author Barnabas Sapan
 */

public class SearchController {
	private SearchModel model;
	private SearchView view;
	
	public SearchController(SearchModel _model, SearchView _view){
		model = _model;
		view = _view;
		
		//Enter is pressed from the textfield, do and display search!
		view.setSearchBoxActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!view.getSearchBoxText().equals(view.getPlaceholderText())){
					List<Picture> fo = model.getSearchObjects();
					Files.getInstance().setPictureList(fo);
				}	
			}
		});
		
		//Changes between the standby text (no focus) and allowing the user to enter text (focus).
		view.setSearchBoxFocusListener(new FocusListener() {
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

		});
		
		//Calls update() on each keystroke by the user.
		view.setSeachBoxDocumentListener(new DocumentListener() {
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
		});
		
		view.setSearchButtonListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!view.getSearchBoxText().equals(view.getPlaceholderText())){
					new SearchThread(model, 0).start();
				}
			}
		});
	}
}
