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
	private static final String PERSISTENCE_UNIT_NAME = "hedgehogphoto";
	private static EntityManagerFactory factory;
	static DatabaseHandler dh;
	EntityManager em;


	public static List<String> getTags(){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		Query q = em.createQuery("select t from Tag t");
		List<Tag> list = q.getResultList();
		List<String> tags = new ArrayList();
		for (int i = 0; i < list.size(); i++ ){
			List<String> tag = list.get(i).getTags();
			for (int j = 0; j < tag.size(); j++ ){
				tags.add(tag.get(j));
			}

		}

		return tags;
	}
	public static List<String> getLocations(){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();

		Query q = em.createQuery("select t from Location t");
		List<Location> LocationsList = q.getResultList();
		List<String> LocationsStringList = new ArrayList();
		for (Location Locations : LocationsList ){
			LocationsStringList.add(Locations.getLocation());
		}
		em.close();
		return LocationsStringList;
	}
	public static List<FileObject> searchNames(String search){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();

		Query q = em.createQuery("select t from Picture t");
		List<Picture> list = q.getResultList();
		List<String> paths = new ArrayList();
		for (int i =0; i< list.size(); i++) {
			if(search == list.get(i).getName())
				paths.add(list.get(i).getPath());
		}
		List<FileObject> fileObjects = new ArrayList();
		for (int i =0; i< paths.size(); i++) {
			fileObjects.add( makeFileObjectfromPath(paths.get(i),em));
		}
		em.close();
		return fileObjects;

	}
	public static List<FileObject> searchDates(String search){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();

		Query q = em.createQuery("select t from Picture t");
		List<Picture> list = q.getResultList();
		//List<String> paths = getPaths("Picture");
		List<FileObject> fileObjects = new ArrayList();
		for (int i =0; i< list.size(); i++) {
			if(search == list.get(i).getDate())
				fileObjects.add( makeFileObjectfromPath(list.get(i).getPath(),em));
		}
		return fileObjects;

	}
	public static List<FileObject> searchAlbumNames(String search){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();

		Query q = em.createQuery("select t from Album t");
		List<Album> list = q.getResultList();
		//List<String> paths = getPaths("Picture");

		Query t = em.createQuery("select t from Album t");
		List<Picture> piclist = t.getResultList();
		List<FileObject> fileObjects = new ArrayList();
		for (int i =0; i< list.size(); i++) {
			if(list.get(i).getName().equals(search)){
				for(Picture pic: piclist){
					if(pic.getAlbum_ID() == list.get(i).getAlbumID())
						
						fileObjects.add( makeFileObjectfromPath(pic.getPath(),em));
				}
			}
		}
		return fileObjects;

	}
	public static List<FileObject> searchComments(String search){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();

		Query q = em.createQuery("select t from Comment t");
		List<Comment> list = q.getResultList();
		//List<String> paths = getPaths("Picture");
		List<FileObject> fileObjects = new ArrayList();
		for (int i =0; i< list.size(); i++) {
			if(list.get(i).getComment().equals(search))
				fileObjects.add( makeFileObjectfromPath(list.get(i).getPath(),em));
		}
		return fileObjects;

	}
	public static List<FileObject> searchTags(String search){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();

		Query q = em.createQuery("select t from Tag t");
		List<Tag> list = q.getResultList();
		List<String> paths = new ArrayList();
		for(int i=0; i <list.size();i++){
			//if(!(paths.contains(list.get(i).getTags())));
			for(int j = 0; j < list.get(i).getTags().size(); i++)
				if(list.get(i).getTags().get(j).equals(search)){
					paths.add(list.get(i).getPath());
				}
		}
		//List<String> paths = getPaths("Picture");
		List<FileObject> fileObjects = new ArrayList();
		for (int i =0; i< paths.size(); i++) {
			fileObjects.add( makeFileObjectfromPath(paths.get(i),em));
		}
		return fileObjects;

	}
	/*public static  List<String> getPaths(String object,EntityManager em){
		Query q = em.createQuery("SELECT e FROM"+object+ "e");
		List<Picture> list =	q.getResultList(); //??
		List<String> paths = new ArrayList();
		for(int i = 0; i < list.size(); i++){
			paths.add(list.get(i).getPath());
		}
		return paths;
	}*/

	public static  FileObject makeFileObjectfromPath(String filePath,EntityManager em){

		Query p = em.createQuery("select t from Picture t");
		List<Picture> list = p.getResultList();
		String name = "";
		String date = "";
		long album_ID = 0;
		for(Picture pic: list){
			if(pic.getPath().equals(filePath)){
				name=pic.getName();
				date=pic.getDate();
				album_ID = pic.getAlbum_ID();
				
			}
		}

		Query a = em.createQuery("select t from Album t");
		List<Album> alist = a.getResultList();
		String coverPath= "";
		String albumName = "";
		//for(Picture pic: list){
			for(Album alb: alist){
				System.out.println("LOOP");
				if(alb.getAlbumID() == (album_ID)){
					System.out.println("Albumid 1: " + alb.getAlbumID());
					System.out.println("Albumid 2: " + album_ID);
					System.out.println("NAME: " + alb.getName());
					System.out.println("CoverPaht: " + alb.getCoverPath());
					albumName = alb.getName();
					coverPath = alb.getCoverPath();
				}
		
			
		}
		Query t = em.createQuery("select t from Tag t");
		List<Tag> tlist = t.getResultList();
		List<String> tags = new ArrayList();
		for(Tag tag: tlist){
			if(tag.getPath().equals(filePath)){
				List<String> taglist = tag.getTags();
				for(int i =0; i<taglist.size();i++){
					tags.add(taglist.get(i));
				}

			}
		}

		Query l = em.createQuery("select t from Location t");
		List<Location> llist = l.getResultList();
		String locations = "";
		for(Location location: llist){
			if(location.getPath().equals(filePath)){
				locations=location.getLocation();
				break;
			}
		}
		Query c = em.createQuery("select t from Comment t");
		List<Comment> clist = c.getResultList();
		String comment = "";
		for(Comment com: clist){
			if(com.getPath().equals(filePath)){
				comment=com.getComment();
				break;
			}
		}

		FileObject f = new ImageObject(); 
		f.setImagePath(filePath);
		f.setComment(comment);
		f.setDate(date);
		f.setCoverPath(coverPath);
		f.setTags(tags);
		f.setLocation(locations);
		f.setImageName(name);
		//System.out.print("aöbumna" + albumName + "slut");
		f.setAlbumName(albumName);
		System.out.println("Setting album name: " + f.getAlbumName());
		return f;
	}



	public static void insert(FileObject f){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		// KOLL S� ATT det inte finnns en med samma filepath? 
		Query t = em.createQuery("select t from Picture t");
		List<Picture> pics =  t.getResultList();
		long albumid = 0;
		boolean existAlready = false; 
		for(Picture pic: pics){
			System.out.print(pic.getPath());
			if(pic.getPath().equals(f.getImagePath())){
				existAlready = true;
				albumid = pic.getAlbum_ID();
				break;
			}
		}


		if(f.getImagePath() != null || (!(f.getImagePath().equals("")))){
		
			if(!(existAlready)){

				
			
				em.getTransaction().begin();
				Tag tag = new Tag();
				tag.setPath(f.getImagePath());
				List <String> tags = f.getTags();
				if(f.getTags() != null){ 
					for(int i = 0; i < tags.size(); i++){
						tag.setTag(tags.get(i));
					} 
				}
				em.persist(tag);
				em.getTransaction().commit();


				em.getTransaction().begin();
				Comment comment = new Comment();
				comment.setPath(f.getImagePath());
				if(f.getComment() != null ||f.getComment().equals("")){
					comment.setComment(f.getComment());
				}
				em.persist(comment);
				em.getTransaction().commit();

				em.getTransaction().begin();
				Location location = new Location();
				location.setPath(f.getImagePath());
				if(f.getLocation() != null || f.getLocation().equals("")){
					location.setLocation(f.getLocation());
				}
				em.persist(location);
				em.getTransaction().commit();
			}
		
				Query a = em.createQuery("select t from Album t");
				List<Album> albums=  a.getResultList();
				boolean albumExistAlready = false;
				Long aid = (long) 0;
				if(aid != null || aid != 0){
				for(Album album: albums){
					if(album.getAlbumID().equals(albumid)){
						albumExistAlready = true; 
						 Album al= em.find(Album.class, albumid);
						  
						  em.getTransaction().begin();
							if(f.getAlbumName() == null || (f.getAlbumName().equals(""))){
								al.setName("Nytt Album");
								System.out.println("NYTT ALBUM");
							}else{
								al.setName(f.getAlbumName());
								System.out.println("Sätter album:" + f.getAlbumName());
							
							}
							
							al.setCoverPath(f.getCoverPath());
						  em.getTransaction().commit();
						  
						//aid = album.getAlbumID();
						System.out.println("sätter aid: " + aid);
						break;
					}
				
				else{
					for(Picture pic: pics){
						if(f.getCoverPath().equals(album.getCoverPath())){
							em.getTransaction().begin();
							Album albumss = new Album();

							if(f.getAlbumName() == null || (f.getAlbumName().equals(""))){
								albumss.setName("Nytt Album");
							}else{
								albumss.setName(f.getAlbumName());
							
							}

							albumss.setCoverPath(f.getCoverPath());
							
						
							
							em.persist(albumss);
							em.getTransaction().commit();
							
						}
					}
				}
				}
			
				if(!(albumExistAlready)){
					em.getTransaction().begin();
					Album album = new Album();

					if(f.getAlbumName() == null || (f.getAlbumName().equals(""))){
						album.setName("Nytt Album");
					}else{
						album.setName(f.getAlbumName());
					
					}
					/*if(f.getCoverPath() == null || (f.getCoverPath().equals(""))){
						album.setCoverPath();
					}else{*/
						album.setCoverPath(f.getCoverPath());
					
					
					
					
				
					System.out.print("MAJSAN");
					em.persist(album);
					em.getTransaction().commit();
				
				}
					/*else{
						pic.setAlbum_ID(aid);
					}*/
				if(!(existAlready)){
			em.getTransaction().begin();
			Picture pic = new Picture();
			pic.setPath(f.getImagePath());
			if(aid != 0){
				pic.setAlbum_ID(aid);
			}else{
			for(Album al: albums){
				if(al.getCoverPath().equals(f.getCoverPath())){
					pic.setAlbum_ID(al.getAlbumID());
				}
			}
			
			if(f.getDate() != "")
				pic.setDate(f.getDate());
			
			if(f.getImageName() != null ||f.getImageName().equals(""))
				pic.setName(f.getImageName());

			em.persist(pic);
			em.getTransaction().commit();
				}

				}
				}
		}
		
	}

	public static void addTag(String tags, String filePath){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		Query b = em.createQuery("select t from Picture t");
		List<Picture> pic =  b.getResultList();
		
		boolean existAlready = false;

		for(Picture picts: pic){
			if(picts.getPath().equals(filePath)){
				existAlready = true;
				break;
			}
		}
		
	/*	if(!(existAlready)){
		em.getTransaction().begin();
		Tag todo = new Tag();
		todo.setPath(filePath);
		todo.setTag(tags);
		em.persist(todo);
		em.getTransaction().commit();	
		em.close();
		}
		else{*/
			if(existAlready){
			  Tag tagg = em.find(Tag.class, filePath);
			  
			  em.getTransaction().begin();
			
			  tagg.setTag(tags);
			  
			  em.getTransaction().commit();
			 
		}
			
			 Tag tagg = em.find(Tag.class, filePath);
			 List<String> tagss = tagg.getTags();
 			 for(String tag: tagss){
 				 System.out.print(tag);
 			 }
	}
	public static void addComment(String comment, String filePath){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		///KOLL ifall det finns redan ==> ej adda utan uppdatera
		Query b = em.createQuery("select t from Picture t");
		List<Picture> pic =  b.getResultList();
		Picture picu = new Picture();
		boolean existAlready = false;

		for(Picture picts: pic){
			if(picts.getPath().equals(filePath)){
				existAlready = true;
				picu = picts;
				break;
			}
		}
	/*	if(!(existAlready)){
			em.getTransaction().begin();
			Comment com = new Comment();
			com.setPath(filePath);
			com.setComment(comment);
			em.persist(comment);
			em.getTransaction().commit();	
		}
		else{
			*/
		if(existAlready){
			  Comment comm = em.find(Comment.class, filePath);
			  
			  em.getTransaction().begin();
			  comm.setComment(comment);
			  em.getTransaction().commit();
			  
		}
		
		 
	}

	/*public static List<Comment> getComments(){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		///KOLL ifall det finns redan ==> ej adda utan uppdatera
		Query b = em.createQuery("select t from Comment t");
		List<Comment> comment =  b.getResultList();
		return comment;
	}*/
	public static void addLocation(String location, String filePath){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		Query b = em.createQuery("select t from Picture t");
		List<Picture> pic =  b.getResultList();
		
		boolean existAlready = false;

		for(Picture picts: pic){
			if(picts.getPath().equals(filePath)){
				existAlready = true;
				break;
			}
		}
		
	/*	if(!(existAlready)){
		em.getTransaction().begin();
		Location todo = new Location();
		todo.setPath(filePath);
		todo.setLocation(location);
		em.persist(todo);
		em.getTransaction().commit();	
		}
		else{*/
		if(existAlready){
			Location loc = em.find(Location.class, filePath);
			  
			  em.getTransaction().begin();
			  loc.setLocation(location);
			  em.getTransaction().commit();
			  System.out.print(loc);
		}

	}

	/**
	 * remove the an object from the database
	 * @param FileObject f
	 */
	public static void removeFileObject(FileObject f){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		Query t = em.createQuery("select t from Picture t");
		List<Picture> pics =  t.getResultList();
		boolean existAlready = false; 
		Picture p = new Picture();
		for(Picture pic: pics){
			if(pic.getPath().equals(f.getImagePath())){
				existAlready = true;
				p = pic;
			}
		}
		System.out.print(existAlready);
		if(existAlready){
			 //Query query = em.createQuery( "DELETE c FROM Picture c");
			// List<Pictures> pics = query.getResultList()
			em.getTransaction().begin();
				//	 int deletedCount = em.createQuery("delete t from Picture t").executeUpdate();
			 Query query = em.createQuery("DELETE FROM Picture c where c.path=:p");
			 String pa = f.getImagePath();
	
					 int deletedCount = query.setParameter("p", f.getImagePath()).executeUpdate();	
					/* Query q = em.createQuery("DELETE FROM Subscription s WHERE s.subscriptionDate < :today");
					 q.setParameter("today", new Date());
					 int deleted = q.executeUpdate();*/
			em.getTransaction().commit();
					 // int pic = query.setParameter(f.getImagePath(),Picture.class).executeUpdate();
			// query.set
		//	Picture userx = (Picture) q.getSingleResult();
		/*	System.out.print(userx);
			em.getTransaction().begin();
			em.remove(userx); 
			em.getTransaction().commit();
			//Picture pict = userx;*/
		}
		checkifexist(f.getImagePath());

	}
	
	/**
	 * test method.
	 * @param path
	 * @return
	 */
	public static boolean checkifexist(String path){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		Query b = em.createQuery("select t from Picture t");
		List<Picture> pic =  b.getResultList();

		for(Picture picts: pic){
			if(picts.getPath().equals(path)){
				System.out.print("nu blev det fel");
				return true;
			}
		}
		return false;
	}
	
	public static void deleteAll(){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		 int deletedCount = em.createQuery("delete from Picture").executeUpdate();
		 deletedCount = em.createQuery("delete from Tag").executeUpdate();
		 deletedCount = em.createQuery("delete from Comment").executeUpdate();
		 deletedCount = em.createQuery("delete from Location").executeUpdate();
		 deletedCount = em.createQuery("delete from Album").executeUpdate();
		em.getTransaction().commit();

	}
	public static void deletePath (String f){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		 Query p = em.createQuery("delete from Picture c where c.path= :p");
		 Query t = em.createQuery("delete from Tag c where c.path= :p");
		 Query c = em.createQuery("delete from Comment c where c.path= :p");
		 Query l = em.createQuery("delete from  Location c where c.path= :p");
	
		 int deletedCount = p.setParameter("p", f).executeUpdate();	
		 deletedCount = t.setParameter("p", f).executeUpdate();	
		 deletedCount = c.setParameter("p", f).executeUpdate();	
		 deletedCount = l.setParameter("p", f).executeUpdate();	
		 em.getTransaction().commit();

	}
	public static void getAlbumName(String filePath){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		Query b = em.createQuery("select t from Album t");
		List<Album> album =  b.getResultList();

		for(Album a: album){
			System.out.println(a.getName());
			System.out.println(a.getAlbumID()+"");
		}
	
	}
}

