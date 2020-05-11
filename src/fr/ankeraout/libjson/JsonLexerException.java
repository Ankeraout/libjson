package fr.ankeraout.libjson;

/**
 * This class describes an exception that is thrown by the JSON lexer when it encounters an
 * unexpected sequence of characters.
 * @author Ankeraout
 *
 */
public class JsonLexerException extends Exception {
	private static final long serialVersionUID = 2577135968240630230L;

	/**
	 * Creates a new JsonLexerException with the given error message
	 * @param message Error message describing what happened
	 */
	public JsonLexerException(String message) {
		super(message);
	}
}
