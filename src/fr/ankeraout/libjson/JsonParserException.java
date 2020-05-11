package fr.ankeraout.libjson;

/**
 * This class represents a parser exception that is thrown by the parser when it encounters an
 * unexpected sequence of tokens from the lexer.
 * @author Ankeraout
 *
 */
public class JsonParserException extends Exception {
	private static final long serialVersionUID = 2577135968240630230L;

	/**
	 * Creates a new JsonParserException with the given error message.
	 * @param message Error message
	 */
	public JsonParserException(String message) {
		super(message);
	}
}
