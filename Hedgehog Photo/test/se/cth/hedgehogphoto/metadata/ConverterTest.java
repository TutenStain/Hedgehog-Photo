package se.cth.hedgehogphoto.metadata;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import se.cth.hedgehogphoto.calendar.model.CalendarModel;

public class ConverterTest {


@Test
public void testConvertComment(){
String comment = "71, 0, 111, 0, 116, 0, 108, 0, 97, 0, 110, 0, 100, 0, 0, 0";
String res = Converter.convertComment(comment);
System.out.print(res);
assertTrue(res.equals("otland"));
}

@Test
public void testConvertTags(){
String tags = "72, 0, 97, 0, 118, 0, 101, 0, 116, 0, 59, 0, 115, 0, 116, 0, 101, 0, 110, 0, 97, 0, 114, 0, 0, 0";
List<String> res = new ArrayList<String>();
res.addAll(Converter.convertTags(tags));
assertTrue(res.get(0).equals("avet") && res.get(1).equals("stenar"));
}


}
