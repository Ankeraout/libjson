package fr.ankeraout.libjson;

/**
 * This class represents a JSON lexer. It is a tool that is used by the JSON parser for cutting the
 * input string into smaller parts that are called "tokens". See JsonToken class documentation for
 * more information.
 * @author Ankeraout
 *
 */
public class JsonLexer {
	/**
	 * The input string
	 */
	private String input;
	
	/**
	 * The current position in the input string
	 */
	private int position;
	
	/**
	 * Creates a new instance of a JsonLexer with the given input string.
	 * @param input The input string for the lexer.
	 */
	public JsonLexer(String input) {
		this.input = input;
		this.position = 0;
	}
	
	/**
	 * Reads a token from the input string and returns it. This method will return null after the
	 * end of the input string has been reached.
	 * @return The next token from the input string
	 * @throws JsonLexerException If the lexer encounters an unexpected token.
	 */
	public JsonToken readToken() throws JsonLexerException {
		JsonLexerState state = JsonLexerState.START;
		StringBuilder buffer = new StringBuilder();
		
		while(this.position < this.input.length()) {
			char c = this.input.charAt(this.position++);

			switch(state) {
			case START:
				switch(c) {
				case '{':
					return new JsonToken(JsonTokenType.LEFT_BRACE, "{");
				case '}':
					return new JsonToken(JsonTokenType.RIGHT_BRACE, "}");
				case '[':
					return new JsonToken(JsonTokenType.LEFT_BRACKET, "[");
				case ']':
					return new JsonToken(JsonTokenType.RIGHT_BRACKET, "]");
				case ',':
					return new JsonToken(JsonTokenType.COMMA, ",");
				case ':':
					return new JsonToken(JsonTokenType.COLON, ":");
				case '"':
					state = JsonLexerState.READING_STRING;
					break;
				case 'T':
				case 't':
					state = JsonLexerState.READING_TRUE_2;
					buffer.append(c);
					break;
				case 'F':
				case 'f':
					state = JsonLexerState.READING_FALSE_2;
					buffer.append(c);
					break;
				case 'N':
				case 'n':
					state = JsonLexerState.READING_NULL_2;
					buffer.append(c);
					break;
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
					state = JsonLexerState.READING_NUMBER_INTPART;
					buffer.append(c);
					break;
				case '-':
					state = JsonLexerState.READING_NUMBER_BEFOREINTPART;
					buffer.append(c);
					break;
				case ' ':
				case '\t':
				case '\r':
				case '\n':
				case '\f':
					break;
				default:
					throw new JsonLexerException("Unexpected character '" + c + "'");
				}
				break;
			case READING_STRING:
				if(c == '"') {
					return new JsonToken(JsonTokenType.STRING, buffer.toString());
				} else if(c == '\\') {
					buffer.append(c);
					state = JsonLexerState.READING_STRING_ESCAPE;
				} else {
					buffer.append(c);
				}
				break;
			case READING_STRING_ESCAPE:
				switch(c) {
				case '"':
				case '\\':
				case '/':
				case 'b':
				case 'f':
				case 'n':
				case 'r':
				case 't':
					buffer.append(c);
					state = JsonLexerState.READING_STRING;
					break;
				case 'u':
					buffer.append(c);
					state = JsonLexerState.READING_STRING_ESCAPE_UNICODE_1;
					break;
				default:
					throw new JsonLexerException("Unexpected escape '\\" + c);
				}
				break;
			case READING_STRING_ESCAPE_UNICODE_1:
				if((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F')) {
					buffer.append(c);
					state = JsonLexerState.READING_STRING_ESCAPE_UNICODE_2;
				} else {
					throw new JsonLexerException("Not an hexadecimal digit : '" + c + '\'');
				}
				break;
			case READING_STRING_ESCAPE_UNICODE_2:
				if((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F')) {
					buffer.append(c);
					state = JsonLexerState.READING_STRING_ESCAPE_UNICODE_3;
				} else {
					throw new JsonLexerException("Not an hexadecimal digit : '" + c + '\'');
				}
				break;
			case READING_STRING_ESCAPE_UNICODE_3:
				if((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F')) {
					buffer.append(c);
					state = JsonLexerState.READING_STRING_ESCAPE_UNICODE_4;
				} else {
					throw new JsonLexerException("Not an hexadecimal digit : '" + c + '\'');
				}
				break;
			case READING_STRING_ESCAPE_UNICODE_4:
				if((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F')) {
					buffer.append(c);
					state = JsonLexerState.READING_STRING;
				} else {
					throw new JsonLexerException("Not an hexadecimal digit : '" + c + '\'');
				}
				break;
			case READING_NUMBER_BEFOREINTPART:
				if(c >= '0' && c <= '9') {
					buffer.append(c);
					state = JsonLexerState.READING_NUMBER_INTPART;
				} else {
					throw new JsonLexerException("Unexpected character '" + c + "'");
				}
				break;
			case READING_NUMBER_INTPART:
				if(c >= '0' && c <= '9') {
					buffer.append(c);
				} else if(c == '.') {
					buffer.append(c);
					state = JsonLexerState.READING_NUMBER_FRACPART;
				} else if(c == 'E' || c == 'e') {
					buffer.append(c);
					state = JsonLexerState.READING_NUMBER_EXPPART_BEFORESIGN;
				} else {
					this.position--;
					return new JsonToken(JsonTokenType.NUMBER, buffer.toString());
				}
				break;
			case READING_NUMBER_FRACPART:
				if(c >= '0' && c <= '9') {
					buffer.append(c);
				} else if(c == 'E' || c == 'e') {
					buffer.append(c);
					state = JsonLexerState.READING_NUMBER_EXPPART_BEFORESIGN;
				} else {
					this.position--;
					return new JsonToken(JsonTokenType.NUMBER, buffer.toString());
				}
				break;
			case READING_NUMBER_EXPPART_BEFORESIGN:
				if(c == '+' || c == '-') {
					buffer.append(c);
					state = JsonLexerState.READING_NUMBER_EXPPART_AFTERSIGN;
				} else if(c >= '0' && c <= '9') {
					buffer.append(c);
					state = JsonLexerState.READING_NUMBER_EXPPART_AFTERSIGN_2;
				} else {
					throw new JsonLexerException("Unexpected character '" + c + "'");
				}
				break;
			case READING_NUMBER_EXPPART_AFTERSIGN:
				if(c >= '0' && c <= '9') {
					buffer.append(c);
					state = JsonLexerState.READING_NUMBER_EXPPART_AFTERSIGN_2;
				} else {
					throw new JsonLexerException("Unexpected character '" + c + "', expected decimal digit.");
				}
				break;
			case READING_NUMBER_EXPPART_AFTERSIGN_2:
				if(c >= '0' && c <= '9') {
					buffer.append(c);
					state = JsonLexerState.READING_NUMBER_EXPPART_AFTERSIGN_2;
				} else {
					this.position--;
					return new JsonToken(JsonTokenType.NUMBER, buffer.toString());
				}
				break;
			case READING_FALSE_2:
				if(c == 'A' || c == 'a') {
					state = JsonLexerState.READING_FALSE_3;
					buffer.append(c);
				} else {
					throw new JsonLexerException("Unexpected character '" + c + "'");
				}
				break;
			case READING_FALSE_3:
				if(c == 'L' || c == 'l') {
					state = JsonLexerState.READING_FALSE_4;
					buffer.append(c);
				} else {
					throw new JsonLexerException("Unexpected character '" + c + "'");
				}
				break;
			case READING_FALSE_4:
				if(c == 'S' || c == 's') {
					state = JsonLexerState.READING_FALSE_5;
					buffer.append(c);
				} else {
					throw new JsonLexerException("Unexpected character '" + c + "'");
				}
				break;
			case READING_FALSE_5:
				if(c == 'E' || c == 'e') {
					buffer.append(c);
					return new JsonToken(JsonTokenType.FALSE, buffer.toString());
				} else {
					throw new JsonLexerException("Unexpected character '" + c + "'");
				}
			case READING_NULL_2:
				if(c == 'U' || c == 'u') {
					state = JsonLexerState.READING_NULL_3;
					buffer.append(c);
				} else {
					throw new JsonLexerException("Unexpected character '" + c + "'");
				}
				break;
			case READING_NULL_3:
				if(c == 'L' || c == 'l') {
					state = JsonLexerState.READING_NULL_4;
					buffer.append(c);
				} else {
					throw new JsonLexerException("Unexpected character '" + c + "'");
				}
				break;
			case READING_NULL_4:
				if(c == 'L' || c == 'l') {
					buffer.append(c);
					return new JsonToken(JsonTokenType.NULL, buffer.toString());
				} else {
					throw new JsonLexerException("Unexpected character '" + c + "'");
				}
			case READING_TRUE_2:
				if(c == 'R' || c == 'r') {
					state = JsonLexerState.READING_TRUE_3;
					buffer.append(c);
				} else {
					throw new JsonLexerException("Unexpected character '" + c + "'");
				}
				break;
			case READING_TRUE_3:
				if(c == 'U' || c == 'u') {
					state = JsonLexerState.READING_TRUE_4;
					buffer.append(c);
				} else {
					throw new JsonLexerException("Unexpected character '" + c + "'");
				}
				break;
			case READING_TRUE_4:
				if(c == 'E' || c == 'e') {
					buffer.append(c);
					return new JsonToken(JsonTokenType.TRUE, buffer.toString());
				} else {
					throw new JsonLexerException("Unexpected character '" + c + "'");
				}
			}
		}
		
		if(state != JsonLexerState.START) {
			throw new JsonLexerException("Unexpected end of JSON data");
		}
		
		return null;
	}
}
