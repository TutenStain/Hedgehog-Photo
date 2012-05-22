import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 
 * @author David Grankvist
 */

public class NoteController {
	private NoteModel model;
	private NoteView view;

	public NoteController(NoteModel m, NoteView v){
		this.model = m;
		this.view = v;

		this.view.setSliderListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent arg0) {
				if(arg0.getSource() instanceof JSlider){
					JSlider slider = (JSlider)arg0.getSource();
					model.setCircleDiam(slider.getValue());
				}
			}
		});
		
		this.view.setColorChooserListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(arg0.getSource() instanceof JButton){
					JButton button = (JButton) arg0.getSource();
					model.setColor(button.getBackground());
				}
			}

		});
		
		this.view.setDrawAreaListeners(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent me) {
				if(me.getSource() instanceof JPanel){
					JPanel drawArea = (JPanel)me.getSource();
					PaintUtils.paintCircle(drawArea.getGraphics(), me.getX(), me.getY(), model.getCircleDiam(), model.getColor());
				}
			}
		}, new MouseAdapter(){
			@Override
			public void mouseDragged(MouseEvent me){
				if(me.getSource() instanceof JPanel){
					JPanel drawArea = (JPanel)me.getSource();
					PaintUtils.paintCircle(drawArea.getGraphics(), me.getX(), me.getY(), model.getCircleDiam(), model.getColor());
				}
			}
		});
		
		this.view.setEraseButtonListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				PaintUtils.erasePainting(view.getDrawArea());
			}
			
		});
	}
}
