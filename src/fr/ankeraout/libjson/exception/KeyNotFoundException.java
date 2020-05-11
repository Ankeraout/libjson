package fr.ankeraout.libjson.exception;

public class KeyNotFoundException extends Exception {
	private static final long serialVersionUID = -2208658826880163074L;
	
	public KeyNotFoundException(String key) {
		super(String.format("Key not found: \"%s\".", key));
	}
}
