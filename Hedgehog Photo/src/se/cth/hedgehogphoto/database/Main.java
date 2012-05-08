package se.cth.hedgehogphoto.database;

import java.util.ArrayList;
import java.util.List;



public class Main {

	public static void main(String[] arg){
	//System.out.print(System.getProperty("java.home"));
		//new PictureInserter();
		//System.out.print(DatabaseHandler.makeFileObjectfromPath("C:\\Users\\Julia\\Pictures\\20111229\\IMG_0175.JPG"));
	//System.out.print(DatabaseHandler.searchPictureNames("P7040011.JPG"));	
	DatabaseHandler db = DatabaseHandler.getInstance();
	//db.deleteAll();
	//System.out.print(db.getAllPictures());
	//db.addTagtoPicture("lollllloooo", "bafddjgfddyfg¨jjsgjjfllgdsgdr");
	//JpaPictureDao jpd = new JpaPictureDao();
	//System.out.print(jpd.findById("bafddjgfddyfg¨jjsgjjfllgdsgdr"));
	FileObject f = new ImageObject();
	f.setFilePath("fileSsfsvvvvvkkkkhjujesrhhh");
	f.setAlbumName("albumjefss");
	f.setComment("comsfefvvvvvffsk");
	f.setDate("datesfsk");
	f.setFileName("namelssf");
	List<String> tags = new ArrayList<String>();
	tags.add("tagsfls");
	f.setTags(tags);
db.insertPicture(f);
System.out.print(db.getAllPictures());

	//JpaTagDao jtd = new JpaTagDSystemao();
	//Tag tag = jtd.findById("lovvtv");
	//System.out.print(tag.getPictures());
	//Picture p = jpd.findById("pathe");
	//db.addTagtoPicture("NYTAGGyr","pathe");
//	System.out.print(p);
			}
}
