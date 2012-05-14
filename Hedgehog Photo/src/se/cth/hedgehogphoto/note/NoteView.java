package se.cth.hedgehogphoto.note;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;

import se.cth.hedgehogphoto.Constants;

/**
 * @author David
 */

public class NoteView extends JPanel implements Observer{
	
	private JPanel bottomPanel = new JPanel();
	private JPanel drawArea = new JPanel();
	private JSlider slider = new JSlider(5, 15);
	private NotePreview preview = new NotePreview();
	private ColorChooser colorChooser = new ColorChooser();
	private NoteModel model;
	
	public NoteView(NoteModel model){
		this.setPreferredSize(new Dimension(Constants.PREFERRED_MODULE_WIDTH, Constants.PREFERRED_MODULE_HEIGHT));
		this.setLayout(new BorderLayout());
		this.add(drawArea, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);
		
		drawArea.setBackground(Color.white);
		bottomPanel.setBackground(Color.white);
		bottomPanel.setLayout(new GridLayout(1, 3));
		
		slider.setBackground(Color.white);
	
		bottomPanel.add(preview);
		bottomPanel.add(colorChooser);
		bottomPanel.add(slider);
		
		bottomPanel.setPreferredSize(new Dimension(300, 50));
		
		this.model = model;
		model.addObserver(this);
	}
	
	public void setSliderListener(ChangeListener l){
		this.slider.addChangeListener(l);
	}
	
	public void setColorChooserListener(ActionListener l){
		this.colorChooser.addListener(l);
	}
	
	public void setDrawAreaListeners(MouseListener ml, MouseMotionListener mml){
		this.drawArea.addMouseListener(ml);
		this.drawArea.addMouseMotionListener(mml);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		this.preview.paintPreview(model.getCircleDiam(), model.getColor());
	}
}
