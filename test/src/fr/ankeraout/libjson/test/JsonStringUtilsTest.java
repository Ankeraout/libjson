package fr.ankeraout.libjson.test;

import org.junit.Test;

import fr.ankeraout.libjson.JsonArray;
import fr.ankeraout.libjson.JsonObject;
import fr.ankeraout.libjson.JsonStringUtils;
import junit.framework.TestCase;

public class JsonStringUtilsTest extends TestCase {
	@Test
	public void test_toString_null() {
		assertEquals("null", JsonStringUtils.toString(null, 0, "", false));
	}
	
	@Test
	public void test_toString_character() {
		assertEquals("\"a\"", JsonStringUtils.toString('a', 0, "", false));
	}
	
	@Test
	public void test_toString_object() {
		JsonObject jobj = new JsonObject();
		jobj.put("test_toString_key", "test_toString_value");
		
		assertEquals("{\"test_toString_key\":\"test_toString_value\"}", JsonStringUtils.toString(jobj, 0, "", false));
	}
	
	@Test
	public void test_toString_array() {
		JsonArray jobj = new JsonArray();
		jobj.add(null);
		
		assertEquals("[null]", JsonStringUtils.toString(jobj, 0, "", false));
	}
	
	@Test
	public void test_toString_string_1() {
		assertEquals("\"\\\"\"", JsonStringUtils.toString("\"", 0, "", false));
	}
	
	@Test
	public void test_toString_string_2() {
		assertEquals("\"\\\\\"", JsonStringUtils.toString("\\", 0, "", false));
	}
	
	@Test
	public void test_toString_string_3() {
		assertEquals("\"\\/\"", JsonStringUtils.toString("/", 0, "", false));
	}
	
	@Test
	public void test_toString_string_4() {
		assertEquals("\"\\b\"", JsonStringUtils.toString("\b", 0, "", false));
	}
	
	@Test
	public void test_toString_string_5() {
		assertEquals("\"\\f\"", JsonStringUtils.toString("\f", 0, "", false));
	}
	
	@Test
	public void test_toString_string_6() {
		assertEquals("\"\\n\"", JsonStringUtils.toString("\n", 0, "", false));
	}
	
	@Test
	public void test_toString_string_7() {
		assertEquals("\"\\r\"", JsonStringUtils.toString("\r", 0, "", false));
	}
	
	@Test
	public void test_toString_string_8() {
		assertEquals("\"\\t\"", JsonStringUtils.toString("\t", 0, "", false));
	}
	
	@Test
	public void test_toString_string_9() {
		assertEquals("\"\\u0001\"", JsonStringUtils.toString("\1", 0, "", false));
	}
	
	@Test
	public void test_toString_string_10() {
		assertEquals("\"\\u007f\"", JsonStringUtils.toString("\177", 0, "", false));
	}
	
	@Test
	public void test_toString_string_11() {
		assertEquals("\"\\u0081\"", JsonStringUtils.toString("\201", 0, "", false));
	}
	
	@Test
	public void test_toString_string_12() {
		assertEquals("\"\300\"", JsonStringUtils.toString("\300", 0, "", false));
	}
	
	@Test
	public void test_toString_integer() {
		assertEquals("-66974", JsonStringUtils.toString((int)-66974, 0, "", false));
	}
	
	@Test
	public void test_toString_double() {
		assertEquals("3.141592654", JsonStringUtils.toString((double)3.141592654, 0, "", false));
	}
	
	@Test
	public void test_toString_float() {
		assertEquals("3.1415927", JsonStringUtils.toString((float)3.1415927, 0, "", false));
	}
	
	@Test
	public void test_toString_boolean() {
		assertEquals("true", JsonStringUtils.toString(true, 0, "", false));
		assertEquals("false", JsonStringUtils.toString(false, 0, "", false));
	}
	
	@Test
	public void test_toString_byte() {
		assertEquals("-56", JsonStringUtils.toString((byte)-56, 0, "", false));
	}
	
	@Test
	public void test_toString_short() {
		assertEquals("-6385", JsonStringUtils.toString((short)-6385, 0, "", false));
	}
	
	@Test
	public void test_toString_long() {
		assertEquals("-46385796354113", JsonStringUtils.toString((long)-46385796354113L, 0, "", false));
	}
}
