package fr.ankeraout.libjson;

import java.util.Stack;

/**
 * This class contains the code for the JSON parser. This class can not be instantiated.
 * @author Ankeraout
 *
 */
public final class JsonParser {
	/**
	 * Private constructor for preventing class instantiation.
	 */
	private JsonParser() {
		
	}
	
	/**
	 * This method parses the given string and returns it in a JsonArray or JsonObject form.
	 * @param input The input string
	 * @return A JsonArray/JsonObject represented by the String parameter.
	 * @throws JsonLexerException If the lexer encounters an unexpected sequence of characters in
	 * the input string.
	 * @throws JsonParserException If the parser encounters an unexpected sequence of tokens in the
	 * input string.
	 */
	public synchronized static Object parse(String input) throws JsonLexerException, JsonParserException {
		Stack<Object> parserStack = new Stack<Object>();
		JsonLexer lexer = new JsonLexer(input);
		JsonParserState state = JsonParserState.START;
		JsonToken token = lexer.readToken();
		boolean errorIfNextToken = false;
		String tmpPropertyName = null;
		Object stackTop = null;
		
		while(token != null) {
			if(errorIfNextToken) {
				throw new JsonParserException("Unexpected token after end of JSON object or array : " + token.getType().name());
			}
			
			switch(state) {
			case START:
				switch(token.getType()) {
				case LEFT_BRACE:
					parserStack.push(new JsonObject());
					stackTop = parserStack.peek();
					state = JsonParserState.OBJECT_BEFORE_PROPERTY_NAME;
					break;
				case LEFT_BRACKET:
					parserStack.push(new JsonArray());
					stackTop = parserStack.peek();
					state = JsonParserState.ARRAY_BEFORE_VALUE;
					break;
				default:
					throw new JsonParserException("Unexpected token type " + token.getType().name() + ". Expected opening brace or bracket.");
				}
				break;
			case ARRAY_BEFORE_VALUE:
				switch(token.getType()) {
				case FALSE:
				case TRUE:
				case NUMBER:
				case STRING:
					((JsonArray)parserStack.peek()).add(token.getValue());
					state = JsonParserState.ARRAY_AFTER_VALUE;
					break;
				case NULL:
					((JsonArray)parserStack.peek()).add(null);
					state = JsonParserState.ARRAY_AFTER_VALUE;
					break;
				case LEFT_BRACE:
				{
					JsonObject object = new JsonObject();
					((JsonArray)parserStack.peek()).add(object);
					parserStack.push(object);
					state = JsonParserState.OBJECT_BEFORE_PROPERTY_NAME;
					break;
				}
				case LEFT_BRACKET:
				{
					JsonArray array = new JsonArray();
					((JsonArray)parserStack.peek()).add(array);
					parserStack.push(array);
					state = JsonParserState.ARRAY_BEFORE_VALUE;
					break;
				}
				case RIGHT_BRACKET:
				{
					parserStack.pop();
					
					if(parserStack.empty()) {
						errorIfNextToken = true;
					} else {
						Object object = parserStack.peek();
						
						if(object instanceof JsonObject) {
							state = JsonParserState.OBJECT_AFTER_PROPERTY_VALUE;
						} else {
							state = JsonParserState.ARRAY_AFTER_VALUE;
						}
					}
					
					break;
				}
				default:
					throw new JsonParserException("Unexpected token type " + token.getType().name());
				}
				break;
			case ARRAY_AFTER_VALUE:
				switch(token.getType()) {
				case COMMA:
					state = JsonParserState.ARRAY_AFTER_COMMA;
					break;
				case RIGHT_BRACKET:
					parserStack.pop();
					
					if(parserStack.empty()) {
						errorIfNextToken = true;
					} else {
						Object object = parserStack.peek();
						
						if(object instanceof JsonObject) {
							state = JsonParserState.OBJECT_AFTER_PROPERTY_VALUE;
						} else {
							state = JsonParserState.ARRAY_AFTER_VALUE;
						}
					}
					
					break;
				default:
					throw new JsonParserException("Unexpected token type " + token.getType().name());
				}
				break;
			case ARRAY_AFTER_COMMA:
				switch(token.getType()) {
				case FALSE:
				case TRUE:
				case NUMBER:
				case STRING:
					((JsonArray)parserStack.peek()).add(token.getValue());
					state = JsonParserState.ARRAY_AFTER_VALUE;
					break;
				case NULL:
					((JsonArray)parserStack.peek()).add(null);
					state = JsonParserState.ARRAY_AFTER_VALUE;
					break;
				case LEFT_BRACE:
				{
					JsonObject object = new JsonObject();
					((JsonArray)parserStack.peek()).add(object);
					parserStack.push(object);
					state = JsonParserState.OBJECT_BEFORE_PROPERTY_NAME;
					break;
				}
				case LEFT_BRACKET:
				{
					JsonArray array = new JsonArray();
					((JsonArray)parserStack.peek()).add(array);
					parserStack.push(array);
					state = JsonParserState.ARRAY_BEFORE_VALUE;
					break;
				}
				default:
					throw new JsonParserException("Unexpected token type " + token.getType().name());
				}
				break;
			case OBJECT_BEFORE_PROPERTY_NAME:
				switch(token.getType()) {
				case STRING:
					tmpPropertyName = (String)token.getValue();
					state = JsonParserState.OBJECT_AFTER_PROPERTY_NAME;
					break;
				case RIGHT_BRACE:
				{
					parserStack.pop();
					
					if(parserStack.empty()) {
						errorIfNextToken = true;
					} else {
						Object object = parserStack.peek();
						
						if(object instanceof JsonObject) {
							state = JsonParserState.OBJECT_AFTER_PROPERTY_VALUE;
						} else {
							state = JsonParserState.ARRAY_AFTER_VALUE;
						}
					}
					
					break;
				}
				default:
					throw new JsonParserException("Unexpected token type " + token.getType().name());
				}
				break;
			case OBJECT_AFTER_PROPERTY_NAME:
				switch(token.getType()) {
				case COLON:
					state = JsonParserState.OBJECT_BEFORE_PROPERTY_VALUE;
					break;
				default:
					throw new JsonParserException("Unexpected token type " + token.getType().name());
				}
				break;
			case OBJECT_BEFORE_PROPERTY_VALUE:
				switch(token.getType()) {
				case FALSE:
				case TRUE:
				case NUMBER:
				case STRING:
					((JsonObject)parserStack.peek()).put(tmpPropertyName, token.getValue());
					state = JsonParserState.OBJECT_AFTER_PROPERTY_VALUE;
					break;
				case NULL:
					((JsonObject)parserStack.peek()).put(tmpPropertyName, null);
					state = JsonParserState.OBJECT_AFTER_PROPERTY_VALUE;
					break;
				case LEFT_BRACE:
				{
					JsonObject object = new JsonObject();
					((JsonObject)parserStack.peek()).put(tmpPropertyName, object);
					parserStack.push(object);
					state = JsonParserState.OBJECT_BEFORE_PROPERTY_NAME;
					break;
				}
				case LEFT_BRACKET:
				{
					JsonArray object = new JsonArray();
					((JsonObject)parserStack.peek()).put(tmpPropertyName, object);
					parserStack.push(object);
					state = JsonParserState.ARRAY_BEFORE_VALUE;
					break;
				}
				default:
					throw new JsonParserException("Unexpected token type " + token.getType().name());
				}
				break;
			case OBJECT_AFTER_PROPERTY_VALUE:
				switch(token.getType()) {
				case COMMA:
					state = JsonParserState.OBJECT_AFTER_COMMA;
					break;
				case RIGHT_BRACE:
				{
					parserStack.pop();
					
					if(parserStack.empty()) {
						errorIfNextToken = true;
					} else {
						Object object = parserStack.peek();
						
						if(object instanceof JsonObject) {
							state = JsonParserState.OBJECT_AFTER_PROPERTY_VALUE;
						} else {
							state = JsonParserState.ARRAY_AFTER_VALUE;
						}
					}
					
					break;
				}
				default:
					throw new JsonParserException("Unexpected token type " + token.getType().name());
				}
				break;
			case OBJECT_AFTER_COMMA:
				switch(token.getType()) {
				case STRING:
					tmpPropertyName = (String)token.getValue();
					state = JsonParserState.OBJECT_AFTER_PROPERTY_NAME;
					break;
				default:
					throw new JsonParserException("Unexpected token type " + token.getType().name());
				}
			}
			
			// Read next token
			token = lexer.readToken();
		}

		if(!errorIfNextToken) {
			throw new JsonParserException("Unexpected end of JSON String");
		}
		
		return stackTop;
	}
}
