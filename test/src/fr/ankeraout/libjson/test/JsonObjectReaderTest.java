package fr.ankeraout.libjson.test;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.ankeraout.libjson.JsonObject;
import fr.ankeraout.libjson.JsonObjectReader;
import fr.ankeraout.libjson.exception.KeyNotFoundException;
import fr.ankeraout.libjson.exception.WrongTypeException;

public class JsonObjectReaderTest {
	@Test
	public void test_getConstructor() throws KeyNotFoundException, WrongTypeException {
		JsonObject jobj = new JsonObject();
		jobj.put("a", 'a');
		JsonObjectReader jor = new JsonObjectReader(jobj);
		
		assertTrue(jor.containsKey("a"));
		assertFalse(jor.containsKey("b"));
		assertEquals(jor.getChar("a"), (Character)'a');
	}
	
	@Test(expected = KeyNotFoundException.class)
	public void test_getGetUnknownKey() throws KeyNotFoundException, WrongTypeException {
		JsonObjectReader jor = new JsonObjectReader(new JsonObject());
		jor.getChar("a");
	}
	
	@Test
	public void test_getGetNullValue() throws KeyNotFoundException, WrongTypeException {
		JsonObject jobj = new JsonObject();
		jobj.put("a", null);
		JsonObjectReader jor = new JsonObjectReader(jobj);
		
		assertNull(jor.get("a", null));
	}
	
	@Test(expected = WrongTypeException.class)
	public void test_getWrongType() throws KeyNotFoundException, WrongTypeException {
		JsonObject jobj = new JsonObject();
		jobj.put("a", null);
		JsonObjectReader jor = new JsonObjectReader(jobj);
		
		jor.getBoolean("a");
	}
}
