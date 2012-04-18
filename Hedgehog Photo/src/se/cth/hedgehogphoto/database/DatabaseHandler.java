package se.cth.hedgehogphoto.database;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import se.cth.hedgehogphoto.FileObject;
import se.cth.hedgehogphoto.ImageObject;


/**
 * 
 * @author Julia
 *
 */


public class DatabaseHandler {
	private static Files files = Files.getInstance();;
	private static List<FileObject> list = files.getList(); 
	private static final String PERSISTENCE_UNIT_NAME = "hedgehogphoto";
	private static EntityManagerFactory factory = factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);;
	static DatabaseHandler dh;

	static EntityManager em = factory.createEntityManager();
	private static int i = 0;

	/**
	 * A method that return all tags in the database as strings.
	 * @return List<String>
	 */
	public static List<String> getTags(){
		Query q = em.createQuery("select t from Tag t");
		try{
			List<Tag> list = q.getResultList();
			List<String> tags = new ArrayList<String>();
			if(list != null){
				for(Tag t: list){
					tags.add(t.getTag().toString());

				}
			}
			return tags;
		}
		catch(Exception e){
			return null;
		}


	}
	/**
	 * return all locations as Strings
	 * @return List<String>
	 */
	public static List<String> getLocations(){

		Query q = em.createQuery("select t from Location t");
		List<Location> LocationsList = q.getResultList();
		List<String> LocationsStringList = new ArrayList<String>();
		for (Location Locations : LocationsList ){
			LocationsStringList.add(Locations.getLocation());
		}
		em.close();
		return LocationsStringList;
	}

	public static void updateSearchPictureNames(String search){
		list = searchPictureNames(search);
		files.setList(list);
	}
	public static List<FileObject> searchPictureNames(String search){

		Query q = em.createQuery("select t from Picture t where t.name=:name");
		q.setParameter("name", search);
		try{
			List<Picture> pictures = q.getResultList();
			List<FileObject> fileObjects = new ArrayList<FileObject>(); 
			for(Picture p:pictures)
				fileObjects.add(makeFileObjectfromPath(p.getPath()));
			return fileObjects;

		}catch(Exception e){
			return null;
		}

	}
	public static void updateSearchPicturesfromDates(String search){
		list = searchPicturesfromDates(search);
		files.setList(list);
	}
	public static List<FileObject> searchPicturesfromDates(String search){

		Query q = em.createQuery("select t from Picture t where t.date=:date");
		q.setParameter("date", search);
		try{
			List<Picture> pictures = q.getResultList();
			List<FileObject> fileObjects = new ArrayList<FileObject>(); 
			for(Picture p:pictures)
				fileObjects.add(makeFileObjectfromPath(p.getPath()));
			return fileObjects;
		}catch(Exception e){
			return null;
		}
	}
	public static void updateSearchAlbumsfromDates(String search){
		list = searchAlbumsfromDates(search);
		files.setList(list);
	}
	public static List<FileObject>  searchAlbumsfromDates(String search){

		Query q = em.createQuery("select t from Album t where t.date=:date");
		q.setParameter("date", search);
		try{
			List<Album> albums = q.getResultList();
			List<FileObject> fileObjects = new ArrayList<FileObject>();
			for(Album album:albums)
				fileObjects.add(makeFileObjectfromAlbumName(album.getName()));
			return fileObjects;
		}catch(Exception e){
			return null;
		}
	}
	public static void updateSearchAlbumNames(String search){
		list =searchAlbumNames(search);
		files.setList(list);
	}
	public static List<FileObject> searchAlbumNames(String search){

		Query q = em.createQuery("select t from Album t where t.albumName=:albumName");
		q.setParameter("albumName", search);
		try{
			List<Album> albums = q.getResultList();
			List<FileObject> fileObjects = new ArrayList<FileObject>();
			for(Album album:albums)
				fileObjects.add(makeFileObjectfromAlbumName(album.getName()));
			return fileObjects;
		}catch(Exception e){
			return null;
		}
	}
	public static void updateSearchPicturesfromComments(String search){
		list = searchPicturesfromComments(search);
		files.setList(list);
	}
	public static List<FileObject> searchPicturesfromComments(String search){
		if(!(search.equals(""))){
		Query q = em.createQuery("select t from Comment t where t.comment=:comment");
		q.setParameter("comment", search);
		try{
			Comment com = (Comment)q.getSingleResult();
		q = em.createQuery("select t from Picture t where t.comment=:comment");
		q.setParameter("comment", com);
		try{
			List<Picture> pictures = q.getResultList();
			List<FileObject> fileObjects = new ArrayList<FileObject>(); 
			for(Picture p:pictures)
				fileObjects.add(makeFileObjectfromPath(p.getPath()));
			return fileObjects;
		}catch(Exception e){

		}
		}
		catch(Exception o){
			
		}
		}
		return null;
	}
	public static void updateSearchAlbumsfromComments(String search){
		list = searchCommentsfromAlbum(search);
		files.setList(list);
	}
	public static List<FileObject> searchCommentsfromAlbum(String search){
		if(!(search.equals(""))){
			Query q = em.createQuery("select t from Comment t where t.comment=:comment");
			q.setParameter("comment", search);
			try{
				Comment com = (Comment)q.getSingleResult();
		q = em.createQuery("select t from Album t where t.comment=:comment");
		q.setParameter("comment", com);
		try{
			List<Album> albums = q.getResultList();
			List<FileObject> fileObjects = new ArrayList<FileObject>();
			for(Album album:albums)
				fileObjects.add(makeFileObjectfromAlbumName(album.getName()));
			return fileObjects;
		}
		catch(Exception e){
		}
			}catch(Exception f){
				
				
			}
		}
		return null;
		
	}
	
	public static void updateSearchPicturesfromTags(String search){
		list =searchPicturesfromTags(search);
		files.setList(list);
	}

	public static List<FileObject> searchPicturesfromTags(String search){
		if(!(search.equals(""))){
		/*Query q = em.createQuery("select t from Tag t where t.tag=:tag");
		q.setParameter("tag", search);
		try{
			Tag tag = (Tag)q.getSingleResult();*/
			Query v = em.createQuery("select t from Picture t");
			try{
			List<Picture> pictures = v.getResultList();
			System.out.println("********************"+pictures+"***************");
			List<Picture> matchingpictures = new ArrayList<Picture>();
			for(Picture p: pictures){
				System.out.print("--------"+p.getTags()+"--------------");
				for(Tag t:  p.getTags()){
					if(t.getTag().equals(search)){
						 matchingpictures.add(p);
					}
					
				}
				
			}
			
			List<FileObject> fileObjects = new ArrayList<FileObject>(); 
			for(Picture p: matchingpictures)
				fileObjects.add(makeFileObjectfromPath(p.getPath()));
			return fileObjects;
		}catch(Exception e){
			
		}
	/*}catch(Exception g){
	}*/
	}
		return null;
		}
	public static void updateSearchPicturefromsLocations(String search){
		list = searchPicturefromsLocations(search);
		files.setList(list);
	}
	public static List<FileObject> searchPicturefromsLocations(String search){
		Query q = em.createQuery("select t from Location t where t.location=:locaion");
		q.setParameter("location", search);
		try{
			Location loc = (Location)q.getSingleResult();
			 q = em.createQuery("select t from Picture t where t.location=:location");
			q.setParameter("location", loc);
			try{
			List<Picture> pictures = q.getResultList();
			List<FileObject> fileObjects = new ArrayList<FileObject>(); 
			for(Picture p:pictures)
				fileObjects.add(makeFileObjectfromPath(p.getPath()));
			return fileObjects;
		}catch(Exception e){
			
		}
	}catch(Exception g){
		
	}
		return null;
		}
	public static void updateAlbumsfromSearchTags(String search){
		list =  searchfromTagsAlbum(search);
		files.setList(list);
	}
	public static List<FileObject> searchfromTagsAlbum(String search){
		Query q = em.createQuery("select t from Tag t where t.tag=:tag");
		q.setParameter("tag", search);
		try{
		Tag tag = (Tag)q.getSingleResult();
		 q = em.createQuery("select t from Album t where t.tag=:tag");
		q.setParameter("tag", tag);
		try{
			List<Album> albums = q.getResultList();
			List<FileObject> fileObjects = new ArrayList<FileObject>();
			for(Album album:albums)
				fileObjects.add(makeFileObjectfromAlbumName(album.getName()));
			return fileObjects;
		}catch(Exception e){
			
		}
		}catch(Exception o){
			
		}
		return null;
	}
	public static void updateSearchAlbumsfromLocations(String search){
		list =  searchAlbumsfromLocations(search);
		files.setList(list);
	}
	public static List<FileObject> searchAlbumsfromLocations(String search){
		Query q = em.createQuery("select t from Location t where t.location=:location");
		q.setParameter("location", search);
		try{
		Location loc = (Location)q.getSingleResult();
		 q = em.createQuery("select t from Album t where t.location=:location");
		q.setParameter("location", loc);
		try{
			List<Album> albums = q.getResultList();
			List<FileObject> fileObjects = new ArrayList<FileObject>();
			for(Album album:albums)
				fileObjects.add(makeFileObjectfromAlbumName(album.getName()));
			return fileObjects;
		}catch(Exception e){
			
		}
		}catch(Exception o){
			
		}
		return null;
	}
	public static void updateAllPictures(){
		List<Picture> pictures = getAllPictures();
		list = new ArrayList<FileObject>();
		for(Picture picture:pictures)
			list.add(makeFileObjectfromPath(picture.getPath()));

		files.setList(list);
	}
	public static List<Picture> getAllPictures(){
		Query p = em.createQuery("select t from Picture t");
		return (List<Picture>)p.getResultList();

	}
	public static List<FileObject> getAllPicturesasFileObject(){
		Query p = em.createQuery("select t from Picture t");
		List<Picture> pictures =  p.getResultList();
		List<FileObject> fileObjects = new ArrayList<FileObject>();
		for(Picture picture:pictures)
			fileObjects.add(makeFileObjectfromPath(picture.getPath()));
		return fileObjects;

		

	}
	public static void updateAllAlbums(){
		List<Album> albums = getAllAlbums();
		list = new ArrayList<FileObject>();
		for(Album album:albums)
			list.add(makeFileObjectfromPath(album.getAlbumName()));

		files.setList(list);
	}
	public static List<Album> getAllAlbums(){
		Query p = em.createQuery("select t from Album t");
		return (List<Album>)p.getResultList();

	}



	public static FileObject makeFileObjectfromPath(String filePath){
		String name = "";
		String date = "";
		String path ="";

		se.cth.hedgehogphoto.LocationObject location = new se.cth.hedgehogphoto.LocationObject("");
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

	public static FileObject makeFileObjectfromAlbumName(String albumName){
		List<String> tags = new ArrayList<String>();
		se.cth.hedgehogphoto.LocationObject location = new se.cth.hedgehogphoto.LocationObject("");
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
			location = new se.cth.hedgehogphoto.LocationObject(loc.getLocation());
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

	public static void updateInsertPicture(ImageObject f){

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

	}
	public static void insertPicture(FileObject f){
		if(f.getFilePath() != null || (!(f.getFilePath().equals("")))){
			Album theAlbum = new Album();
			//long albumID = -1;
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

				
					theAlbum.setName(f.getAlbumName());
				

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

				List<Picture> thePictures = theAlbum.getPicture();
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
					List<Picture> thePictures = theAlbum.getPicture();
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
							List<Picture> ptag= tag.getPicture();
							//List<Album> atag = tag.getAlbum();

							/*if(!(atag.contains(theAlbum)))
								atag.add(theAlbum);
							*/	
							if(!(ptag.contains(pic)))
								ptag.add(pic);

							List<Tag> aTags = theAlbum.getTags();

							/*if(!(aTags.contains(tag)))
								aTags.add(tag);
							*/
							List<Tag> pTags = pic.getTags();

							if(!(pTags.contains(tag)))
								pTags.add(tag);

							tag.setPicture(ptag);
							//tag.setAlbum(atag);

							//theAlbum.setTag(aTags);
							pic.setTag(pTags);
							em.persist(tag);
							em.persist(pic);
							//em.persist(theAlbum);
							em.getTransaction().commit();
						}
						catch(Exception ee){
							if(!(em.getTransaction().isActive())){
								em.getTransaction().begin();
							}
							Tag tag = new Tag();
							tag.setTag(tags.get(i));
							List<Album> ag = new ArrayList<Album>();

							ag.add(theAlbum);
							tag.setAlbum(ag);
							List<Picture> peg = new ArrayList<Picture>();
							peg.add(pic);
							tag.setPicture(peg);
							List<Tag> aTags = theAlbum.getTags();
							if(aTags!=null)
								aTags.add(tag);
							List<Tag> pTags = pic.getTags();
							if(pTags!=null)
								pTags.add(tag);

							theAlbum.setTag(aTags);
							pic.setTag(pTags);

							em.persist(tag);
							em.persist(pic);
							em.persist(theAlbum);
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
							List<Picture> pics = comment.getPicture();
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
						/*Query ca = em.createQuery("select t from Comment t where t.album=:album");
						ca.setParameter("album", theAlbum);
						try{
							Comment comment = (Comment)c.getSingleResult();
							em.getTransaction().begin();
							comment.setComment(f.getComment());	
							theAlbum.setComment(comment);
							em.persist(theAlbum);
							em.persist(comment);
							em.getTransaction().commit();
						}catch(Exception d){				
							em.getTransaction().begin();
							Comment comment = new Comment();

							if(f.getComment() != null ||f.getComment().equals("")){
								comment.setComment(f.getComment());
							}
							List<Album> albums = new ArrayList<Album>();
							albums.add(theAlbum);
							comment.setAlbum(albums);
							theAlbum.setComment(comment);
							em.persist(theAlbum);
							em.persist(comment);
							em.getTransaction().commit();
						}
					}*/
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
						location.setPicture(pics);
						pic.setLocation(location);
						em.persist(pic);
						em.persist(location);
						em.getTransaction().commit();
					}
					/*Query la = em.createQuery("select t from Location t where t.album=:album");
					la.setParameter("album", theAlbum);
					try{

					}catch(Exception b){
						em.getTransaction().begin();
						Location location = new Location();
						if(f.getLocation() != null || f.getLocation().equals("")){
							location.setLatitude((f.getLocation().getLatitude()));
							location.setLongitude(f.getLocation().getLongitude());

						}
						List<Album> albums = new ArrayList<Album>();
						albums.add(theAlbum);
						location.setAlbum(albums);
						theAlbum.setLocation(location);
						em.persist(theAlbum);
						em.persist(location);
						em.getTransaction().commit();
					}
				}*/
				}catch(Exception j){
				}	
			}

		}

	}

	public static void updateAddTagtoPicture(String tag, String filePath){
		for(FileObject f:list){
			if(f.getFilePath().equals(filePath))
					list.remove(f);
	}
	addTagtoPicture(tag, filePath);
	list.add(makeFileObjectfromPath(filePath));
	files.setList(list);
}

	/**
	 * a method that add a tag to a picture
	 * @param tag
	 * @param filePath
	 */
	public static void addTagtoPicture(String tag, String filePath){
		Picture picture = new Picture();

		boolean pictureExist = true;
		Query b = em.createQuery("select t from Picture t where t.path=:path");
		b.setParameter("path", filePath);
		try{
			Picture pic =  (Picture)b.getSingleResult();
			picture = pic; 
		}catch(Exception a){
			pictureExist = false;
		}
		if( pictureExist ){
			Query pt = em.createQuery("select t from Tag t where t.tag=:tag");
			pt.setParameter("tag", tag);
			try{
				Tag tagg = (Tag) pt.getSingleResult();

				List<Picture> pics = tagg.getPicture();
				if(!(pics.contains(picture))){
					em.getTransaction().begin();
					pics.add(picture);
					tagg.setPicture(pics);
					em.persist(tagg);
					em.getTransaction().commit();	
				}
			}catch(Exception a){
				em.getTransaction().begin();
				Tag newTag = new Tag();
				newTag.setTag(tag);
				List<Picture> pictures = new ArrayList<Picture>();
				pictures.add(picture);
				newTag.setPicture(pictures);
				List<Tag> tags =picture.getTags();
				tags.add(newTag);
				picture.setTag(tags);
				em.persist(picture);
				em.persist(newTag);
				em.getTransaction().commit();	
			}

		}

	}
	public static void updateAddTagtoAlbum(String tag, String albumName){
		for(FileObject f:list){
			if(f.getAlbumName().equals(albumName))
					list.remove(f);
	}
	addTagtoAlbum(tag, albumName);
	list.add(makeFileObjectfromAlbumName(albumName));
	files.setList(list);
}
	public static void addTagtoAlbum(String tag, String albumName){
		Album album = new Album();

		boolean pictureExist = true;
		Query b = em.createQuery("select t from Album t where t.albumName=:albumName");
		b.setParameter("albumName", albumName);
		try{
			Album alb =  (Album)b.getSingleResult();
			album = alb; 
		}catch(Exception a){
			pictureExist = false;
		}
		if( pictureExist ){
			Query pt = em.createQuery("select t from Tag t where t.tag=:tag");
			pt.setParameter("tag", tag);
			try{
				Tag tagg = (Tag) pt.getSingleResult();

				List<Album> albums = tagg.getAlbum();
				if(!(albums.contains(album))){
					em.getTransaction().begin();
					albums.add(album);
					tagg.setAlbum(albums);
					em.persist(tagg);
					em.getTransaction().commit();	
				}
			}catch(Exception a){
				em.getTransaction().begin();
				Tag newTag = new Tag();
				newTag.setTag(tag);
				List<Album> albums = new ArrayList<Album>();
				albums.add(album);
				newTag.setAlbum(albums);
				List<Tag> tags =album.getTags();
				tags.add(newTag);
				album.setTag(tags);
				em.persist(album);
				em.persist(newTag);
				em.getTransaction().commit();	
			}

		}

	}
	public static void updateaddCommenttoPicture(String comment, String filePath){
		for(FileObject f:list){
			if(f.getFilePath().equals(filePath))
					list.remove(f);
	}
	addCommenttoPicture(comment, filePath);
	list.add(makeFileObjectfromPath(filePath));
	files.setList(list);
}
	/**
	 * add a comment to a picture
	 * @param comment
	 * @param filePath
	 */
	public static void addCommenttoPicture(String comment, String filePath){
		Picture picture = new Picture();
		Query b = em.createQuery("select t from Picture t where t.path=:path");
		b.setParameter("path", filePath);
		boolean existPicture = true;
		try{
			picture = (Picture) b.getSingleResult();
		}catch(Exception f){
			existPicture = false;
		}
		if(existPicture ) {
			Query bc = em.createQuery("select t from Comment t where t.comment=:comment");
			bc.setParameter("comment",comment);
			try{
				Comment comm = (Comment) bc.getSingleResult();
				em.getTransaction().begin();
				List<Picture> pics = comm.getPicture();

				if((!pics.contains(picture))){
					pics.add(picture);
					comm.setPicture(pics);
					em.persist(picture);
					em.persist(comm);;
					em.getTransaction().commit();
				}


			}catch(Exception f){
				em.getTransaction().begin();
				Comment com = new Comment();
				com.setComment(comment);
				List<Picture> pictures = new ArrayList<Picture>();
				pictures.add(picture);
				com.setPicture(pictures);
				picture.setComment(com);
				em.persist(com);
				em.persist(picture);
				em.getTransaction().commit();
			}
		}
	}
	public static void updateAddCommenttoAlbum(String comment, String albumName){
		for(FileObject f:list){
			if(f.getAlbumName().equals(albumName))
					list.remove(f);
	}
	addCommenttoAlbum(comment, albumName);
	list.add(makeFileObjectfromAlbumName(albumName));
	files.setList(list);
}
	public static void addCommenttoAlbum(String comment, String filePath){
		Album album= new Album();
		Query b = em.createQuery("select t from Album t where t.albumName=:albumName");
		b.setParameter("albumName", filePath);
		boolean existPicture = true;
		try{
			album = (Album) b.getSingleResult();
		}catch(Exception f){
			existPicture = false;
		}
		if(existPicture ) {
			Query bc = em.createQuery("select t from Comment t where t.comment=:comment");
			bc.setParameter("comment",comment);
			try{
				Comment comm = (Comment) bc.getSingleResult();
				em.getTransaction().begin();
				List<Album> albums = comm.getAlbum();
				if((!albums.contains(album))){
					albums.add(album);
					comm.setAlbum(albums);
					em.persist(album);
					em.persist(comm);;
					em.getTransaction().commit();
				}


			}catch(Exception f){
				em.getTransaction().begin();
				Comment com = new Comment();
				com.setComment(comment);
				List<Album> albums = new ArrayList<Album>();
				albums.add(album);
				com.setAlbum(albums);
				album.setComment(com);
				em.persist(com);
				em.persist(album);
				em.getTransaction().commit();
			}
		}
	}

	public static void updateAddLocationtoPicture(String location, String filePath){
		for(FileObject f:list){
			if(f.getFilePath().equals(filePath))
					list.remove(f);
	}
	addLocationtoPicture(location, filePath);
	list.add(makeFileObjectfromPath(filePath));
	files.setList(list);
}
	/**
	 * 
	 * @param location
	 * @param filePath
	 */
	public static void addLocationtoPicture(String location, String filePath){
		//REMOVA GAMMAL LOCATION
		Picture picture = new Picture();
		Query b = em.createQuery("select t from Picture t where t.path=:path");
		b.setParameter("path", filePath);
		boolean existPicture = true;
		try{
			picture = (Picture) b.getSingleResult();
		}catch(Exception f){
			existPicture = false;
		}
		if(existPicture ) {

			Query bc = em.createQuery("select t from Location t where t.location=:location");
			bc.setParameter("location",location);
			try{
				Location loc= (Location) bc.getSingleResult();

				List<Picture> pics =loc.getPicture();

				if((!pics.contains(picture))){

					em.getTransaction().begin();
					pics.add(picture);
					loc.setPicture(pics);
					picture.setLocation(loc);
					em.persist(picture);
					em.persist(loc);
					em.getTransaction().commit();
				}


			}catch(Exception f){

				em.getTransaction().begin();
				Location loc= new Location();
				loc.setLocation(location);
				List<Picture> pictures = new ArrayList<Picture>();
				pictures.add(picture);
				loc.setPicture(pictures);
				picture.setLocation(loc);
				em.persist(loc);
				em.persist(picture);
				em.getTransaction().commit();
			}
		}

	}
	public static void updateAddLocationtoAlbum(String location, String albumName){
		for(FileObject f:list){
			if(f.getAlbumName().equals(albumName))
					list.remove(f);
	}
	addLocationtoAlbum(location, albumName);
	list.add(makeFileObjectfromAlbumName(albumName));
	files.setList(list);
}
	public static void addLocationtoAlbum(String location, String albumName){
		//REMOVA GAMMAL LOCATION
		Album album = new Album();
		Query b = em.createQuery("select t from Album t where t.albumName=:albumName");
		b.setParameter("albumName", albumName);
		boolean existPicture = true;
		try{
			album = (Album) b.getSingleResult();
		}catch(Exception f){
			existPicture = false;
		}
		if(existPicture ) {
			Query bc = em.createQuery("select t from Location t where t.location=:location");
			bc.setParameter("location",location);
			try{
				Location loc= (Location) bc.getSingleResult();

				List<Album> albums =loc.getAlbum();

				if((!albums.contains(album))){

					em.getTransaction().begin();
					albums.add(album);
					loc.setAlbum(albums);
					album .setLocation(loc);
					em.persist(album );
					em.persist(loc);
					em.getTransaction().commit();
				}


			}catch(Exception f){

				em.getTransaction().begin();
				Location loc= new Location();
				loc.setLocation(location);
				List<Album> albums = new ArrayList<Album>();
				albums.add(album);
				loc.setAlbum(albums);
				album .setLocation(loc);
				em.persist(loc);
				em.persist(album );
				em.getTransaction().commit();
			}
		}

	}
	// 

	public static void deleteAll(){
		Query b = em.createQuery("select t from Picture t");
		List<Picture> allPictures = b.getResultList();
		for(Picture pic:allPictures){
			deletePicture(pic.getPath());
		}
	}
	
	public static void updateDeletePictures(String filePath){
		deletePicture(filePath);
		List<Picture> pictures = getAllPictures();
		list = new ArrayList<FileObject>();
		for(Picture picture:pictures)
			list.add(makeFileObjectfromPath(picture.getPath()));

		files.setList(list);
	}
	/**
	 * Delete a picture
	 * @param filePath
	 */

	public static void deletePicture (String filePath){
		Picture picture = em.find(Picture.class, filePath);

		if(picture != null){
			List<Tag> tags = picture.getTags();
			for(Tag tag: tags){

				em.getTransaction().begin();
				List<Picture> pics =tag.getPicture();
				pics.remove(picture);
				tag.setPicture(pics);
				em.persist(tag);
				em.getTransaction().commit();	
			}
			em.getTransaction().begin();
			Album album = picture.getAlbum();
			List<Picture> pics = album.getPicture();
			pics.remove(picture);
			album.setPictures(pics);
			em.persist(album);
			em.getTransaction().commit();	

			Location loc = picture.getLocation();
			em.getTransaction().begin();
			List<Picture>  picts = loc.getPicture();
			picts.remove(picture);
			loc.setPicture(picts);
			em.persist(loc);
			em.getTransaction().commit();	

			Comment com = picture.getComment();
			em.getTransaction().begin();
			List<Picture> pictures = com.getPicture();
			pictures.remove(picture);
			com.setPicture(pictures);
			em.persist(com);
			em.getTransaction().commit();	

			em.getTransaction().begin();
			em.remove(picture);
			em.getTransaction().commit();
		}


	}
	
	public static void updateDeleteTagsfromPictures(String filePath){
		deleteTagsfromPicture(filePath);
		for(FileObject f: list){
			if(f.getFilePath().equals(filePath))
				list.remove(f);
		}
		list.add(makeFileObjectfromPath(filePath));
		files.setList(list);
	}
	/**
	 * delete all tags from a picture
	 * @param filePath
	 */
	public static void deleteTagsfromPicture(String filePath){

		Picture picture = em.find(Picture.class, filePath);
		if(picture != null){
			List<Tag> tags = picture.getTags();
			for(Tag tag: tags){

				em.getTransaction().begin();
				List<Picture> pics =tag.getPicture();
				pics.remove(picture);
				tag.setPicture(pics);
				em.persist(tag);
				em.getTransaction().commit();	
			}
		}
	}
	public static void updateDeletePicturefromAlbum(String filePath){
		 deletePicturefromAlbum(filePath);
		for(FileObject f: list){
			if(f.getFilePath().equals(filePath))
				list.remove(f);
		}
		list.add(makeFileObjectfromPath(filePath));
		files.setList(list);
	}
	/*
	 * Delete a picture from an album
	 */	
	public static void deletePicturefromAlbum(String filePath){
		Picture picture = em.find(Picture.class, filePath);

		if(picture != null){
			em.getTransaction().begin();
			Album album = picture.getAlbum();
			List<Picture> pics = album.getPicture();
			pics.remove(picture);
			album.setPictures(pics);
			em.persist(album);
			em.getTransaction().commit();	
		}
	}
	public static void updateDeleteCommentfromPicture(String filePath){
		 deleteCommentfromPicture(filePath);
		for(FileObject f: list){
			if(f.getFilePath().equals(filePath))
				list.remove(f);
		}
		list.add(makeFileObjectfromPath(filePath));
		files.setList(list);
	}
	
	/**
	 * Delete a pictures comment
	 */
	public static void deleteCommentfromPicture(String filePath){
		Picture picture = em.find(Picture.class, filePath);
		if(picture != null){
			Comment com = picture.getComment();
			em.getTransaction().begin();
			List<Picture> pictures = com.getPicture();
			pictures.remove(picture);
			com.setPicture(pictures);
			em.persist(com);
			em.getTransaction().commit();	
		}
	}
	public static void updateDeleteLocationfromPicture(String filePath){
		deleteLocationfromPicture(filePath);
		for(FileObject f: list){
			if(f.getFilePath().equals(filePath))
				list.remove(f);
		}
		list.add(makeFileObjectfromPath(filePath));
		files.setList(list);
	}
	
	/**
	 * Delete a pictures location
	 * @param filePath
	 */
	public static void deleteLocationfromPicture(String filePath){
		Picture picture = em.find(Picture.class, filePath);
		if(picture != null){
			Location loc = picture.getLocation();
			em.getTransaction().begin();
			List<Picture>  picts = loc.getPicture();
			picts.remove(picture);
			loc.setPicture(picts);
			em.persist(loc);
			em.getTransaction().commit();	
		}
	}
	public static void updateDeleteCommentfromAlbum(String albumName){
		 deleteCommentfromAlbum(albumName);
		for(FileObject f: list){
			if(f.getAlbumName().equals(albumName))
				list.remove(f);
		}
		list.add(makeFileObjectfromAlbumName(albumName));
		files.setList(list);
	}
	
	/**
	 * Delete a pictures comment
	 */
	public static void deleteCommentfromAlbum(String albumName){
		Album album = em.find(Album.class, albumName);
		if(album != null){
			Comment com = album.getComment();
			em.getTransaction().begin();
			List<Album> albums = com.getAlbum();
			albums.remove(album);
			com.setAlbum(albums);
			em.persist(com);
			em.getTransaction().commit();	
		}
	}
	public static void updateDeleteLocationfromAlbum(String albumName){
		deleteLocationfromAlbum(albumName);
		for(FileObject f: list){
			if(f.getAlbumName().equals(albumName))
				list.remove(f);
		}
		list.add(makeFileObjectfromAlbumName(albumName));
		files.setList(list);
	}
	
	/**
	 * Delete a pictures location
	 * @param filePath
	 */
	public static void deleteLocationfromAlbum(String albumName){
		Album album = em.find(Album.class, albumName);
		if(album!= null){
			Location loc = album.getLocation();
			em.getTransaction().begin();
			List<Album> albums = loc.getAlbum();
			albums.remove(album);
			loc.setAlbum(albums);
			em.persist(loc);
			em.getTransaction().commit();	
		}
	}
	public static void updateDeleteTagsfromAlbum(String albumName){
		deleteTagsfromAlbum(albumName);
		for(FileObject f: list){
			if(f.getAlbumName().equals(albumName))
				list.remove(f);
		}
		list.add(makeFileObjectfromAlbumName(albumName));
		files.setList(list);
	}
	/**
	 * delete all tags from a picture
	 * @param filePath
	 */
	public static void deleteTagsfromAlbum(String albumName){
		Album album = em.find(Album.class, albumName);
		if(album!= null){
			List<Tag> tags = album.getTags();
			for(Tag tag: tags){

				em.getTransaction().begin();
				List<Album> albums =tag.getAlbum();
				albums .remove(album);
				tag.setAlbum(albums);
				em.persist(tag);
				em.getTransaction().commit();	
			}
		}
	}
}

