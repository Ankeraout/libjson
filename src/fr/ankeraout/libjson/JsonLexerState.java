package fr.ankeraout.libjson;

/**
 * This enum defines the names of the states of the lexer automata.
 * @author Ankeraout
 *
 */
public enum JsonLexerState {
	START,
	READING_STRING,
	READING_STRING_ESCAPE,
	READING_STRING_ESCAPE_UNICODE_1,
	READING_STRING_ESCAPE_UNICODE_2,
	READING_STRING_ESCAPE_UNICODE_3,
	READING_STRING_ESCAPE_UNICODE_4,
	READING_NUMBER_BEFOREINTPART,
	READING_NUMBER_INTPART,
	READING_NUMBER_FRACPART,
	READING_NUMBER_EXPPART_BEFORESIGN,
	READING_NUMBER_EXPPART_AFTERSIGN,
	READING_NUMBER_EXPPART_AFTERSIGN_2,
	READING_TRUE_2,
	READING_TRUE_3,
	READING_TRUE_4,
	READING_FALSE_2,
	READING_FALSE_3,
	READING_FALSE_4,
	READING_FALSE_5,
	READING_NULL_2,
	READING_NULL_3,
	READING_NULL_4
}