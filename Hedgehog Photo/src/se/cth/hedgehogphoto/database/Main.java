package se.cth.hedgehogphoto.database;

public class Main {

	public static void main(String[] args){

	//new PictureInserter();
	DatabaseHandler bajs = DatabaseHandler.getInstance();
	bajs.getAllPictures();
	
	}


}