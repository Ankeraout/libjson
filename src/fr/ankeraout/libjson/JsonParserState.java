package fr.ankeraout.libjson;

/**
 * This enum defines the states of the parsing automata.
 * @author Ankeraout
 *
 */
public enum JsonParserState {
	START,
	
	OBJECT_BEFORE_PROPERTY_NAME,
	OBJECT_AFTER_PROPERTY_NAME,
	OBJECT_BEFORE_PROPERTY_VALUE,
	OBJECT_AFTER_PROPERTY_VALUE,
	OBJECT_AFTER_COMMA,
	
	ARRAY_BEFORE_VALUE,
	ARRAY_AFTER_VALUE,
	ARRAY_AFTER_COMMA
}
