package se.cth.hedgehogphoto.controller;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import se.cth.hedgehogphoto.metadata.PictureInserter;
import se.cth.hedgehogphoto.view.ImageUtils;
import se.cth.hedgehogphoto.view.MainView;
import se.cth.hedgehogphoto.view.PhotoPanel;

/**
 * 
 * @author David Grankvist
 */

public class DefaultController {
	private MainView view;

	public DefaultController(MainView v){
		view = v;

		view.setUploadPictureButtonListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new PictureInserter();
			}
			
		});
		view.setPhotoNameButtonListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				for(int i=0;i<view.getPhotoPanels().size();i++){
					view.getPhotoPanels().get(i).displayPhotoName(!view.getPhotoPanels().get(i).isVisiblePhotoName());
				}
			}

		});		
		view.setCommentsButtonListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				for(int i=0;i<view.getPhotoPanels().size();i++){
					view.getPhotoPanels().get(i).displayComments(!view.getPhotoPanels().get(i).isVisibleComments());
				}
			}

		});
		view.setLocationButtonListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i=0;i<view.getPhotoPanels().size();i++){
					view.getPhotoPanels().get(i).displayLocation(!view.getPhotoPanels().get(i).isVisibleLocation());
				}
			}

		});
		view.setTagsButtonListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i=0;i<view.getPhotoPanels().size();i++){
					view.getPhotoPanels().get(i).displayTags(!view.getPhotoPanels().get(i).isVisibleTags());
				}
			}

		});
		view.setSliderListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) {
				if(arg0.getSource() instanceof JSlider){
					JSlider slider = (JSlider)arg0.getSource();
					float scale = (float)slider.getValue()/100;
					List<PhotoPanel> ppList = view.getPhotoPanels();
					for(int i=0;i<ppList.size();i++){
						Image image = ppList.get(i).getIcon().getImage();
						Dimension d = ppList.get(i).getScaleDimension();
						BufferedImage bi = ImageUtils.resize(image, Math.round(d.width*scale),
								Math.round(d.height*scale));
						ImageIcon icon2 = new ImageIcon(bi);
						view.getPhotoPanels().get(i).setIcon(icon2);
					}
				}
			}
		});
		view.setPrevNextBtnListeners(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(arg0.getSource() instanceof JButton){
					JButton btn = (JButton) arg0.getSource();
					JPanel panel = view.getSinglePhotoPanel();
					PhotoPanel pp = view.getCurrentPhotoPanel();
					List<PhotoPanel> ppList = view.getPhotoPanels();
					if(ppList.contains(pp)){
						panel.removeAll();
						if(btn.getText().equals("Previous")){
							if((ppList.indexOf(pp) - 1) < 0){
								pp = ppList.get(ppList.size() - 1);
							}else{
								pp = ppList.get(ppList.indexOf(pp) - 1);
							}
						}
						if(btn.getText().equals("Next")){
							if((ppList.indexOf(pp) + 1) >= ppList.size()){
								pp = ppList.get(0);
							}else{
								pp = ppList.get(ppList.indexOf(pp) + 1);
							}
							
						}
						Image image = pp.getIcon().getImage();
						BufferedImage bi = ImageUtils.resize(image, image.getWidth(null), image.getHeight(null));
						ImageIcon icon2 = new ImageIcon(bi);
						pp.setIcon(icon2);
						panel.add(pp);
						panel.repaint();
						panel.revalidate();
						view.resetSlider();
					}
				}
			}
			
		});
		view.setBackBtnListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
					JPanel cardPanel = view.getCardPanel();
					CardLayout cl = (CardLayout) cardPanel.getLayout();
					cl.show(cardPanel, "All");
					view.setTopButtonsVisibility(false);
					JPanel panel = view.getPhotoViewPanel();
					List<PhotoPanel> ppList = view.getPhotoPanels();
					//TODO not sure why this is needed
					for(int i=0;i<ppList.size();i++){
						Image image = ppList.get(i).getIcon().getImage();
						Dimension d = ppList.get(i).getScaleDimension();
						BufferedImage bi = ImageUtils.resize(image, Math.round(d.width),
								Math.round(d.height));
						ImageIcon icon2 = new ImageIcon(bi);
						view.getPhotoPanels().get(i).setIcon(icon2);
						panel.add(view.getPhotoPanels().get(i));
					}
					view.resetSlider();
			}
			
		});
	}
	
}
