package se.cth.hedgehogphoto.database;

import java.util.ArrayList;
import java.util.List;

import se.cth.hedgehogphoto.objects.FileObject;




public class JpaAlbumDao  extends JpaDao<Album, String> implements AlbumDao{

	
	public JpaAlbumDao(){
		super();
	}

	public  List<Album>  searchfromDates(String search){
		if(search.equals("")){
		 return findByLike("date",search.toLowerCase());
		}else{
			return null;
		}
	}
	public List<Album>  searchfromNames(String search){
		if(search.equals("")){
			return findByLike("albumName", search.toLowerCase());
		}else{
			return null;
		}

	}
	public Album findbyName(String albumName){
		if(albumName.equals("")){
			albumName = albumName.toLowerCase();
		return findById(albumName);
		}else{
			return null;
		}
	}
	
	public Album makeAlbum(FileObject f){
		beginTransaction();
	Album theAlbum = new Album();	
	theAlbum.setAlbumName(f.getAlbumName());
	theAlbum.setCoverPath(f.getFilePath());
	 entityManager.persist(theAlbum);
	commitTransaction();
	return theAlbum;
	}

	public List<Album> searchfromComments(String search){
		search = search.toLowerCase();
		if(!(search.equals(""))){
			JpaCommentDao jcd = new JpaCommentDao();
			List<Comment> comments = jcd.findByLike("comment", search);
			if(!(comments.isEmpty())){
				List<Album> albums = new ArrayList<Album>();
				for(Comment comment:comments)
				albums.addAll(findByEntity(comment,"dao.database.Comment"));
			}
	}
	return null;
	}
	public  List<Album> searchfromTags(String search){
		search = search.toLowerCase();
		if(!(search.equals(""))){
			JpaTagDao jtd = new JpaTagDao();
			List<Tag> tags = jtd.findByLike("Tag", search);
			List<Album> albums = new ArrayList<Album>();
 			for(Tag t: tags){
				albums.addAll(findByEntity(t,"dao.database.Tag"));
			}
			return albums;
		}
		return null;
	}
	public List<Album> searchfromLocations(String search){
		search = search.toLowerCase();
		if(!(search.equals(""))){
			JpaLocationDao jld = new JpaLocationDao();
			List<Location> locations = jld.findByLike("Location", search);
			List<Album> albums = new ArrayList<Album>();
			for(Location l:locations){
				albums.addAll(findByEntity(l,"dao.database.Location"));
			}
			return albums;
		}
		return null;
	}

public void addTag(String tag, String albumName){
	Album album = findById(albumName);
	albumName = albumName.toLowerCase();
	tag = tag.toLowerCase();
	if(album != null) {
	
		JpaTagDao jtd = new JpaTagDao();
		Tag tagg = jtd.findById(tag);
		if(tagg!= null){
			List<Album> albums = tagg.getAlbums();
			if(!(albums.contains(album))){
				beginTransaction();
				albums.add(album);
				tagg.setAlbums(albums);
				 entityManager.persist(tagg);
				commitTransaction();
			}
		}else{
			beginTransaction();
			Tag newTag = new Tag();
			newTag.setTag(tag);
			List<Album> albums = new ArrayList<Album>();
			albums.add(album);
			newTag.setAlbums(albums);
			List<Tag> tags =album.getTags();
			tags.add(newTag);
			album.setTags(tags);
			persist(album);
			 entityManager.persist(newTag);
			commitTransaction();
		}

	}

}
public void addComment(String comment, String albumName){
	albumName = albumName.toLowerCase();
	comment = comment.toLowerCase();
	Album album = findById(albumName);
	if(album != null) {
		JpaCommentDao jcd = new JpaCommentDao();
		Comment comm = jcd.findById(comment);
		if(comm != null){
			beginTransaction();
			List<Album> albums = comm.getAlbums();
			if((!albums.contains(album))){
				albums.add(album);
				comm.setAlbums(albums);
				persist(album);
				 entityManager.persist(comm);;
				commitTransaction();
			}


		}else{
			beginTransaction();
			Comment com = new Comment();
			com.setComment(comment);
			List<Album> albums = new ArrayList<Album>();
			albums.add(album);
			com.setAlbums(albums);
			album.setComment(com);
			 entityManager.persist(com);
			persist(album);
			commitTransaction();
		}
	}
}
public void addLocation(String location, String albumName){
	albumName = albumName.toLowerCase();
	location = location.toLowerCase();
	Album album = findById(albumName);
	if(album != null) {
	JpaLocationDao jld = new JpaLocationDao();
		Location loc = jld.findById(location);
		if(loc != null){
			List<Album> albums =loc.getAlbums();

			if((!albums.contains(album))){

				beginTransaction();
				albums.add(album);
				loc.setAlbums(albums);
				album .setLocation(loc);
				persist(album);
				 entityManager.persist(loc);
				commitTransaction();
			}
		}else{
			beginTransaction();
			 loc= new Location();
			loc.setLocation(location);
			List<Album> albums = new ArrayList<Album>();
			albums.add(album);
			loc.setAlbums(albums);
			album .setLocation(loc);
			 entityManager.persist(loc);
			persist(album);
			commitTransaction();
		}
	}

}

public void deleteComment(String albumName){
	albumName = albumName.toLowerCase();
	Album album = findById(albumName);
	if(album != null){
		Comment com = album.getComment();
		beginTransaction();
		List<Album> albums = com.getAlbums();
		albums.remove(album);
		com.setAlbums(albums);
		 entityManager.persist(com);
		commitTransaction();
	}
}
public void deleteLocation(String albumName){
	Album album = findById(albumName);
	if(album!= null){
		Location loc = album.getLocation();
		beginTransaction();
		List<Album> albums = loc.getAlbums();
		albums.remove(album);
		loc.setAlbums(albums);
		 entityManager.persist(loc);
		commitTransaction();
	}
}
public void deleteTags(String albumName){
	albumName = albumName.toLowerCase();
	Album album = findById(albumName);
	if(album!= null){
		List<Tag> tags = album.getTags();
		for(Tag tag: tags){

			beginTransaction();
			List<Album> albums =tag.getAlbums();
			albums .remove(album);
			tag.setAlbums(albums);
			 entityManager.persist(tag);
			commitTransaction();
		}
	}
}
public void deletePicture(String filePath){
	JpaPictureDao jpd = new JpaPictureDao();
	Picture picture = jpd.findById(filePath);

	if(picture != null){
		beginTransaction();
		Album album = picture.getAlbum();
		List<Picture> pics = album.getPictures();
		pics.remove(picture);
		album.setPictures(pics);
		persist(album);
		beginTransaction();	
	}
}
}
