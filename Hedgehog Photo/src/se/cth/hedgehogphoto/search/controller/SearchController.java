package se.cth.hedgehogphoto.search.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import se.cth.hedgehogphoto.database.DatabaseAccess;
import se.cth.hedgehogphoto.database.Files;
import se.cth.hedgehogphoto.database.PictureObject;
import se.cth.hedgehogphoto.search.model.SearchModel;
import se.cth.hedgehogphoto.search.model.SearchThread;
import se.cth.hedgehogphoto.search.view.JPopupItemI;
import se.cth.hedgehogphoto.search.view.JSearchBox;
import se.cth.hedgehogphoto.search.view.PreviewI;

/**
 * @author Barnabas Sapan
 */

public class SearchController {
	private SearchModel model;
	private JSearchBox view;
	
	private Thread searchThread;
	
	private Files files;
	private DatabaseAccess db;
	
	public SearchController(SearchModel model, JSearchBox view, PreviewI preview, Files files, DatabaseAccess db){
		this.model = model;
		this.view = view;
		this.view.setSearchPreview(preview);
		this.db = db;
		this.searchThread = new SearchThread(this.model, this.view.getSearchBoxText(), this.db);
		this.files = files;
		
		this.view.setSearchBoxActionListener(new SearchBoxEnterListener());
		this.view.setSearchBoxFocusListener(new SearchBoxFocusListener());
		this.view.setSearchBoxDocumentListener(new SearchBoxDocumentListener());
		this.view.setSearchButtonListener(new SearchButtonListener());
		
		this.view.setPreviewComponentListener(new PreviewComponentMouseListener());
	}
	
	public SearchController(SearchModel model, JSearchBox view, Files files, DatabaseAccess db){
		this(model, view, null, files, db);
	}
	
	//-----------------------------LISTENERS-----------------------------------

	/** Listens to mouse-events of the JPopupItemI. */
	public class PreviewComponentMouseListener extends MouseAdapter {
		
		private JPopupItemI getItem(MouseEvent e) {
			JPopupItemI item = null;
			if (e.getSource() instanceof JPopupItemI) {
				item = (JPopupItemI) e.getSource();
			} else if (e.getComponent().getParent() instanceof JPopupItemI) {
				item = (JPopupItemI) e.getComponent().getParent();
			}
			
			return item;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			JPopupItemI item = getItem(e);
			if (item != null)
				files.setPictureList(item.getPictures());
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			JPopupItemI item = getItem(e);
			if (item != null)
				item.setBackground(JPopupItemI.HOVER_COLOR);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			JPopupItemI item = getItem(e);
			if (item != null)
				item.setBackground(JPopupItemI.DEFAULT_COLOR);
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}
	}
	
	//------------------------------SEARCHBOX-LISTENERS------------------------------

	/** Enter is pressed from the textfield, do and display search! */
	public class SearchBoxEnterListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!view.getSearchBoxText().equals(view.getPlaceholderText())){
				List<PictureObject> pictures = model.getPictures(); //Performance enhancement: Interrupt searchThread, if it is running, so we don't search 2 times
				files.setPictureList(pictures);
			}
		}	
	}


	/** Changes between the standby text (no focus) and allowing the user to enter text (focus). */
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
	
	/** Calls update() on each keystroke by the user. */
	public class SearchBoxDocumentListener implements DocumentListener {
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
        
        /** Updates our model to always contain the latest 
         *  and most recent search query. */
        private void update(){
        	if (searchBoxContainsQuery()) {
        		if (searchThread.getState() != Thread.State.NEW) {
            		searchThread.interrupt();
            		searchThread = new SearchThread(model, view.getSearchBoxText(), db);
            	}
        		searchThread.start();
        	}
        }
        
        private boolean searchBoxContainsQuery() {
        	String text = view.getSearchBoxText();
        	text = text.trim();
        	boolean noQuery = (text.isEmpty()) || (text.equals(view.getPlaceholderText()));
        	boolean shortQuery = text.length() < 2; //don't search if only 1 letter
        	
        	return !(noQuery || shortQuery);
        }
	}
	
	public class SearchButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!view.getSearchBoxText().equals(view.getPlaceholderText())){
				Thread searchThread = new SearchThread(model, view.getSearchBoxText(), 0, db);
				searchThread.start();
				files.setPictureList(model.getPictures());
			}
		}
	}
}
