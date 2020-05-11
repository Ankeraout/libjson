package fr.ankeraout.libjson;

/**
 * This enum defines all the JSON lexer token types.
 * @author Ankeraout
 *
 */
public enum JsonTokenType {
	LEFT_BRACE,
	RIGHT_BRACE,
	LEFT_BRACKET,
	RIGHT_BRACKET,
	COMMA,
	COLON,
	
	STRING,
	NUMBER,
	TRUE,
	FALSE,
	NULL
}
