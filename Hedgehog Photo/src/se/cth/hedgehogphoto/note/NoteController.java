package se.cth.hedgehogphoto.note;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class NoteController {
	private NoteModel model;
	private NoteView view;
	
	public NoteController(NoteModel m, NoteView v){
		model = m;
		view = v;
		
		view.setSliderListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent arg0) {
				JSlider slider = (JSlider)arg0.getSource();
				model.setCircleDiam(slider.getValue());
			}
		});
		view.setColorChooserListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JButton button = (JButton) arg0.getSource();
				model.setColor(button.getBackground());
			}
			
		});
		view.setDrawAreaListeners(new MouseAdapter(){
			public void mousePressed(MouseEvent me) {
				JPanel drawArea = (JPanel)me.getSource();
				PaintUtils.paintCircle(drawArea.getGraphics(), me.getX(), me.getY(), model.getCircleDiam(), model.getColor());
			}
		}, new MouseAdapter(){
			public void mouseDragged(MouseEvent me){
				JPanel drawArea = (JPanel)me.getSource();
				PaintUtils.paintCircle(drawArea.getGraphics(), me.getX(), me.getY(), model.getCircleDiam(), model.getColor());
			}
		});
	}
}
