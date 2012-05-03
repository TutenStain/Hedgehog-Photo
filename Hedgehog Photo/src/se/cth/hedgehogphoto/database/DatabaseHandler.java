package se.cth.hedgehogphoto.database;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import se.cth.hedgehogphoto.metadata.*;
import se.cth.hedgehogphoto.*;


/**
 * 
 * @author Julia
 *
 */


public class DatabaseHandler implements DatabaseAccess {
	private Files files = Files.getInstance();
	//private List<FileObject> list = files.getList(); 
	private List<Picture> pictureList = files.getPictureList();
	private List<Album> albumList = files.getAlbumList();
	private final String PERSISTENCE_UNIT_NAME = "hedgehogphoto";
	private EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	private JpaAlbumDao jad = new JpaAlbumDao();
	private JpaCommentDao jcd = new JpaCommentDao();
	private JpaLocationDao jld = new JpaLocationDao();
	private JpaTagDao jtd = new JpaTagDao();
	private JpaPictureDao jpd = new JpaPictureDao();
	private static DatabaseHandler db;
	

	EntityManager em = factory.createEntityManager();
	private int i = 0;
	
	private DatabaseHandler(){
		
	}
	public static DatabaseHandler getInstance(){
		if(db == null)
			db = new DatabaseHandler();
		return db;
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
		Query q = em.createQuery("select t from Picture t where t.date=:date");
		q.setParameter("date", search);
			try{
				List<Picture> pics = q.getResultList();
			return pics;
			
		}catch(Exception e){
			return null;
		}
	}
	public void updateSearchAlbumsfromDates(String search){
		albumList = searchAlbumsfromDates(search);
		files.setAlbumList(albumList);
	}
	public List<Album>  searchAlbumsfromDates(String search){
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
	public List<Album> searchAlbumsfromComments(String search){
		return jad.searchfromComments(search);

	}

	public void updateSearchPicturesfromTags(String search){
		pictureList =searchPicturesfromTags(search);
		files.setPictureList(pictureList);
	}

	public List<Picture> searchPicturesfromTags(String search){
		return jpd.searchfromTags(search);
	}
	public void updateSearchPicturefromsLocations(String search){
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
	public List<Album> searchAlbumfromTag(String search){
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
		Query p = em.createQuery("select t from Picture t");
		return (List<Picture>)p.getResultList();

	}
	public void updateAllAlbums(){
		 albumList = getAllAlbums();
		 files.setAlbumList(albumList);
	 }
	 public List<Album> getAllAlbums(){
		 Query p = em.createQuery("select t from Album t");
		 return (List<Album>)p.getResultList();

	 }



	 /*public FileObject makeFileObjectfromPath(String filePath){
		String name = "";
		String date = "";
		String path ="";

		se.cth.hedgehogphoto.metadata.Location location = new se.cth.hedgehogphoto.metadata.Location("");
		String albumName = "";
		String comment = "";
		Picture picture = new Picture();
		List<String> tags = new ArrayList<String>();

		Query p = em.createQuery("select t from Picture t where t.path=:path");
		p.setParameter("path", filePath);
		try{
			Picture pic = (Picture) p.getSingleResult();
			picture = pic;
			//	path = pic.getPath();
			name=pic.getName();
			albumName = pic.getAlbum().getAlbumName();
			date=pic.getDate();
			albumName = pic.getAlbum().getName();
			comment = pic.getComment().getCommentAsString();


		}
		catch(Exception e){
			return null;
		}
		Query t = em.createQuery("select t from Tag t where t.picture=:picture");
		t.setParameter("picture",picture);
		try{
			List<Tag> tlist = t.getResultList();
			for(int i = 0; i < tlist.size();i++){
				tags.add(tlist.get(i).getTagAsString());
			}

		}
		catch(Exception e){
		}
		FileObject f = new ImageObject(); 


		f.setComment(comment);
		f.setDate(date);
		f.setFilePath(filePath);
		f.setFileName(name);
		f.setTags(tags);

		f.setLocation(location);
		f.setAlbumName(albumName);
		return f;

	}

	public FileObject makeFileObjectfromAlbumName(String albumName){
		List<String> tags = new ArrayList<String>();
		se.cth.hedgehogphoto.metadata.Location location = new se.cth.hedgehogphoto.metadata.Location("");
		Album album = new Album();
		String date = "";
		String coverPath= "";

		String comment = "";
		Query a = em.createQuery("select t from Album t where t.albumName=:albumName");
		a.setParameter("albumName", albumName);
		try{
			album = (Album) a.getSingleResult();
			coverPath =  album.getCoverPath();
		}catch(Exception e){

			return null;
		}
		Query t = em.createQuery("select t from Tag t where t.album=:album");
		t.setParameter("album",album);
		try{
			List<Tag> tlist = t.getResultList();
			for(int i = 0; i < tlist.size();i++){
				tags.add(tlist.get(i).getTagAsString());
			}
		}
		catch(Exception e){
		}

		Query l = em.createQuery("select t from Location t where t.location=:location");
		l.setParameter("location", album.getLocation().getLocationasString());
		try{

			Location loc = (Location) l.getSingleResult();
			location = new se.cth.hedgehogphoto.metadata.Location(loc.getLocation());
			location.setLatitude(loc.getLatitude());
			location.setLongitude(loc.getLongitude());
		}catch(Exception e){
		}
		Query c = em.createQuery("select t from Comment t where t.comment=:comment");

		try{
			c.setParameter("comment", album.getComment().getCommentAsString());
			Comment com = (Comment) c.getSingleResult();
			comment = com.getComment();
		}catch(Exception e){

		}
		FileObject f = new AlbumObject();
		f.setComment(comment);
		f.setLocation(location);
		f.setDate(date);
		f.setAlbumName(albumName);
		f.setCoverPath(coverPath);
		f.setTags(tags);
		return f;
	}
	  */
	 /*	public void updateInsertPicture(ImageObject f){

		boolean pictureExist = false;
		boolean albumExist = false;
		for(FileObject filesObject: list){
			if(filesObject.getFilePath().equals(f.getFilePath())){
				pictureExist = true;
			}
			if(filesObject.getAlbumName().equals(f.getAlbumName())){
				albumExist =  true;
			}
		}
		if(!(pictureExist))
			list.add(f);

		if(!(albumExist))
			list.add(makeFileObjectfromAlbumName(f.getAlbumName()));
		files.setList(list);
		insertPicture(f);

	}*/
	/* public void insertPicture(FileObject f){
		 if(f.getFilePath() != null || (!(f.getFilePath().equals("")))){
			 Album theAlbum = AlbumHandler.dothis(f);
			 Picture pic = PictureHandler.dothis(f, theAlbum);
			 //Picture pic = PictureHandler.getfromPath(f.getFilePath());
			 /*if(pic == null){
				 pic 
			/*	 em.getTransaction().begin();
					
				Picture pic = new Picture();
					pic.setPath(f.getFilePath());	
						pic.setDate(f.getDate());		
					if(f.getFileName() != null ||(!f.getFileName().equals("")))
						pic.setName(f.getFileName());
					if(theAlbum != null){
						pic.setAlbum(theAlbum);
						List<Picture> thePictures = theAlbum.getPicture();
						if(!(thePictures.contains(pic)))
						thePictures.add(pic);
						theAlbum.setPictures(thePictures);
						em.persist(theAlbum);
					}
						em.persist(pic);
						em.getTransaction().commit();	
				 /pic = PictureHandler.makePicture(f, theAlbum);*/
		/*	 }else{
				 PictureHandler.changePicture(pic, f, theAlbum);
			 }*/
		/*	 List<String> tags = f.getTags();
			 if(tags!=null){
				 for(int i = 0; i <tags.size();i++){	
					 Tag tag = TagHandler.getTag(tags.get(i));
					 if(tag == null){
						 tag = new Tag();							
					 }else{
						 TagHandler.changeTag(pic, tag);
					 }
				 }
			 }
			 if((!f.getComment().equals(""))){
				 Comment comment = CommentHandler.getComment(f.getComment());	
				 if(comment == null){		
					 comment = CommentHandler.makeComment(pic, f);			
				 }else{
					 CommentHandler.changeComment(comment, pic, f);
				 }
			 }
			 Location location =  LocationHandler.getLocation(f.getLocation().getLocation());	
			 if(location == null){
				 location = LocationHandler.makeLocation(pic, f);
			 }else{
				 LocationHandler.changeLocation(location, pic, f);
			 }
		 }
	 }*/
	 public void insertPicture(FileObject f){
			if(f.getFilePath() != null || (!(f.getFilePath().equals("")))){
				Album theAlbum = new Album();
				if(f.getAlbumName() != null || (!f.getAlbumName().equals(""))){
				Query a = em.createQuery("select t from Album t where t.albumName=:albumName");
				a.setParameter("albumName", f.getAlbumName());
				try{
					theAlbum=  (Album) a.getSingleResult();
					em.getTransaction().begin();	
					if(theAlbum.getCoverPath().equals("")|| theAlbum.getCoverPath()==null)
						theAlbum.setCoverPath(f.getFilePath());
					em.persist(theAlbum);
					em.getTransaction().commit();
				}catch(Exception e){
					em.getTransaction().begin();
					theAlbum = new Album();	
						theAlbum.setAlbumName(f.getAlbumName());
					theAlbum.setCoverPath(f.getFilePath());
					em.persist(theAlbum);
					em.getTransaction().commit();
				}
				}
				Picture pic = new Picture();
				boolean pictureExist = false;
				Query t = em.createQuery("select t from Picture t where t.path=:path");
				t.setParameter("path", f.getFilePath());
				try{
					pic=  (Picture)t.getSingleResult();

					em.getTransaction().begin();
					pic.setAlbum(theAlbum);
					if(!(f.getDate().equals("")))
					pic.setDate(f.getDate());

					List<Picture> thePictures = theAlbum.getPictures();
					if(!(thePictures.contains(pic)))
						thePictures.add(pic);
					theAlbum.setPictures(thePictures);
					em.persist(pic);
					em.persist(theAlbum);
					em.getTransaction().commit();

				}
				catch(Exception e){
					em.getTransaction().begin();
					pic = new Picture();
					pic.setPath(f.getFilePath());	
						pic.setDate(f.getDate());
					if(f.getFileName() != null ||(!f.getFileName().equals(""))){
						pic.setName(f.getFileName());
						pic.setAlbum(theAlbum);
						List<Picture> thePictures = theAlbum.getPictures();
						thePictures.add(pic);
						theAlbum.setPictures(thePictures);
						em.persist(theAlbum);
						em.persist(pic);
						em.getTransaction().commit();
						pictureExist=true;
					}
					List<String> tags = f.getTags();
					if(tags!=null){
						for(int i = 0; i <tags.size();i++){	
							Query ta = em.createQuery("select t from Tag t where t.tag=:tag");
							ta.setParameter("tag",tags.get(i));
							try{
								Tag tag= (Tag) ta.getSingleResult();
								em.getTransaction().begin();
								List<Picture> ptag= tag.getPictures();						
								if(!(ptag.contains(pic)))
									ptag.add(pic);
								List<Tag> pTags = pic.getTags();
								if(!(pTags.contains(tag)))
									pTags.add(tag);
								tag.setPictures(ptag);						
								pic.setTags(pTags);
								em.persist(tag);
								em.persist(pic);
								em.getTransaction().commit();
							}
							catch(Exception ee){
								if(!(em.getTransaction().isActive())){
									em.getTransaction().begin();
								}
								Tag tag = new Tag();
								tag.setTag(tags.get(i));			
								List<Picture> peg = new ArrayList<Picture>();
								peg.add(pic);
								tag.setPictures(peg);
								List<Tag> pTags = pic.getTags();
								if(pTags!=null)
									pTags.add(tag);		
								pic.setTags(pTags);
								em.persist(tag);
								em.persist(pic);
								em.getTransaction().commit();
							}
						}
					}
					try{
						if((!f.getComment().equals(""))){
							Query c = em.createQuery("select t from Comment t where t.comment=:comment");
							c.setParameter("comment",f.getComment());
							try{
								Comment comment = (Comment)c.getSingleResult();
								em.getTransaction().begin();
								comment.setComment(f.getComment());
								List<Picture> pics = comment.getPictures();
								pics.add(pic);
								comment.setPicture(pics);
								pic.setComment(comment);							
								em.persist(pic);
								em.persist(comment);
								em.getTransaction().commit();
							}catch(Exception eee){				
								em.getTransaction().begin();
								Comment comment = new Comment();		
									comment.setComment(f.getComment());		
								List<Picture> pics = new ArrayList<Picture>();
								pics.add(pic);
								comment.setPicture(pics);
								pic.setComment(comment);
								em.persist(pic);
								em.persist(comment);
								em.getTransaction().commit();
							}
					}
				}catch(Exception k){
					}
					try{
						Query l = em.createQuery("select t from Location t where t.location=:location");
						l.setParameter("location", f.getLocation().getLocation());
						try{
							Location location = (Location)l.getSingleResult();
							em.getTransaction().begin();
							location.setLatitude((f.getLocation().getLatitude()));
							location.setLongitude(f.getLocation().getLongitude());
							pic.setLocation(location);
							em.persist(pic);
							em.persist(location);
							em.getTransaction().commit();
						}catch(Exception b){
							em.getTransaction().begin();
							Location location = new Location();
								location.setLatitude((f.getLocation().getLatitude()));
								location.setLongitude(f.getLocation().getLongitude());
							location.setLocation(f.getLocation().getLocation());
							List<Picture> pics = new ArrayList<Picture>();
							pics.add(pic);
							location.setPictures(pics);
							pic.setLocation(location);
							em.persist(pic);
							em.persist(location);
							em.getTransaction().commit();
						}	
					}catch(Exception j){
					}	
				}
			}
		}

	 public void updateAddTagtoPicture(String tag, String filePath){
		 for(Picture f:pictureList){
			 if(f.getPath().equals(filePath))
				 pictureList.remove(f);
		 }
		 addTagtoPicture(tag, filePath);
		 files.setPictureList(pictureList);
	 }


	 public void addTagtoPicture(String tag, String filePath){
		 jpd.addTag(tag, filePath);
	 }
	 public void updateAddTagtoAlbum(String tag, String albumName){
		for(Album f:albumList){
			if(f.getAlbumName().equals(albumName))
					albumList.remove(f);
	}
	addTagtoAlbum(tag, albumName);
	files.setAlbumList(albumList);
}
	 public void addTagtoAlbum(String tag, String albumName){
		 jad.addTag(tag, albumName);

	 }
	 public void updateaddCommenttoPicture(String comment, String filePath){
		 for(Picture f:pictureList){
			 if(f.getPath().equals(filePath))
				 pictureList.remove(f);
		 }
			addCommenttoPicture(comment, filePath);
		 files.setPictureList(pictureList);
	 }

	

	 public void addCommenttoPicture(String comment, String filePath){
		 jpd.addComment(comment, filePath);
	 }
	 public void updateAddCommenttoAlbum(String comment, String albumName){
			for(Album f:albumList){
				if(f.getAlbumName().equals(albumName))
						albumList.remove(f);
		}
			addCommenttoAlbum(comment, albumName);
		files.setAlbumList(albumList);
	}


	 public void addCommenttoAlbum(String comment, String filePath){
		 jad.addComment(comment, filePath);
	 }

	 public void updateAddLocationtoPicture(String location, String filePath){
		 for(Picture f:pictureList){
			 if(f.getPath().equals(filePath))
				 pictureList.remove(f);
		 }
			addLocationtoPicture(location, filePath);
		 files.setPictureList(pictureList);
	 }



	 public void addLocationtoPicture(String location, String filePath){
		 jpd.addLocation(location, filePath);
	 }
	 public void updateAddLocationtoAlbum(String location, String albumName){
			for(Album f:albumList){
				if(f.getAlbumName().equals(albumName))
						albumList.remove(f);
		}
			addLocationtoAlbum(location, albumName);
		files.setAlbumList(albumList);
	}


	 public void addLocationtoAlbum(String location, String albumName){
		jad.addLocation(location, albumName);
	 }
	 public void deleteAll(){
		 Query b = em.createQuery("select t from Picture t");
		 List<Picture> allPictures = b.getResultList();
		 for(Picture pic:allPictures){
			 deletePicture(pic.getPath());
		 }
	 }

	 public void updateDeletePictures(String filePath){
		 deletePicture(filePath);
		pictureList = getAllPictures();
		 files.setPictureList(pictureList);
	 }
	 

	 public void deletePicture (String filePath){
		 Picture picture = em.find(Picture.class, filePath);

		 if(picture != null){
			 List<Tag> tags = picture.getTags();
			 for(Tag tag: tags){

				 em.getTransaction().begin();
				 List<Picture> pics =tag.getPictures();
				 pics.remove(picture);
				 tag.setPictures(pics);
				 em.persist(tag);
				 em.getTransaction().commit();	
			 }
			 em.getTransaction().begin();
			 Album album = picture.getAlbum();
			 List<Picture> pics = album.getPictures();
			 pics.remove(picture);
			 album.setPictures(pics);
			 if(album.getPictures().isEmpty()){
				 em.remove(album);
			 }
			 em.persist(album);
			 em.getTransaction().commit();	

			 Location loc = picture.getLocation();
			 em.getTransaction().begin();
			 List<Picture>  picts = loc.getPictures();
			 picts.remove(picture);
			 loc.setPictures(picts);
			 em.persist(loc);
			 em.getTransaction().commit();	

			 Comment com = picture.getComment();
			 em.getTransaction().begin();
			 List<Picture> pictures = com.getPictures();
			 pictures.remove(picture);
			 com.setPicture(pictures);
			 em.persist(com);
			 em.getTransaction().commit();	

			 em.getTransaction().begin();
			 em.remove(picture);
			 em.getTransaction().commit();
		 }


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
	 	public void updateDeleteCommentfromPicture(String filePath){
		 deleteCommentfromPicture(filePath);
			for(Picture f: pictureList){
				if(f.getPath().equals(filePath))
					pictureList.remove(f);
					
			}
			files.setPictureList(pictureList);
	 }
	 

	 public void deleteCommentfromPicture(String filePath){
		jpd.deleteComment(filePath);
	 }
	 public void updateDeleteLocationfromPicture(String filePath){
		deleteLocationfromPicture(filePath);
		for(Picture f: pictureList){
			if(f.getPath().equals(filePath))
				pictureList.remove(f);
				
		}
		files.setPictureList(pictureList);
	}

	
	 public void deleteLocationfromPicture(String filePath){
		 jpd.deleteLocation(filePath);
	 }
	 	public void updateDeleteCommentfromAlbum(String albumName){
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
	 public void updateDeleteLocationfromAlbum(String albumName){
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
}

