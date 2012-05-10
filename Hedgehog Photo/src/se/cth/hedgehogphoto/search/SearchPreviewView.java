package se.cth.hedgehogphoto.search;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import se.cth.hedgehogphoto.database.Picture;
import se.cth.hedgehogphoto.objects.FileObject;

/**
 * @author Barnabas Sapan
 */

public class SearchPreviewView extends JPopupMenu implements Observer{
	private JTextField jtf;
	//Better to add everything to a JPanel first
	//instead of adding directly to a JPopup to prevent some rendering issues.
	private JPanel panel;
	
	public SearchPreviewView(){
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		add(panel);
		
		setFocusable(false);
	}
	
	public void setTextField(JTextField t){
		jtf = t;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		SearchModel model = (SearchModel)arg;
		System.out.println("UPDATE @ SEARCH_PREVIEW_VIEW: " + model.getSearchQueryText());
		show(jtf, -50, jtf.getHeight());
		List<Picture> fo = model.getSearchObjects();
		Iterator<Picture> itr = fo.iterator();
		panel.removeAll();
		
		//TODO Change this value to reflect the max size of the popup.
		if(fo.size() > 2){
			JPanel p = new JPanel();
			p.setLayout(new FlowLayout());
			p.add(new JLabel("More results for '" + model.getSearchQueryText() + "'"));
			p.addMouseListener(new MouseAdapter() {     
				@Override
				public void mouseClicked(MouseEvent e) {
					System.out.println("Clicked ON ME!!!");
		        }
			});
			panel.add(p);
			panel.add(new JSeparator());
		}
		
		int i = 0;
		while(itr.hasNext() && i < 5){
			Picture ob = itr.next();
			SearchComponentView view = new SearchComponentView(ob);
			new SearchComponentController(view, ob, model);
			panel.add(view);
			i++;
			//panel.add(new JSeparator());		
		}
		
		setPopupSize(250, (i * 70));

		panel.revalidate();
	}
}
