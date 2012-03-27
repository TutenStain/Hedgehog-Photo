package se.cth.hedgehogphoto.search;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

/**
 * 
 * @author Barnabas Sapan
 *
 */

public class SearchPreviewView extends JPopupMenu implements Observer{
	private JPanel jp;
	private JLabel jl;
	private JTextField jtf;
	public SearchPreviewView(){
		jl = new JLabel();
		jp = new JPanel();
		jp.add(jl);
		setLayout(new BorderLayout());
		add(jp);
		setPopupSize(250, 100);
		//Better implementation needed as right now we can't select things in the popup.
		setFocusable(false);
	}
	
	public void setTextField(JTextField t){
		jtf = t;
	}

	@Override
	public void update(Observable o, Object arg) {
		SearchModel model = (SearchModel)arg;
		System.out.println("UPDATE @ SEARCH_PREVIEW_VIEW: " + model.getSearchQueryText());
		jl.setText(model.getSearchQueryText());
		show(jtf, -50, jtf.getHeight());
		System.out.println(model.getSearchObjects());
	}
}
