package se.cth.hedgehogphoto.database;

import javax.persistence.EntityManagerFactory;

public class DaoFactory {

	private static JpaAlbumDao jad;
	private static JpaCommentDao jcd;
	private static JpaLocationDao jld;
	private static JpaTagDao jtd;
	private static JpaPictureDao jpd;
	private static DaoFactory daoFactory;

	private DaoFactory(){
		jad = new JpaAlbumDao();
		jcd = new JpaCommentDao();
		jld = new JpaLocationDao();
		jtd = new JpaTagDao();
		jpd = new JpaPictureDao();

	}
	
	public static JpaAlbumDao getJpaAlbumDao() {
		return jad;
	}

	public static void setJpaAlbumDao(JpaAlbumDao jad) {
		DaoFactory.jad = jad;
	}

	public static JpaCommentDao getJpaCommentDao () {
		return jcd;
	}

	public static void setJpaCommentDao (JpaCommentDao jcd) {
		DaoFactory.jcd = jcd;
	}

	public static JpaLocationDao getJpaLocationDao() {
		return jld;
	}

	public static void setJpaLocationDao(JpaLocationDao jld) {
		DaoFactory.jld = jld;
	}

	public static JpaTagDao getJpaTagDao() {
		return jtd;
	}

	public static void setJpaTagDao(JpaTagDao jtd) {
		DaoFactory.jtd = jtd;
	}

	public static JpaPictureDao getJpaPictureDao() {
		return jpd;
	}

	public static void setJpaPictureDao(JpaPictureDao jpd) {
		DaoFactory.jpd = jpd;
	}

	public static DaoFactory getInstance(){
		if(daoFactory == null)
			daoFactory = new DaoFactory();
		return daoFactory;
	}

}
