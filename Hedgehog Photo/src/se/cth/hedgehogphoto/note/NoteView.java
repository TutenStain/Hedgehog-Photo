package se.cth.hedgehogphoto.note;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import se.cth.hedgehogphoto.Constants;

public class NoteView extends JPanel implements ChangeListener, Observer, ActionListener{
	
	private JPanel bottomPanel = new JPanel();
	private JSlider slider = new JSlider(5, 15);
	private NoteDrawPanel board = new NoteDrawPanel(200, 200);
	private NotePreview preview = new NotePreview(100);
	
	private ColorChooser colorChooser = new ColorChooser(new Dimension(5, 5));
	
	private NoteModel model = new NoteModel(10, Color.black);
	
	public NoteView(){
		this.setPreferredSize(new Dimension(Constants.PREFERRED_MODULE_WIDTH, Constants.PREFERRED_MODULE_HEIGHT));
		this.setLayout(new BorderLayout());
		this.add(board, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);
		
		bottomPanel.setBackground(Color.white);
		bottomPanel.setLayout(new GridLayout(1, 3));
		
		
		slider.setBackground(Color.white);
		slider.addChangeListener(this);
		
		colorChooser.addListener(this);
		
		bottomPanel.add(preview);
		bottomPanel.add(colorChooser);
		bottomPanel.add(slider);
		
		model.addObserver(this);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider slider = (JSlider)e.getSource();
		this.model.setCircleDiam(slider.getValue());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		this.model.setColor(button.getBackground());
		
	}
	
	@Override
	public void update(Observable o, Object arg) {
		this.board.setCircleDiam(model.getCircleDiam());
		this.board.setColor(model.getColor());
		this.preview.paintPreview(model.getCircleDiam(), model.getColor());
	}
}
