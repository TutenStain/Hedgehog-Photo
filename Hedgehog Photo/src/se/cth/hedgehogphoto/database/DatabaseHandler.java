package se.cth.hedgehogphoto.database;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import se.cth.hedgehogphoto.FileObject;



public class DatabaseHandler {
	private static final String PERSISTENCE_UNIT_NAME = "hedgehogphoto";
	private static EntityManagerFactory factory;
	static DatabaseHandler dh;
	EntityManager em;


	public static List<String> getTags(){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		Query q = em.createQuery("select t from Tags t");
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

		Query q = em.createQuery("select t from Locations t");
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
			if(list.get(i).getName() == search)
				for(Picture pic: piclist){
				if(pic.getAlbum_ID() == list.get(i).getAlbumID())
				fileObjects.add( makeFileObjectfromPath(pic.getPath(),em));
		}
		}
		return fileObjects;

	}
	public static List<FileObject> searchComments(String search){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();

		Query q = em.createQuery("select t.path from Comment t");
		List<Comment> list = q.getResultList();
		//List<String> paths = getPaths("Picture");
		List<FileObject> fileObjects = new ArrayList();
		for (int i =0; i< list.size(); i++) {
			fileObjects.add( makeFileObjectfromPath(list.get(i).getPath(),em));
		}
		return fileObjects;

	}
	public static List<FileObject> searchTags(String search){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();

		Query q = em.createQuery("select t from Tags t");
		List<Tag> list = q.getResultList();
		List<String> paths = new ArrayList();
		for(int i=0; i <list.size();i++){
			//if(!(paths.contains(list.get(i).getTags())));
			for(int j = 0; j < list.get(i).getTags().size(); i++)
				if(list.get(i).getTags().get(j) == search){
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

		Query p = em.createQuery("select t from Pictures t");
		List<Picture> list = p.getResultList();
		String name = "";
		String date = "";
		String album_ID = "";
		for(Picture pic: list){
			if(pic.getPath() == filePath){
				name=pic.getName();
				date=pic.getDate();
				album_ID = pic.getAlbum_ID()+"";		
			}
		}

		Query a = em.createQuery("select t from Album t");
		List<Album> alist = a.getResultList();
		String coverPath= "";
		for(Picture pic: list){
			for(Album alb: alist){
				if(pic.getPath() == filePath){

					coverPath = alb.getCoverPath();

				}

			}
		}
		Query t = em.createQuery("select t from Tags t");
		List<Tag> tlist = t.getResultList();
		List<String> tags = new ArrayList();
		for(Tag tag: tlist){
			if(tag.getPath() == filePath){
				List<String> taglist = tag.getTags();
				for(int i =0; i<taglist.size();i++){
					tags.add(taglist.get(i));
				}

			}
		}

		Query l = em.createQuery("select t from Locations t");
		List<Location> llist = l.getResultList();
		String locations = "";
		for(Location location: llist){
			if(location.getPath() == filePath){
				locations=location.getLocation();
			}
		}



		return null;
	}



	public static void insert(FileObject f){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();

		em.getTransaction().begin();
		Picture pic = new Picture();
		pic.setPath(f.getImagePath());
		pic.setDate(f.getDate());
		pic.setName(f.getImageName());
		Tag tag = new Tag();
		tag.setPath(f.getImagePath());

		em.persist(pic);
		em.getTransaction().commit();

		em.getTransaction().begin();
		List <String> tags = f.getTags();
		if(f.getTags() != null){ 

			for(int i = 0; i < tags.size(); i++){
				tag.setTag(tags.get(i));

			} 
		}
		em.persist(tags);
		em.getTransaction().commit();

		em.getTransaction().begin();

		Comment comment = new Comment();
		if(f.getComment() != null || f.getComment() != ""){

			comment.setComment(f.getComment());
		}
		em.persist(comment);
		em.getTransaction().commit();

		em.getTransaction().begin();
		Location location = new Location();
		if(f.getLocation() != null || f.getLocation() != ""){

			location.setLocation(f.getLocation());
		}
		em.persist(location);
		em.getTransaction().commit();

		em.getTransaction().begin();
		Album album = new Album();
		//if(f.getAlbumName() != null || f.getAlbumName() != ""){

		album.setCoverPath(f.getCoverPath());
		//album.setName(f.getAlbumName());

		em.persist(album);
		em.getTransaction().commit();
		em.close();

	}
	public static void addTag(String tag, String filePath){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();

		em.getTransaction().begin();
		Tag todo = new Tag();
		todo.setPath(filePath);
		todo.setTag(tag);
		em.persist(todo);
		em.getTransaction().commit();	
		em.close();

	}
	public static void addComment(String comment, String filePath){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();

		em.getTransaction().begin();
		Comment com = new Comment();
		com.setPath(filePath);
		com.setComment(comment);
		em.persist(comment);
		em.getTransaction().commit();	


	}
	public static void addLocation(String location, String filePath){
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();

		em.getTransaction().begin();
		Location todo = new Location();
		todo.setPath(filePath);
		todo.setLocation(location);
		em.persist(todo);
		em.getTransaction().commit();	


	}
}

