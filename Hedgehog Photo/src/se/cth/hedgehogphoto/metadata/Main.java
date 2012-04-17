package se.cth.hedgehogphoto.metadata;

import se.cth.hedgehogphoto.database.DatabaseHandler;

public class Main {

	public static void main(String[] arg){
		new PictureInserter();
		//System.out.print(DatabaseHandler.makeFileObjectfromPath("C:\\Users\\Julia\\Pictures\\20111229\\IMG_0175.JPG"));
		//System.out.print(DatabaseHandler.getAllPictures());
	}
}
