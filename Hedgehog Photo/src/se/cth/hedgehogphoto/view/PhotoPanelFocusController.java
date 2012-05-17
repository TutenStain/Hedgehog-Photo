package se.cth.hedgehogphoto.view;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

import se.cth.hedgehogphoto.database.DaoFactory;
import se.cth.hedgehogphoto.database.JpaPictureDao;

public class PhotoPanelFocusController implements FocusListener{
	private static DaoFactory daoFactory = DaoFactory.getInstance();
	private JpaPictureDao jpd = daoFactory.getJpaPictureDao();
	private String path; 
	private String focusCommand;

	public PhotoPanelFocusController(String path, String focusCommand){
		this.path = path;
		this.focusCommand = focusCommand;
	}

	@Override
	public void focusGained(FocusEvent arg0) {		
	}

	@Override
	public void focusLost(FocusEvent e) {
		JTextField cell = (JTextField) e.getSource();  

		if(focusCommand.equals("comment")){
			jpd.addComment(cell.getText(), path);
			System.out.println("JTF" +cell.getText());
			System.out.println(jpd.findById(path));
		}
		if(focusCommand.equals("location")){
			jpd.addLocation(cell.getText(), path);
			System.out.println("JTF" +cell.getText());
			System.out.println(jpd.findById(path));
		}
		if(focusCommand.equals("name")){
			jpd.setName(cell.getText(), path);
			System.out.println("JTF" +cell.getText());
			System.out.println(jpd.findById(path));
		}
		if(focusCommand.equals("tags")){
			jpd.deleteTags(path);
			JTextField jtf = (JTextField)e.getSource();
			String[] tags = jtf.getText().split(";");
			for(int i = 0; i < tags.length; i++){
				jpd.addTag(tags[i], path);
				System.out.println("JTF" +jtf.getText());
			}
			System.out.print("ALL"+DaoFactory.getInstance().getJpaTagDao().getAll());
		}
	}
}
