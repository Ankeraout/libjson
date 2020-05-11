package fr.ankeraout.libjson;

/**
 * This class represents a JSON lexer token.
 * @author Ankeraout
 *
 */
public class JsonToken {
	/**
	 * The type of the token
	 */
	private JsonTokenType tokenType;
	
	/**
	 * The value of the token (the actual type of this field depends on the token type)
	 */
	private Object value;
	
	/**
	 * Creates a new JSON lexer token from its type and its value.
	 * @param tokenType The type of the token
	 * @param value The string value of the token
	 */
	public JsonToken(JsonTokenType tokenType, String value) {
		this.tokenType = tokenType;

		switch(tokenType) {
		case LEFT_BRACE:
		case RIGHT_BRACE:
		case LEFT_BRACKET:
		case RIGHT_BRACKET:
		case COLON:
		case COMMA:
		case NULL:
			this.value = value;
			break;
		case STRING:
			this.value = new String();
			{
				boolean escapeNext = false;
				int unicodeDigitIndex = -1;
				char unicodeChar = 0;
				
				for(int i = 0; i < value.length(); i++) {
					char c = value.charAt(i);
					
					if(escapeNext) {
						if(unicodeDigitIndex == -1) {
							switch(c) {
							case '"':
							case '\\':
							case '/':
								this.value = ((String)this.value) + c;
								escapeNext = false;
								break;
							case 'b':
								this.value = ((String)this.value) + '\b';
								escapeNext = false;
								break;
							case 'f':
								this.value = ((String)this.value) + '\f';
								escapeNext = false;
								break;
							case 'n':
								this.value = ((String)this.value) + '\n';
								escapeNext = false;
								break;
							case 'r':
								this.value = ((String)this.value) + '\r';
								escapeNext = false;
								break;
							case 't':
								this.value = ((String)this.value) + '\t';
								escapeNext = false;
								break;
							case 'u':
								unicodeDigitIndex = 0;
								break;
							}
						} else {
							int unicodeDigit;
							
							switch(c) {
							case '0':
								unicodeDigit = 0;
								break;
							case '1':
								unicodeDigit = 1;
								break;
							case '2':
								unicodeDigit = 2;
								break;
							case '3':
								unicodeDigit = 3;
								break;
							case '4':
								unicodeDigit = 4;
								break;
							case '5':
								unicodeDigit = 5;
								break;
							case '6':
								unicodeDigit = 6;
								break;
							case '7':
								unicodeDigit = 7;
								break;
							case '8':
								unicodeDigit = 8;
								break;
							case '9':
								unicodeDigit = 9;
								break;
							case 'a':
							case 'A':
								unicodeDigit = 10;
								break;
							case 'b':
							case 'B':
								unicodeDigit = 11;
								break;
							case 'c':
							case 'C':
								unicodeDigit = 12;
								break;
							case 'd':
							case 'D':
								unicodeDigit = 13;
								break;
							case 'e':
							case 'E':
								unicodeDigit = 14;
								break;
							case 'f':
							case 'F':
								unicodeDigit = 15;
								break;
							default:
								throw new IllegalArgumentException("Failed to parse JSON string : unicode digit parsing failed.");
							}
							
							unicodeChar |= unicodeDigit << ((3 - unicodeDigitIndex++) * 4);
							
							if(unicodeDigitIndex == 4) {
								unicodeDigitIndex = -1;
								this.value = ((String)this.value) + unicodeChar;
								escapeNext = false;
								unicodeChar = 0;
							}
						}
					} else {
						if(c == '\\') {
							escapeNext = true;
						} else if((c >= 0x0000 && c <= 0x001f) || c == 0x007f || (c >= 0x0080 && c <= 0x009f)) {
							throw new IllegalArgumentException("Failed to parse JSON string : unexpected unicode control character");
						} else {
							this.value = ((String)this.value) + c;
						}
					}
				}
			}
			break;
		case NUMBER:
			this.value = new Double(value);
			break;
		case TRUE:
			this.value = new Boolean(true);
			break;
		case FALSE:
			this.value = new Boolean(false);
			break;
		}
	}
	
	/**
	 * Returns the type of the token.
	 * @return The type of the token
	 */
	public JsonTokenType getType() {
		return this.tokenType;
	}
	
	/**
	 * Returns the value of the token. The type of the returned object depends on the token type.
	 * @return The value of the token
	 */
	public Object getValue() {
		return this.value;
	}
	
	@Override
	public String toString() {
		return this.tokenType.name() + " -> " + this.value;
	}
	
	@Override
	public boolean equals(Object other) {
		assert other != null;
		
		if(!(other instanceof JsonToken)) {
			return false;
		}
		
		JsonToken token = (JsonToken)other;
		
		if(token.tokenType != this.tokenType) {
			return false;
		}
		
		return token.value.equals(this.value);
	}
}
