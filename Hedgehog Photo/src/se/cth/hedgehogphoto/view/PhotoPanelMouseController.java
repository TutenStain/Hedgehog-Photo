package se.cth.hedgehogphoto.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

public class PhotoPanelMouseController {
	private PhotoPanel photoPanel;
	
	public PhotoPanelMouseController(PhotoPanel pp){
		photoPanel = pp;
		photoPanel.setMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				if(e.getSource() instanceof JLabel){
					JLabel label = (JLabel) e.getSource();
				}
			}
		});
	}
}
