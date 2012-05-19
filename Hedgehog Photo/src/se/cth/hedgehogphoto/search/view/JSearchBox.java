package se.cth.hedgehogphoto.search.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;

import se.cth.hedgehogphoto.search.model.SearchConstants;
import se.cth.hedgehogphoto.search.model.SearchModel;

/**
 * @author Barnabas Sapan
 */

@SuppressWarnings("serial")
public class JSearchBox extends JPanel implements Observer{
	private Dimension searchBoxSize = new Dimension(100, 30);
	private Dimension searchButtonSize = new Dimension(100, 30);
	private JTextField searchBox;
	private JButton searchButton;
	
	private JPopupPreview preview;
	private SearchModel model;

	public JSearchBox(SearchModel model, JPopupPreview popup){
		setLayout(new FlowLayout());
		this.searchButton = new JButton(SearchConstants.SEARCH_BUTTON_TEXT);
		this.searchButton.setPreferredSize(this.searchButtonSize);
		this.searchBox = new JTextField(SearchConstants.PLACE_HOLDER_TEXT);
		this.searchBox.setPreferredSize(this.searchBoxSize);
		add(this.searchBox);
		add(this.searchButton);
		
		this.setModel(model);
		this.setSearchPreview(popup);
		
		addObserverToModel();
		
		setPreferredSize(new Dimension(250, 30));
	}
	
	public JSearchBox(SearchModel model) {
		this(model, null);
	}
	
	public void addObserverToModel() {
		this.model.addObserver(this);
		if (this.preview != null) 
			this.model.addObserver(this.preview);
	}

	public void setSearchBoxFocusListener(FocusListener fl){
		this.searchBox.addFocusListener(fl);
	}

	public void setSearchBoxDocumentListener(DocumentListener dl){
		this.searchBox.getDocument().addDocumentListener(dl);
	}

	public void setSearchBoxEnterListener(KeyAdapter keyListener){
		this.searchBox.addKeyListener(keyListener);
	}

	public void setSearchButtonListener(ActionListener ac){
		this.searchButton.addActionListener(ac);
	}

	public String getPlaceholderText(){
		return SearchConstants.PLACE_HOLDER_TEXT;
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
		if (preview != null) {
			this.preview = preview;
			this.preview.setTextField(this.searchBox);
			add(this.preview);
		}
	}
	
	public void setModel(SearchModel model) {
		this.model = model;
		if (this.preview != null)
			this.preview.setModel(model);
	}

	public void setSearchBoxText(String txt){
		this.searchBox.setText(txt);
	}

	public String getSearchBoxText(){
		return this.searchBox.getText();
	}
	
	public void setPreviewComponentListener(MouseAdapter listener) {
		if (this.preview != null) {
			this.preview.addMouseListener(listener);
		}
	}

	@Override
	public void update(Observable o, Object arg) { //TODO: Do we need to listen?				
	}
}
