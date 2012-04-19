package se.cth.hedgehogphoto.database;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class PictureHandler {
	private static final String PERSISTENCE_UNIT_NAME = "hedgehogphoto";

	private static EntityManagerFactory factory = factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	private static EntityManager em = factory.createEntityManager();


	
	public static List<Picture> searchfromComments(String search){
		if(!(search.equals(""))){
		Query q = em.createQuery("select t from Comment t where t.comment=:comment");
		q.setParameter("comment", search);
		try{
			Comment com = (Comment)q.getSingleResult();
		q = em.createQuery("select t from Picture t where t.comment=:comment");
		q.setParameter("comment", com);
		try{
			return (List<Picture> )q.getResultList();
		}catch(Exception d){
		}
		}catch(Exception e){
		}		
		}
		return null;
}
/*	public static Picture makePicture(FileObject f, Album theAlbum){
		em.getTransaction().begin();
		
		Picture pic = new Picture();
		pic.setPath(f.getFilePath());	
			pic.setDate(f.getDate());		
		if(f.getFileName() != null ||(!f.getFileName().equals("")))
			pic.setName(f.getFileName());
		if(theAlbum != null){
			
			List<Picture> thePictures = theAlbum.getPicture();
			if(!(thePictures.contains(pic)))
			thePictures.add(pic);
			theAlbum.setPictures(thePictures);
			pic.setAlbum(theAlbum);
			em.persist(theAlbum);
		}
			em.persist(pic);
			em.getTransaction().commit();	
			return pic;
	
	}
	public static void changePicture(Picture pic,FileObject f, Album theAlbum){
		em.getTransaction().begin();
		pic.setName(f.getFileName());
		pic.setAlbum(theAlbum);
		pic.setDate(f.getDate());
		List<Picture> thePictures = theAlbum.getPicture();
		if(!(thePictures.contains(pic)))
		thePictures.add(pic);
		theAlbum.setPictures(thePictures);
		em.persist(theAlbum);
		em.persist(pic);
		em.getTransaction().commit();
	}*/
	public static List<Picture> searchfromNames(String search){
	Query q = em.createQuery("select t from Picture t where t.name=:name");
	q.setParameter("name", search);
	try{
		return (List<Picture>)q.getResultList();
}
	catch(Exception d){
	}
	return null;
}
	public static List<Picture> searchfromDates(String search){

		Query q = em.createQuery("select t from Picture t where t.date=:date");
		q.setParameter("date", search);
		try{
			return (List<Picture>)q.getResultList();
		}catch(Exception e){
			return null;
		}
}

	public static List<Picture> searchfromTags(String search){
		if(!(search.equals(""))){
			Query v = em.createQuery("select t from Picture t");
			try{
			List<Picture> pictures = v.getResultList();
			
			List<Picture> matchingPictures = new ArrayList<Picture>();
			for(Picture p: pictures){
				for(Tag t:  p.getTags()){
					if(t.getTag().equals(search)){
						 matchingPictures.add(p);
					}
					
				}
				
			}
			return matchingPictures;
			
		}catch(Exception e){
			
		}

	}
		return null;
		}
	public static List<Picture> searchfromLocations(String search){
		Query q = em.createQuery("select t from Location t where t.location=:locaion");
		q.setParameter("location", search);
		try{
			Location loc = (Location)q.getSingleResult();
			 q = em.createQuery("select t from Picture t where t.location=:location");
			q.setParameter("location", loc);
			try{
			return (List<Picture>)q.getResultList();
		}catch(Exception e){
			
		}
	}catch(Exception g){
		
	}
		return null;
		}
	public static Picture getfromPath(String search){
		Query q = em.createQuery("select t from Picture t where t.path=:path");
		q.setParameter("path", search);
		try{
			return (Picture)q.getSingleResult();
	}
		catch(Exception d){
			return null;
		}

	}
	public static void addTag(String tag, String filePath){
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
	public static void addComment(String comment, String filePath){
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
		public static void addLocation(String location, String filePath){
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
		public static void deleteTags(String filePath){

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
		public static void deleteComment(String filePath){
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
		public static void deleteLocation(String filePath){
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
	/*	public static Picture dothis(FileObject f, Album theAlbum){
		Picture pic = new Picture();
		boolean pictureExist = false;
		Query t = em.createQuery("select t from Picture t where t.path=:path");
		t.setParameter("path", f.getFilePath());
		try{
			pic=  (Picture)t.getSingleResult();

			em.getTransaction().begin();
			
			if(!(f.getDate().equals("")))
			pic.setDate(f.getDate());
			if(!(theAlbum==null)){
		
			List<Picture> thePictures = theAlbum.getPicture();
			if(!(thePictures.contains(pic)))
				thePictures.add(pic);
			theAlbum.setPictures(thePictures);
			pic.setAlbum(theAlbum);
			em.persist(theAlbum);
			}
		
			em.persist(pic);
			
			em.getTransaction().commit();
			return pic;

		}
		catch(Exception e){
			em.getTransaction().begin();
			pic = new Picture();
			pic.setPath(f.getFilePath());	
				pic.setDate(f.getDate());
			if(f.getFileName() != null ||(!f.getFileName().equals(""))){
				pic.setName(f.getFileName());
				if(!(theAlbum==null)){
			
				List<Picture> thePictures = theAlbum.getPicture();
				thePictures.add(pic);
				theAlbum.setPictures(thePictures);
				pic.setAlbum(theAlbum);
				em.persist(theAlbum);
				}
				em.persist(pic);
				em.getTransaction().commit();
				return pic;
			}
		}return null;
	}*/
}