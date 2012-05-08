package se.cth.hedgehogphoto.database;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main2 {

	public static void main(String[] args){
		DatabaseHandler db = DatabaseHandler.getInstance();
		//db.deleteAll();
	//S	new PictureInserter();
			//entityManager.getTransaction().begin();
/*		Picture p = new Picture();
			p.setPath("bafddjgfddyfg¨jjsgjjfllgdsgdr");
			//Location l = new Location();
		/*	l.setLocation("dg");
			l.setLongitude(4.5);
			l.setLatitude(2.3);
			Tag tag = new Tag();
			tag.setTag("lolloljgygglfdj");
		p.setLocation(l);f
			List<Tag> tags = new ArrayList<Tag>();
			tags.add(tag);
			p.setTags(tags);
			List<Picture> pics = new ArrayList<Picture>();
			pics.add(p);
			tag.setPictures(pics);
			entityManager.persist(tag);
			entityManager.persist(p);
			//entityManager.persist(l);
			entityManager.getTransaction().commit();
		*JpaPictureDao jpa = new JpaPictureDao();
			System.out.print(jpa.searchfromTags("lollo"));
			//Query bc =  entityManager.createQuery("select t from Location t where t.location like '%lok%'");
	//	bc.setParameter("location","loka");
			/*try{
				List<Location> loc= bc.getResultList();
				System.out.print(loc);
			}catch(Exception e){
				
	
		
		//album.setComment(com);*/
		JpaAlbumDao jpa = new JpaAlbumDao();
		 Album a = jpa.findById("tönt");
		// List<Tag> t = a.getTags();
		 //t.size();
		// Tag tag = new Tag();
			//tag.setTag("luaww");
		 //t.add(tag);
		 //System.out.print(t);
	//	System.out.print(jpa.getAll());
		JpaTagDao jtd = new JpaTagDao();
		System.out.print(jpa.getAll());	FileObject f = new ImageObject();
		f.setFilePath("pathjjj");
		f.setAlbumName("albumensa");
		f.setComment("wwddddd");
		f.setDate("datessa");
		f.setLocation(new LocationObject("Växjö"));
		f.setFileName("nameass");
		List<String> tagss= new ArrayList<String>();
		tagss.add("tagssas");
		f.setTags(tagss);
	db.insertPicture(f);
	System.out.println(db.getAllPictures());
	System.out.println(db.getTags());
	JpaCommentDao jcd = new JpaCommentDao();
	System.out.println(jcd.getAll());
	
		//System.out.print(jtd.getAll());
		//System.out.print(jpa.searchfromDates("Lallanarnsstggdfgadrxsfsllgkaasglsdggssra"));
//	System.out.print(jpa.searchfromDates("d"));
	/*FileObject o = new ImageObject();
	o.setFilePath("majslATHs");
	List<String> tags = new ArrayList<String>();
	tags.add("MAJs-tagg");
	o.setTags(tags);
	o.setComment("BAJS");
	o.setFileName("majsnamn");
	DatabaseHandler db = new DatabaseHandler();
	db.insertPicture(o);
	System.out.print(db.searchPictureNames("majs"));
	*/
	
	}
}
