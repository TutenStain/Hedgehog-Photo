package se.cth.hedgehogphoto.database;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.sanselan.formats.png.PngText.tEXt;

import se.cth.hedgehogphoto.objects.FileObject;




/**
 * 
 * @author Julia
 *
 */


public class DatabaseHandler implements DatabaseAccess, Runnable{
	private static Files files = Files.getInstance(); 
	private static List<Picture> pictureList = files.getPictureList();
	private static List<Album> albumList = files.getAlbumList();
	
	/*private static JpaAlbumDao jad = new JpaAlbumDao();
	private static JpaCommentDao jcd = new JpaCommentDao();
	private static JpaLocationDao jld = new JpaLocationDao();
	private static JpaTagDao jtd = new JpaTagDao();
	private static JpaPictureDao jpd = new JpaPictureDao();*/
	
	private JpaAlbumDao jad;
	private JpaCommentDao jcd;
	private JpaLocationDao jld;
	private JpaTagDao jtd;
	private JpaPictureDao jpd;
	
	private EntityManager em;// = Entity.entityManager;
	private static DatabaseHandler db = null;

	private static Thread t = Thread.currentThread();
	
	public DatabaseHandler () {}

	public static DatabaseHandler getInstance(){
		if(db == null){
			db = new DatabaseHandler();
		}

		return db;
	}
	
	@Override
	public void run(){
		jad = new JpaAlbumDao();
		jcd = new JpaCommentDao();
		jld = new JpaLocationDao();
		jtd = new JpaTagDao();
		jpd = new JpaPictureDao();
		em = Entity.entityManager;
	}
		
	/**
	 * A method that return all tags in the database as strings.
	 * @return List<String>
	 */
	public List<String> getTags(){
		List<Tag> tags = jtd.getAll();
		List<String> taggs = new ArrayList<String>();
		for(Tag t:tags){
			taggs.add(t.getTag());
		}
		return taggs;
	}
	/**
	 * return all locations as Strings
	 * @return List<String>
	 */
	public List<String> getLocations(){
		List<Location> location = jld.getAll();
		List<String> locations = new ArrayList<String>();
		for(Location l:location){
			locations.add(l.getLocation());
		}
		return locations;
	}

	public void updateSearchPictureNames(String search){
		pictureList = searchPictureNames(search);
		files.setPictureList(pictureList);
	}
	public List<Picture> searchPictureNames(String search){

		return jpd.searchfromNames(search);
	}
	public void updateSearchPicturesfromDates(String search){
		pictureList = searchPicturesfromDates(search);
		files.setPictureList(pictureList);
	}
	public List<Picture> searchPicturesfromDates(String search){
	
			List<Picture> pics = jpd.findByString("date", search);
			return pics;
	}
	public void updateSearchAlbumsfromDates(String search){
		albumList = searchAlbumsfromDates(search);
		files.setAlbumList(albumList);
	}
	public  List<Album>  searchAlbumsfromDates(String search){
		return jad.searchfromDates(search);

	}
	public void updateSearchAlbumNames(String search){
		albumList =searchAlbumNames(search);
		files.setAlbumList(albumList);
	}
	public List<Album> searchAlbumNames(String search){
		return jad.searchfromNames(search);
	}
	public void updateSearchPicturesfromComments(String search){
		pictureList = searchPicturesfromComments(search);
		files.setPictureList(pictureList);
	}
	public List<Picture> searchPicturesfromComments(String search){
		return jpd.searchfromComments(search);
	}
	public void updateSearchAlbumsfromComments(String search){
		albumList = searchAlbumsfromComments(search);
		files.setAlbumList(albumList);
	}
	public  List<Album> searchAlbumsfromComments(String search){
		return jad.searchfromComments(search);

	}

	public  void updateSearchPicturesfromTags(String search){
		pictureList =searchPicturesfromTags(search);
		files.setPictureList(pictureList);
	}

	public List<Picture> searchPicturesfromTags(String search){
		return jpd.searchfromTags(search);
	}
	public  void updateSearchPicturefromsLocations(String search){
		pictureList = searchPicturefromsLocations(search);
		files.setPictureList(pictureList);
	}
	public List<Picture> searchPicturefromsLocations(String search){
		return jpd.searchfromLocations(search);
	}
	public void updateAlbumsfromSearchTags(String search){
		albumList = searchAlbumfromTag(search);
		files.setAlbumList(albumList);
	}
	public  List<Album> searchAlbumfromTag(String search){
		return jad.searchfromTags(search);
	}
	public void updateSearchAlbumsfromLocations(String search){
		albumList  =  searchAlbumsfromLocations(search);
		files.setAlbumList(albumList);
	}
	public List<Album> searchAlbumsfromLocations(String search){
		return jad.searchfromTags(search);
	}
	public void updateAllPictures(){
		pictureList = getAllPictures();
		files.setPictureList(pictureList);


	}
	public List<Picture> getAllPictures(){
		return jpd.getAll();

	}
	public void updateAllAlbums(){
		albumList = getAllAlbums();
		files.setAlbumList(albumList);
	}
	public List<Album> getAllAlbums(){
		Query p = em.createQuery("select t from Album t");
		return (List<Album>)p.getResultList();

	}

	public  void insertPicture(FileObject f){
		jpd.insertPicture(f);
	}
		
		public  void updateAddTagtoPicture(String tag, String filePath){
		for(Picture f:pictureList){
			if(f.getPath().equals(filePath))
				pictureList.remove(f);
		}
		addTagtoPicture(tag, filePath);
		files.setPictureList(pictureList);
	}


	public  void addTagtoPicture(String tag, String filePath){
		jpd.addTag(tag, filePath);
	}
	public  void updateAddTagtoAlbum(String tag, String albumName){
		for(Album f:albumList){
			if(f.getAlbumName().equals(albumName))
				albumList.remove(f);
		}
		addTagtoAlbum(tag, albumName);
		files.setAlbumList(albumList);
	}
	public  void addTagtoAlbum(String tag, String albumName){
		jad.addTag(tag, albumName);

	}
	public  void updateaddCommenttoPicture(String comment, String filePath){
		for(Picture f:pictureList){
			if(f.getPath().equals(filePath))
				pictureList.remove(f);
		}
		addCommenttoPicture(comment, filePath);
		files.setPictureList(pictureList);
	}



	public  void addCommenttoPicture(String comment, String filePath){
		jpd.addComment(comment, filePath);
	}
	public  void updateAddCommenttoAlbum(String comment, String albumName){
		for(Album f:albumList){
			if(f.getAlbumName().equals(albumName))
				albumList.remove(f);
		}
		addCommenttoAlbum(comment, albumName);
		files.setAlbumList(albumList);
	}


	public  void addCommenttoAlbum(String comment, String filePath){
		jad.addComment(comment, filePath);
	}

	public  void updateAddLocationtoPicture(String location, String filePath){
		for(Picture f:pictureList){
			if(f.getPath().equals(filePath))
				pictureList.remove(f);
		}
		addLocationtoPicture(location, filePath);
		files.setPictureList(pictureList);
	}



	public  void addLocationtoPicture(String location, String filePath){
		jpd.addLocation(location, filePath);
	}
	public  void updateAddLocationtoAlbum(String location, String albumName){
		for(Album f:albumList){
			if(f.getAlbumName().equals(albumName))
				albumList.remove(f);
		}
		addLocationtoAlbum(location, albumName);
		files.setAlbumList(albumList);
	}


	public  void addLocationtoAlbum(String location, String albumName){
		jad.addLocation(location, albumName);
	}
	public  void deleteAll(){
	
		List<Picture> allPictures = jpd.getAll();
		for(Picture pic:allPictures){
			deletePicture(pic.getPath());
		}
	}

	public  void updateDeletePictures(String filePath){
		deletePicture(filePath);
		pictureList = getAllPictures();
		files.setPictureList(pictureList);
	}


	public void deletePicture (String filePath){
		jpd.deletePicture(filePath);		
	}

	

	public void updateDeleteTagsfromPictures(String filePath){
		deleteTagsfromPicture(filePath);
		for(Picture p: pictureList){
			if(p.getPath().equals(filePath))
				pictureList.remove(p);
		}
		files.setPictureList(pictureList);
	}

	public void deleteTagsfromPicture(String filePath){
		jpd.deleteTags(filePath);
	}
	public void updateDeletePicturefromAlbum(String filePath){
		jad.deletePicture(filePath);
		String albumName = "";
		for(Picture f: pictureList){
			if(f.getPath().equals(filePath))
				pictureList.remove(f);
			albumName = f.getAlbum().getAlbumName();
		}
		files.setPictureList(pictureList);
		if(albumName.equals("")){
			for(Album f:albumList){
				if(f.getAlbumName().equals(albumName))
					albumList.remove(f);
			}
			files.setAlbumList(albumList);
		}
	}

	public void deletePicturefromAlbum(String filePath){
		jpd.deletePicture(filePath);
	}
	public  void updateDeleteCommentfromPicture(String filePath){
		deleteCommentfromPicture(filePath);
		for(Picture f: pictureList){
			if(f.getPath().equals(filePath))
				pictureList.remove(f);

		}
		files.setPictureList(pictureList);
	}


	public  void deleteCommentfromPicture(String filePath){
		jpd.deleteComment(filePath);
	}
	public  void updateDeleteLocationfromPicture(String filePath){
		deleteLocationfromPicture(filePath);
		for(Picture f: pictureList){
			if(f.getPath().equals(filePath))
				pictureList.remove(f);

		}
		files.setPictureList(pictureList);
	}


	public  void deleteLocationfromPicture(String filePath){
		jpd.deleteLocation(filePath);
	}
	public  void updateDeleteCommentfromAlbum(String albumName){
		deleteCommentfromAlbum(albumName);
		for(Album f:albumList){
			if(f.getAlbumName().equals(albumName))
				albumList.remove(f);
		}
		files.setAlbumList(albumList);
	}


	public void deleteCommentfromAlbum(String albumName){
		jad.deleteComment(albumName);
	}
	public  void updateDeleteLocationfromAlbum(String albumName){
		deleteLocationfromAlbum(albumName);
		for(Album f:albumList){
			if(f.getAlbumName().equals(albumName))
				albumList.remove(f);
		}
		files.setAlbumList(albumList);
	}

	public void deleteLocationfromAlbum(String albumName){
		jad.deleteLocation(albumName);
	}
	public void updateDeleteTagsfromAlbum(String albumName){
		deleteTagsfromAlbum(albumName);
		for(Album f:albumList){
			if(f.getAlbumName().equals(albumName))
				albumList.remove(f);
		}
		files.setAlbumList(albumList);
	}

	public void deleteTagsfromAlbum(String albumName){
		jad.deleteTags(albumName);
	}
	@Override
	public DaoFactory getFactory() {
		// TODO Auto-generated method stub
		return null;
	}
}

