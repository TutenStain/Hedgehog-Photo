package se.cth.hedgehogphoto.note.model;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

public class NoteModelTest {
	private NoteModel m;
	
	@Before
	public void setUp(){
		m = new NoteModel();
	}
	@Test
	public void testSetColor() {
		m.setColor(Color.gray);
		Color c = m.getColor();
		assertTrue(c == Color.gray);
	}
	@Test
	public void testSetCircleDiam(){
		m.setCircleDiam(1);
		int cd = m.getCircleDiam();
		assertTrue(cd == 1);
	}

}
