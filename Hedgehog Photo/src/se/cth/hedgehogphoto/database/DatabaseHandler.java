package se.cth.hedgehogphoto.database;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import se.cth.hedgehogphoto.objects.FileObject;

/**
 * 
 * @author Julia
 *
 */
public class DatabaseHandler implements DatabaseAccess, Runnable{
	private static Files files = Files.getInstance(); 
	private static List<PictureObject> pictureList = files.getPictureList();
	private static List<AlbumObject> albumList = files.getAlbumList();

	private JpaAlbumDao albumDao;
	private JpaCommentDao commentDao;
	private JpaLocationDao locationDao;
	private JpaTagDao tagDao;
	private JpaPictureDao pictureDao;

	private EntityManager em;// = Entity.entityManager;
	private static DatabaseHandler db = null;

	public DatabaseHandler () {}

	public static DatabaseHandler getInstance(){
		if(db == null){
			db = new DatabaseHandler();
		}

		return db;
	}

	@Override
	public void run(){
		this.albumDao = new JpaAlbumDao();
		this.commentDao = new JpaCommentDao();
		this.locationDao = new JpaLocationDao();
		this.tagDao = new JpaTagDao();
		this.pictureDao = new JpaPictureDao();
		em = Entity.entityManager;
	}

	/**
	 * A method that return all tags in the database as strings.
	 * @return List<String>
	 */
	public List<String> getTags(){
		List<Tag> tags = this.tagDao.getAll();
		List<String> taggs = new ArrayList<String>();
		for(TagObject t : tags){
			taggs.add(t.getTag());
		}
		return taggs;
	}
	/**
	 * return all locations as Strings
	 * @return List<String>
	 */
	public List<String> getLocations(){
		List<Location> location = this.locationDao.getAll();
		List<String> locations = new ArrayList<String>();
		for(LocationObject l:location){
			locations.add(l.getLocation());
		}
		return locations;
	}

	public void updateSearchPictureNames(String search){
		pictureList.clear();
		pictureList.addAll(searchPictureNames(search));
		files.setPictureList(pictureList);
	}
	public List<? extends PictureI> searchPictureNames(String search){
		return this.pictureDao.searchfromNames(search);
	}
	public void updateSearchPicturesfromDates(String search){
		pictureList.clear();
		pictureList.addAll(searchPicturesfromDates(search));
		files.setPictureList(pictureList);
	}

	public List<? extends PictureObject> searchPicturesfromDates(String search){
		return this.pictureDao.findByString("date", search);
	}

	public void updateSearchAlbumsfromDates(String search){
		albumList.clear();
		albumList.addAll(searchAlbumsfromDates(search));
		files.setAlbumList(albumList);
	}

	public  List<? extends AlbumI>  searchAlbumsfromDates(String search){
		return this.albumDao.searchfromDates(search);
	}

	public void updateSearchAlbumNames(String search){
		albumList.clear();
		albumList.addAll(searchAlbumNames(search));
		files.setAlbumList(albumList);
	}
	public List<? extends AlbumI> searchAlbumNames(String search){
		return this.albumDao.searchfromNames(search);
	}
	public void updateSearchPicturesfromComments(String search){
		pictureList.clear();
		pictureList.addAll(searchPicturesfromComments(search));
		files.setPictureList(pictureList);
	}
	public List<? extends PictureI> searchPicturesfromComments(String search){
		return this.pictureDao.searchfromComments(search);
	}
	public void updateSearchAlbumsfromComments(String search){
		albumList.clear();
		albumList.addAll(searchAlbumsfromComments(search));
		files.setAlbumList(albumList);
	}
	public  List<? extends AlbumI> searchAlbumsfromComments(String search){
		return this.albumDao.searchfromComments(search);

	}

	public  void updateSearchPicturesfromTags(String search){
		pictureList.clear();
		pictureList.addAll(searchPicturesfromTags(search));
		files.setPictureList(pictureList);
	}


	@Override
	public List<? extends PictureObject> searchPicturesfromTags(String search){
		return this.pictureDao.searchfromTags(search);
	}

	public void updateSearchPicturefromsLocations(String search){
		pictureList.clear();
		pictureList.addAll(searchPicturefromsLocations(search));
		files.setPictureList(pictureList);
	}

	public List<? extends PictureI> searchPicturefromsLocations(String search){
		return this.pictureDao.searchfromLocations(search);
	}
	public void updateAlbumsfromSearchTags(String search){
		albumList.clear();
		albumList.addAll(searchAlbumfromTag(search));
		files.setAlbumList(albumList);
	}
	public  List<? extends AlbumI> searchAlbumfromTag(String search){
		return this.albumDao.searchfromTags(search);
	}
	public void updateSearchAlbumsfromLocations(String search){
		albumList.clear();
		albumList.addAll(searchAlbumsfromLocations(search));
		files.setAlbumList(albumList);
	}
	public List<? extends AlbumI> searchAlbumsfromLocations(String search){
		return this.albumDao.searchfromTags(search);
	}
	public void updateAllPictures(){
		pictureList.clear();
		pictureList.addAll(getAllPictures());
		files.setPictureList(pictureList);


	}
	public List<? extends PictureI> getAllPictures(){
		return this.pictureDao.getAll();
	}

	public void updateAllAlbums(){
		albumList.clear();
		albumList.addAll(getAllAlbums());
		files.setAlbumList(albumList);
	}
	public List<? extends AlbumI> getAllAlbums(){
		Query p = em.createQuery("select t from Album t");
		return (List<Album>)p.getResultList();

	}

	public  void insertPicture(FileObject f){
		this.pictureDao.insertPicture(f);
	}

	public  void updateAddTagtoPicture(String tag, String filePath){
		for(PictureObject f:pictureList){
			if(f.getPath().equals(filePath))
				pictureList.remove(f);
		}
		addTagtoPicture(tag, filePath);
		files.setPictureList(pictureList);
	}


	public  void addTagtoPicture(String tag, String filePath){
		this.pictureDao.addTag(tag, filePath);
	}
	public  void updateAddTagtoAlbum(String tag, String albumName){
		for(AlbumObject f:albumList){
			if(f.getAlbumName().equals(albumName))
				albumList.remove(f);
		}
		addTagtoAlbum(tag, albumName);
		files.setAlbumList(albumList);
	}
	public  void addTagtoAlbum(String tag, String albumName){
		this.albumDao.addTag(tag, albumName);

	}
	public  void updateaddCommenttoPicture(String comment, String filePath){
		for(PictureObject f:pictureList){
			if(f.getPath().equals(filePath))
				pictureList.remove(f);
		}
		addCommenttoPicture(comment, filePath);
		files.setPictureList(pictureList);
	}



	public  void addCommenttoPicture(String comment, String filePath){
		this.pictureDao.addComment(comment, filePath);
	}
	public  void updateAddCommenttoAlbum(String comment, String albumName){
		for(AlbumObject f:albumList){
			if(f.getAlbumName().equals(albumName))
				albumList.remove(f);
		}
		addCommenttoAlbum(comment, albumName);
		files.setAlbumList(albumList);
	}


	public  void addCommenttoAlbum(String comment, String filePath){
		this.albumDao.addComment(comment, filePath);
	}

	public  void updateAddLocationtoPicture(String location, String filePath){
		for(PictureObject f:pictureList){
			if(f.getPath().equals(filePath))
				pictureList.remove(f);
		}
		addLocationtoPicture(location, filePath);
		files.setPictureList(pictureList);
	}



	public  void addLocationtoPicture(String location, String filePath){
		this.pictureDao.addLocation(location, filePath);
	}
	public  void updateAddLocationtoAlbum(String location, String albumName){
		for(AlbumObject f:albumList){
			if(f.getAlbumName().equals(albumName))
				albumList.remove(f);
		}
		addLocationtoAlbum(location, albumName);
		files.setAlbumList(albumList);
	}


	public  void addLocationtoAlbum(String location, String albumName){
		this.albumDao.addLocation(location, albumName);
	}
	public  void deleteAll(){

		List<Picture> allPictures = this.pictureDao.getAll();
		for(PictureObject pic:allPictures){
			deletePicture(pic.getPath());
		}
	}

	public  void updateDeletePictures(String filePath){
		deletePicture(filePath);
		pictureList.clear();
		pictureList.addAll(getAllPictures());
		files.setPictureList(pictureList);
	}


	public void deletePicture (String filePath){
		this.pictureDao.deletePicture(filePath);		
	}



	public void updateDeleteTagsfromPictures(String filePath){
		deleteTagsfromPicture(filePath);
		for(PictureObject p: pictureList){
			if(p.getPath().equals(filePath))
				pictureList.remove(p);
		}
		files.setPictureList(pictureList);
	}

	public void deleteTagsfromPicture(String filePath){
		this.pictureDao.deleteTags(filePath);
	}
	public void updateDeletePicturefromAlbum(String filePath){
		this.albumDao.deletePicture(filePath);
		String albumName = "";
		for(PictureObject f: pictureList){
			if(f.getPath().equals(filePath))
				pictureList.remove(f);
			albumName = f.getAlbum().getAlbumName();
		}
		files.setPictureList(pictureList);
		if(albumName.equals("")){
			for(AlbumObject f:albumList){
				if(f.getAlbumName().equals(albumName))
					albumList.remove(f);
			}
			files.setAlbumList(albumList);
		}
	}

	public void deletePicturefromAlbum(String filePath){
		this.pictureDao.deletePicture(filePath);
	}
	public  void updateDeleteCommentfromPicture(String filePath){
		deleteCommentfromPicture(filePath);
		for(PictureObject f: pictureList){
			if(f.getPath().equals(filePath))
				pictureList.remove(f);

		}
		files.setPictureList(pictureList);
	}


	public  void deleteCommentfromPicture(String filePath){
		this.pictureDao.deleteComment(filePath);
	}
	public  void updateDeleteLocationfromPicture(String filePath){
		deleteLocationfromPicture(filePath);
		for(PictureObject f: pictureList){
			if(f.getPath().equals(filePath))
				pictureList.remove(f);

		}
		files.setPictureList(pictureList);
	}


	public  void deleteLocationfromPicture(String filePath){
		this.pictureDao.deleteLocation(filePath);
	}
	public  void updateDeleteCommentfromAlbum(String albumName){
		deleteCommentfromAlbum(albumName);
		for(AlbumObject f:albumList){
			if(f.getAlbumName().equals(albumName))
				albumList.remove(f);
		}
		files.setAlbumList(albumList);
	}


	public void deleteCommentfromAlbum(String albumName){
		this.albumDao.deleteComment(albumName);
	}
	public  void updateDeleteLocationfromAlbum(String albumName){
		deleteLocationfromAlbum(albumName);
		for(AlbumObject f:albumList){
			if(f.getAlbumName().equals(albumName))
				albumList.remove(f);
		}
		files.setAlbumList(albumList);
	}

	public void deleteLocationfromAlbum(String albumName){
		this.albumDao.deleteLocation(albumName);
	}
	public void updateDeleteTagsfromAlbum(String albumName){
		deleteTagsfromAlbum(albumName);
		for(AlbumObject f:albumList){
			if(f.getAlbumName().equals(albumName))
				albumList.remove(f);
		}
		files.setAlbumList(albumList);
	}

	public void deleteTagsfromAlbum(String albumName){
		this.albumDao.deleteTags(albumName);
	}
	@Override
	public DaoFactory getFactory() {
		// TODO Auto-generated method stub
		return null;
	}
}

