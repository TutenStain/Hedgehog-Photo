package se.cth.hedgehogphoto.controller;

import javax.swing.JFrame;

import se.cth.hedgehogphoto.model.MainModel;
import se.cth.hedgehogphoto.view.MainView;

public class MainViewInitiator {
	private MainView view;
	private MainModel model;
	
	public MainViewInitiator(JFrame frame) {
		this.model = new MainModel();
		this.view = new MainView(frame);
		model.addObserver(this.view);
		new DefaultController(this.view);
		this.view.addPhotoPanelActionListeners(new PhotoPanelActionListener());
		this.view.addPhotoPanelFocusListener(new PhotoPanelFocusListener());
		this.view.addPhotoPanelMouseListener(new PhotoPanelMouseListener());
	}
	
	public MainView getMainView() {
		return this.view;
	}
	
	public MainModel getMainModel() {
		return this.model;
	}

}
