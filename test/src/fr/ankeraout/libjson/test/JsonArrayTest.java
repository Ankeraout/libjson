package fr.ankeraout.libjson.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fr.ankeraout.libjson.JsonArray;
import fr.ankeraout.libjson.JsonObject;

public class JsonArrayTest {
	@Test
	public void test_constructor_default() {
		JsonArray jarr = new JsonArray();
		
		assertEquals(true, jarr.isEmpty());
	}
	
	@Test
	public void test_constructor_collection() {
		List<Object> intList = new ArrayList<Object>(5);
		
		intList.add(-36);
		intList.add(6984);
		intList.add(555555);
		intList.add(0);
		intList.add(638451);
		
		JsonArray jarr = new JsonArray(intList);
		
		assertEquals(5, jarr.size());
		assertEquals(-36, ((Integer)jarr.get(0)).intValue());
		assertEquals(6984, ((Integer)jarr.get(1)).intValue());
		assertEquals(555555, ((Integer)jarr.get(2)).intValue());
		assertEquals(0, ((Integer)jarr.get(3)).intValue());
		assertEquals(638451, ((Integer)jarr.get(4)).intValue());
		
		intList.set(3, 987);
		
		assertEquals(5, jarr.size());
		assertEquals(-36, ((Integer)jarr.get(0)).intValue());
		assertEquals(6984, ((Integer)jarr.get(1)).intValue());
		assertEquals(555555, ((Integer)jarr.get(2)).intValue());
		assertEquals(0, ((Integer)jarr.get(3)).intValue());
		assertEquals(638451, ((Integer)jarr.get(4)).intValue());
	}
	
	@Test
	public void test_toString_1() {
		JsonArray jarr = new JsonArray();
		
		assertEquals("[]", jarr.toString());
	}
	
	@Test
	public void test_toString_2() {
		JsonArray jarr = new JsonArray();
		
		jarr.add(96);
		jarr.add(null);
		jarr.add(true);
		
		assertEquals("[96,null,true]", jarr.toString());
		assertEquals("[\n\t96,\n\tnull,\n\ttrue\n]", jarr.toString(0, "\t", true));
		assertEquals("[\n96,\nnull,\ntrue\n]", jarr.toString(1, null, true));
	}
	
	@Test
	public void test_add_goodTypeOfValue() {
		JsonArray jarr = new JsonArray();
		
		// Test no exceptions on allowed types
		jarr.add('c'); // Character
		jarr.add(new JsonObject()); // JsonObject
		jarr.add(new JsonArray()); // JsonArray
		jarr.add("Hello world!"); // String
		jarr.add((int)1);
		jarr.add((double)1);
		jarr.add((float)1);
		jarr.add(true);
		jarr.add((byte)1);
		jarr.add((short)1);
		jarr.add((long)1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_add_badTypeOfValue() {
		JsonArray jarr = new JsonArray();
		
		jarr.add(new ArrayList<String>());
	}
}
