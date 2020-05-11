package fr.ankeraout.libjson.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import fr.ankeraout.libjson.JsonArray;
import fr.ankeraout.libjson.JsonObject;

public class JsonObjectTest {
	@Test
	public void test_constructor_default() {
		JsonObject jobj = new JsonObject();
		
		assertEquals("{}", jobj.toString());
	}
	
	@Test
	public void test_put() {
		JsonObject jobj = new JsonObject();
		
		assertEquals(null, jobj.put("hello", "world"));
		assertEquals("world", jobj.get("hello"));
		assertEquals("{\"hello\":\"world\"}", jobj.toString());
		
		assertEquals("world", jobj.put("hello", "hello"));
		assertEquals("hello", jobj.get("hello"));
		assertEquals("{\"hello\":\"hello\"}", jobj.toString());
	}
	
	@Test
	public void test_toString_1() {
		JsonObject jobj = new JsonObject();
		
		jobj.put("k1", "v1");
		jobj.put("k2", "v2");
		
		assertEquals("{\"k1\":\"v1\",\"k2\":\"v2\"}", jobj.toString());
	}
	
	@Test
	public void test_toString_2() {
		JsonObject jobj = new JsonObject();
		
		jobj.put("k1", "v1");
		
		assertEquals("{\n\"k1\": \"v1\"\n}", jobj.toString(0, null, true));
		assertEquals("{\n\t\"k1\": \"v1\"\n}", jobj.toString(0, "\t", true));
		assertEquals("{\"k1\":\"v1\"}", jobj.toString(0, "", false));
		assertEquals("{\n\t\t\"k1\": \"v1\"\n\t}", jobj.toString(1, "\t", true));
	}
	
	@Test
	public void test_put_goodTypeOfValue() {
		JsonObject jobj = new JsonObject();
		
		// Test no exceptions on allowed types
		jobj.put("testKey", null);
		jobj.put("testKey", 'c'); // Character
		jobj.put("testKey", new JsonObject()); // JsonObject
		jobj.put("testKey", new JsonArray()); // JsonArray
		jobj.put("testKey", "Hello world!"); // String
		jobj.put("testKey", (int)1);
		jobj.put("testKey", (double)1);
		jobj.put("testKey", (float)1);
		jobj.put("testKey", true);
		jobj.put("testKey", (byte)1);
		jobj.put("testKey", (short)1);
		jobj.put("testKey", (long)1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_put_badTypeOfValue() {
		JsonObject jobj = new JsonObject();
		
		jobj.put("testKey", new ArrayList<String>());
	}
	
}
