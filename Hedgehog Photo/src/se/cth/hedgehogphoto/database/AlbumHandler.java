package se.cth.hedgehogphoto.database;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import se.cth.hedgehogphoto.FileObject;

public class AlbumHandler {
	private static final String PERSISTENCE_UNIT_NAME = "hedgehogphoto";

	private static EntityManagerFactory factory = factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	private static EntityManager em = factory.createEntityManager();


	public static List<Album>  searchfromDates(String search){

		Query q = em.createQuery("select t from Album t where t.date=:date");
		q.setParameter("date", search);
		try{
			return (List<Album>)q.getResultList();
		}catch(Exception e){
			return null;
		}
	}
	public static List<Album>  searchfromNames(String search){
		Query q = em.createQuery("select t from Album t where t.albumName=:albumName");
		q.setParameter("albumName", search);
		try{
			return (List<Album>)q.getResultList();
		}catch(Exception e){
			return null;
		}

	}
	
	/*public static Album makeAlbum(FileObject f){
	em.getTransaction().begin();
	Album theAlbum = new Album();	
	theAlbum.setAlbumName(f.getAlbumName());
	theAlbum.setCoverPath(f.getFilePath());
	em.persist(theAlbum);
	em.getTransaction().commit();
	return theAlbum;
	}
	public static void changeAlbum(Album theAlbum,FileObject f){
		em.getTransaction().begin();	
		if(theAlbum.getCoverPath().equals("")|| theAlbum.getCoverPath()==null)
			theAlbum.setCoverPath(f.getFilePath());
		em.persist(theAlbum);
		em.getTransaction().commit();
	}*/
	public static List<Album> searchfromComments(String search){
		if(!(search.equals(""))){
			Query q = em.createQuery("select t from Comment t where t.comment=:comment");
			q.setParameter("comment", search);
			try{
				Comment com = (Comment)q.getSingleResult();
				q = em.createQuery("select t from Album t where t.comment=:comment");
				q.setParameter("comment", com);
				try{
					return (List<Album>)q.getResultList();
				}
				catch(Exception e){
				}
			}catch(Exception f){


			}
		}
		return null;

	}
	public static List<Album> searchfromTags(String search){
		if(!(search.equals(""))){
			Query q = em.createQuery("select t from Tag t where t.tag=:tag");
			q.setParameter("tag", search);
			try{
				Tag tag = (Tag)q.getSingleResult();
				q = em.createQuery("select t from Album t where t.tag=:tag");
				q.setParameter("tag", tag);
				try{
					return (List<Album>)q.getResultList();
				}catch(Exception e){

				}
			}catch(Exception o){
			}
		}
		return null;
	}
	public static List<Album> searchfromLocations(String search){
		if(!(search.equals(""))){
			Query q = em.createQuery("select t from Location t where t.location=:location");
			q.setParameter("location", search);
			try{
				Location loc = (Location)q.getSingleResult();
				q = em.createQuery("select t from Album t where t.location=:location");
				q.setParameter("location", loc);
				try{
					return (List<Album>)q.getResultList();
				}catch(Exception e){

				}
			}catch(Exception o){
			}
		}
		return null;
	}

public static Album getfromNames(String search){
	Query q = em.createQuery("select t from Album t where t.albumName=:albumName");
	q.setParameter("albumName", search);
	try{
		return (Album)q.getSingleResult();
	}catch(Exception e){
		return null;
	}

}
public static void addTag(String tag, String albumName){
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
public static void addComment(String comment, String filePath){
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
public static void addLocation(String location, String albumName){
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
public static void deletePicture(String filePath){
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
public static void deleteComment(String albumName){
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
public static void deleteLocation(String albumName){
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
public static void deleteTags(String albumName){
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
/*public static Album dothis(FileObject f){
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
return theAlbum;
}
return null;
}*/
}