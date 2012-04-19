package se.cth.hedgehogphoto.database;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import se.cth.hedgehogphoto.FileObject;

public class CommentHandler {
	private static final String PERSISTENCE_UNIT_NAME = "hedgehogphoto";

	private static EntityManagerFactory factory = factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	private static EntityManager em = factory.createEntityManager();

	public static Comment getComment(String com){
	Query c = em.createQuery("select t from Comment t where t.comment=:comment");
	c.setParameter("comment",com);
	try{
		return (Comment)c.getSingleResult();
	}
	catch(Exception e){
		return null;
	}
}
	public static Comment makeComment(Picture pic, FileObject f){
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
		return comment;
	}
	public static void changeComment(Comment comment, Picture pic,FileObject f){
		em.getTransaction().begin();
		comment.setComment(f.getComment());
		List<Picture> pics = comment.getPicture();
		pics.add(pic);
		comment.setPicture(pics);
		pic.setComment(comment);							
		em.persist(pic);
		em.persist(comment);
		em.getTransaction().commit();
	}
}
