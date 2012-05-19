package se.cth.hedgehogphoto.controller;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import se.cth.hedgehogphoto.model.MainModel;
import se.cth.hedgehogphoto.view.ImageUtils;
import se.cth.hedgehogphoto.view.MainView;

/**
 * 
 * @author David Grankvist
 */

public class DefaultController {
	private MainModel model;
	private MainView view;

	public DefaultController(MainModel m, MainView v){
		model = m;
		view = v;

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
					for(int i=0;i<view.getPhotoPanels().size();i++){
						Image image = view.getPhotoPanels().get(i).getIcon().getImage();
						List<Dimension> dims = view.getNewDimensions();
						BufferedImage bi = ImageUtils.resize(image, Math.round(dims.get(i).width*scale),
								Math.round(dims.get(i).height*scale));
						ImageIcon icon2 = new ImageIcon(bi);
						view.getPhotoPanels().get(i).setIcon(icon2);
					}
				}
			}
		});
	}
}
