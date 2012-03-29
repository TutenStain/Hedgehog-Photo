package se.cth.hedgehogphoto.search;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import se.cth.hedgehogphoto.FileObject;

/**
 * 
 * @author Barnabas Sapan
 *
 */

public class SearchPreviewView extends JPopupMenu implements Observer{
	private JTextField jtf;
	
	public SearchPreviewView(){
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));		
		setPopupSize(250, 300);
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
		show(jtf, -50, jtf.getHeight());
		List<FileObject> fo = model.getSearchObjects();
		Iterator<FileObject> itr = fo.iterator();
		removeAll();
		while(itr.hasNext()){
			FileObject ob = itr.next();
			SearchComponent sc = new SearchComponent(ob);
			sc.setAlignmentX(Component.LEFT_ALIGNMENT);
			sc.setAlignmentY(Component.LEFT_ALIGNMENT);
			add(sc);
			
			//Insert a space between each SearchComponent.
			add(Box.createRigidArea(new Dimension(0, 3)));
		}
		revalidate();
		System.out.println(fo);
	}
}
