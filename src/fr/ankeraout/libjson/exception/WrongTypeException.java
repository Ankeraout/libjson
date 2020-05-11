package fr.ankeraout.libjson.exception;

public class WrongTypeException extends Exception {
	private static final long serialVersionUID = 1241481080932705288L;
	
	public WrongTypeException(String key, Class<?> expected, Class<?> actual) {
		super(
			String.format(
				"Wrong type for key \"%s\" in JsonObject: expected %s, got %s.",
				key,
				expected == null ? "null" : expected.getName(),
				actual == null ? "null" : actual.getName()
			)
		);
	}
}