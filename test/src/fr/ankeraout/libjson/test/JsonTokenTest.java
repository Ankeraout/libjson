package fr.ankeraout.libjson.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import fr.ankeraout.libjson.JsonToken;
import fr.ankeraout.libjson.JsonTokenType;

public class JsonTokenTest {
	@Test
	public void test_constructor_special() {
		JsonToken token_leftBrace = new JsonToken(JsonTokenType.LEFT_BRACE, "{");
		JsonToken token_leftBracket = new JsonToken(JsonTokenType.LEFT_BRACKET, "[");
		JsonToken token_rightBrace = new JsonToken(JsonTokenType.RIGHT_BRACE, "}");
		JsonToken token_rightBracket = new JsonToken(JsonTokenType.RIGHT_BRACKET, "]");
		JsonToken token_colon = new JsonToken(JsonTokenType.COLON, ":");
		JsonToken token_comma = new JsonToken(JsonTokenType.COMMA, ",");
		JsonToken token_null = new JsonToken(JsonTokenType.NULL, "null");
		
		assertEquals(JsonTokenType.LEFT_BRACE, token_leftBrace.getType());
		assertEquals(JsonTokenType.RIGHT_BRACE, token_rightBrace.getType());
		assertEquals(JsonTokenType.LEFT_BRACKET, token_leftBracket.getType());
		assertEquals(JsonTokenType.RIGHT_BRACKET, token_rightBracket.getType());
		assertEquals(JsonTokenType.COLON, token_colon.getType());
		assertEquals(JsonTokenType.COMMA, token_comma.getType());
		assertEquals(JsonTokenType.NULL, token_null.getType());
		
		assertEquals("{", token_leftBrace.getValue());
		assertEquals("}", token_rightBrace.getValue());
		assertEquals("[", token_leftBracket.getValue());
		assertEquals("]", token_rightBracket.getValue());
		assertEquals(":", token_colon.getValue());
		assertEquals(",", token_comma.getValue());
		assertEquals("null", token_null.getValue());
	}
	
	@Test
	public void test_constructor_number() {
		JsonToken token = new JsonToken(JsonTokenType.NUMBER, "-182.0635E+02");
		
		assertEquals(JsonTokenType.NUMBER, token.getType());
		assertEquals(-182.0635E+02, token.getValue());
	}
	
	@Test
	public void test_constructor_false() {
		JsonToken token = new JsonToken(JsonTokenType.FALSE, "false");
		
		assertEquals(JsonTokenType.FALSE, token.getType());
		assertEquals(false, token.getValue());
	}
	
	@Test
	public void test_constructor_true() {
		JsonToken token = new JsonToken(JsonTokenType.TRUE, "true");
		
		assertEquals(JsonTokenType.TRUE, token.getType());
		assertEquals(true, token.getValue());
	}
	
	@Test
	public void test_constructor_string() {
		JsonToken token = new JsonToken(JsonTokenType.STRING, "Hello");
		
		assertEquals(JsonTokenType.STRING, token.getType());
		assertEquals("Hello", token.getValue());
	}
	
	@Test
	public void test_constructor_string_escapes() {
		JsonToken token = new JsonToken(JsonTokenType.STRING, "\\\"Hello\\\\\\/\\b\\f\\n\\r\\t");
		
		assertEquals(JsonTokenType.STRING, token.getType());
		assertEquals("\"Hello\\/\b\f\n\r\t", token.getValue());
	}
	
	@Test
	public void test_constructor_string_escapes_unicode() {
		JsonToken token = new JsonToken(JsonTokenType.STRING, "\\u001f\\u002e\\u003d\\u004c\\u005b\\u006a\\u0079\\u0008");
		
		assertEquals(JsonTokenType.STRING, token.getType());
		assertEquals("\37\56\75\114\133\152\171\10", token.getValue());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_constructor_string_escapes_unicode_fail() {
		new JsonToken(JsonTokenType.STRING, "\\ux000");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_constructor_string_badChar_1() {
		new JsonToken(JsonTokenType.STRING, "\177");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_constructor_string_badChar_2() {
		new JsonToken(JsonTokenType.STRING, "\01");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_constructor_string_badChar_3() {
		new JsonToken(JsonTokenType.STRING, "\200");
	}
	
	@Test
	public void test_toString() {
		JsonToken token = new JsonToken(JsonTokenType.STRING, "Hello");
		
		assertEquals("STRING -> Hello", token.toString());
	}
	
	@Test
	public void test_equals_1() {
		JsonToken token1 = new JsonToken(JsonTokenType.STRING, "Hi");
		JsonToken token2 = new JsonToken(JsonTokenType.STRING, "Hello");
		
		assertFalse(token1.equals(token2));
	}
	
	@Test
	public void test_equals_2() {
		JsonToken token1 = new JsonToken(JsonTokenType.STRING, "Hello");
		JsonToken token2 = new JsonToken(JsonTokenType.STRING, "Hello");
		
		assertTrue(token1.equals(token2));
	}
	
	@Test
	public void test_equals_3() {
		JsonToken token1 = new JsonToken(JsonTokenType.STRING, "Hello");
		
		assertTrue(token1.equals(token1));
	}
	
	@Test(expected = AssertionError.class)
	public void test_equals_4() {
		JsonToken token1 = new JsonToken(JsonTokenType.STRING, "Hello");
		
		token1.equals(null);
	}
	
	@Test
	public void test_equals_5() {
		JsonToken token1 = new JsonToken(JsonTokenType.STRING, "Hello");
		
		assertFalse(token1.equals(654));
	}
	
	@Test
	public void test_equals_6() {
		JsonToken token1 = new JsonToken(JsonTokenType.STRING, "null");
		JsonToken token2 = new JsonToken(JsonTokenType.NULL, "null");
		
		assertFalse(token1.equals(token2));
	}
}
