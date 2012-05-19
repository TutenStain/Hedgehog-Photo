package se.cth.hedgehogphoto.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import se.cth.hedgehogphoto.database.DaoFactory;
import se.cth.hedgehogphoto.database.JpaCommentDao;
import se.cth.hedgehogphoto.database.JpaLocationDao;
import se.cth.hedgehogphoto.database.JpaPictureDao;

public class PhotoPanelController implements ActionListener {
	static DaoFactory daoFactory = DaoFactory.getInstance();
	JpaPictureDao jpd = daoFactory.getJpaPictureDao();
	private String path; 
	public PhotoPanelController(String path){
		this.path = path;
	}
	@Override
	public void actionPerformed(ActionEvent a) {
		if(a.getActionCommand().equals("comment")){
			JTextField jtf = (JTextField)a.getSource();
			jpd.addComment( jtf.getText(), path);
			System.out.println("JTF" + jtf.getText() +" " +  path);
			System.out.print(new JpaCommentDao().getAll());
		}
		if(a.getActionCommand().equals("location")){
			JTextField jtf = (JTextField)a.getSource();
			jpd.addLocation(jtf.getText(), path);
			System.out.println("JTF" +jtf.getText());
			System.out.print(new JpaLocationDao().getAll());
		}
		if(a.getActionCommand().equals("tags")){
			//for(int i = 0; i <jpd.findById(path).getTags().size();i++){
		
			//System.out.print(DaoFactory.getInstance().getJpaTagDao().getAll());
		//}
			jpd.deleteTags(path);
			JTextField jtf = (JTextField)a.getSource();
			String[] tags = jtf.getText().split(";");
			for(int i = 0; i < tags.length; i++){
				jpd.addTag(tags[i], path);
				System.out.println("JTF" +jtf.getText());
			}
			System.out.print("ALL"+DaoFactory.getInstance().getJpaTagDao().getAll());
		}
		if(a.getActionCommand().equals("name")){
			JTextField jtf = (JTextField)a.getSource();
			jpd.setName(jtf.getText(), path);
			System.out.println("JTF" +jtf.getText());
			System.out.print(jpd.findById(path));
			
		}
	}


	
	
}

