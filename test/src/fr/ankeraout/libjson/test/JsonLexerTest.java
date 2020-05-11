package fr.ankeraout.libjson.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fr.ankeraout.libjson.JsonLexer;
import fr.ankeraout.libjson.JsonLexerException;
import fr.ankeraout.libjson.JsonToken;
import fr.ankeraout.libjson.JsonTokenType;

public class JsonLexerTest {

	private static JsonToken[] lex(String input) throws JsonLexerException {
		List<JsonToken> tokens = new ArrayList<JsonToken>(256);
		
		JsonLexer lexer = new JsonLexer(input);
		JsonToken token = lexer.readToken();
		
		while(token != null) {
			tokens.add(token);
			token = lexer.readToken();
		}
		
		return tokens.toArray(new JsonToken[tokens.size()]);
	}
	
	@Test
	public void test_number() throws JsonLexerException {
		String input = "{\"test1\":0,\"test2\":-1,\"test3\":2.0,\"test4\":-30.0,\"test5\":4.0E01,\"test6\":5.0E-1,\"test7\":6E1}";
		
		JsonToken[] tokens = lex(input);

		assertEquals(new JsonToken(JsonTokenType.NUMBER, "0"), tokens[3]);
		assertEquals(new JsonToken(JsonTokenType.NUMBER, "-1"), tokens[7]);
		assertEquals(new JsonToken(JsonTokenType.NUMBER, "2.0"), tokens[11]);
		assertEquals(new JsonToken(JsonTokenType.NUMBER, "-30.0"), tokens[15]);
		assertEquals(new JsonToken(JsonTokenType.NUMBER, "4.0E1"), tokens[19]);
		assertEquals(new JsonToken(JsonTokenType.NUMBER, "5.0E-1"), tokens[23]);
		assertEquals(new JsonToken(JsonTokenType.NUMBER, "6E1"), tokens[27]);
		assertEquals(29, tokens.length);
	}
	
	@Test
	public void test_string() throws JsonLexerException {
		String input = "{\"test1\":\"\\u0065\", \"test2\":\"\\n\"}";
		
		JsonToken[] tokens = lex(input);
		
		assertEquals(new JsonToken(JsonTokenType.STRING, "e"), tokens[3]);
		assertEquals(new JsonToken(JsonTokenType.STRING, "\\n"), tokens[7]);
		assertEquals(9, tokens.length);
	}
	
	@Test
	public void test_boolean() throws JsonLexerException {
		String input = "{\"test1\":true, \"test2\":false}";
		
		JsonToken[] tokens = lex(input);
		
		assertEquals(new JsonToken(JsonTokenType.TRUE, "true"), tokens[3]);
		assertEquals(new JsonToken(JsonTokenType.FALSE, "false"), tokens[7]);
		assertEquals(9, tokens.length);
	}
	
	@Test
	public void test_null() throws JsonLexerException {
		String input = "{\"test1\":null}";
		
		JsonToken[] tokens = lex(input);
		
		assertEquals(new JsonToken(JsonTokenType.NULL, "null"), tokens[3]);
		assertEquals(5, tokens.length);
	}
	
	@Test
	public void test_array() throws JsonLexerException {
		String input = "{\"test1\":[null]}";
		
		JsonToken[] tokens = lex(input);
		
		assertEquals(new JsonToken(JsonTokenType.LEFT_BRACKET, "["), tokens[3]);
		assertEquals(new JsonToken(JsonTokenType.RIGHT_BRACKET, "]"), tokens[5]);
		assertEquals(7, tokens.length);
	}
	
	@Test(expected = JsonLexerException.class)
	public void test_lexer_exception_unexpectedCharacter() throws JsonLexerException {
		lex("l");
	}
	
	@Test(expected = JsonLexerException.class)
	public void test_lexer_exception_unknownEscape() throws JsonLexerException {
		lex("\"\\l\"");
	}
	
	@Test(expected = JsonLexerException.class)
	public void test_lexer_exception_unicodeHex0() throws JsonLexerException {
		lex("\"\\ux000\"");
	}
	
	@Test(expected = JsonLexerException.class)
	public void test_lexer_exception_unicodeHex1() throws JsonLexerException {
		lex("\"\\u0x00\"");
	}
	
	@Test(expected = JsonLexerException.class)
	public void test_lexer_exception_unicodeHex2() throws JsonLexerException {
		lex("\"\\u00x0\"");
	}
	
	@Test(expected = JsonLexerException.class)
	public void test_lexer_exception_unicodeHex3() throws JsonLexerException {
		lex("\"\\u000x\"");
	}
	
	@Test(expected = JsonLexerException.class)
	public void test_lexer_exception_notADecimalDigit() throws JsonLexerException {
		lex("-x");
	}
	
	@Test(expected = JsonLexerException.class)
	public void test_lexer_exception_exp() throws JsonLexerException {
		lex("0.96Ex");
	}
	
	@Test(expected = JsonLexerException.class)
	public void test_lexer_exception_expAfterSign() throws JsonLexerException {
		lex("0.96E+x");
	}
	
	@Test(expected = JsonLexerException.class)
	public void test_lexer_exception_false_1() throws JsonLexerException {
		lex("fx");
	}
	
	@Test(expected = JsonLexerException.class)
	public void test_lexer_exception_false_2() throws JsonLexerException {
		lex("fax");
	}
	
	@Test(expected = JsonLexerException.class)
	public void test_lexer_exception_false_3() throws JsonLexerException {
		lex("falx");
	}
	
	@Test(expected = JsonLexerException.class)
	public void test_lexer_exception_false_4() throws JsonLexerException {
		lex("falsx");
	}
	
	@Test(expected = JsonLexerException.class)
	public void test_lexer_exception_null_2() throws JsonLexerException {
		lex("nx");
	}
	
	@Test(expected = JsonLexerException.class)
	public void test_lexer_exception_null_3() throws JsonLexerException {
		lex("nux");
	}
	
	@Test(expected = JsonLexerException.class)
	public void test_lexer_exception_null_4() throws JsonLexerException {
		lex("nulx");
	}
	
	@Test(expected = JsonLexerException.class)
	public void test_lexer_exception_true_2() throws JsonLexerException {
		lex("tx");
	}
	
	@Test(expected = JsonLexerException.class)
	public void test_lexer_exception_true_3() throws JsonLexerException {
		lex("trx");
	}
	
	@Test(expected = JsonLexerException.class)
	public void test_lexer_exception_true_4() throws JsonLexerException {
		lex("trux");
	}
	
	@Test(expected = JsonLexerException.class)
	public void test_lexer_exception_end() throws JsonLexerException {
		lex("tru");
	}
}
