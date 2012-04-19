package se.cth.hedgehogphoto.database;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class TagHandler {
	private static final String PERSISTENCE_UNIT_NAME = "hedgehogphoto";

	private static EntityManagerFactory factory = factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	private static EntityManager em = factory.createEntityManager();

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
	public static Tag getTag(String t){
	Query ta = em.createQuery("select t from Tag t where t.tag=:tag");
	ta.setParameter("tag",t);
	try{
		return (Tag) ta.getSingleResult();
	}catch(Exception e){
		return null;
	}
	}
	public static Tag makeTag(Picture pic, String t){
		em.getTransaction().begin();
		Tag tag = new Tag();
		tag.setTag(t);			
		List<Picture> peg = new ArrayList<Picture>();
		peg.add(pic);
		tag.setPicture(peg);
		List<Tag> pTags = pic.getTags();
		if(pTags!=null)
			pTags.add(tag);		
		pic.setTag(pTags);
		em.persist(tag);
		em.persist(pic);
		em.getTransaction().commit();
		return tag;
	}
	public static void changeTag(Picture pic, Tag tag ){
		em.getTransaction().begin();
		List<Picture> ptag= tag.getPicture();						
		if(!(ptag.contains(pic)))
			ptag.add(pic);
		List<Tag> pTags = pic.getTags();
		if(!(pTags.contains(tag)))
			pTags.add(tag);
		tag.setPicture(ptag);						
		pic.setTag(pTags);
		em.persist(tag);
		em.persist(pic);
		em.getTransaction().commit();
	}
}