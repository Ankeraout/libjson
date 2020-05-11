package fr.ankeraout.libjson;

import java.util.HashMap;

/**
 * This class describes a JSON object. A JSON object is basically a dictionary, where values can be
 * accessed with keys.
 * @author Ankeraout
 *
 */
public class JsonObject {
	/**
	 * The wrapped HashMap object that will contain the dictionary entries.
	 */
	private HashMap<String, Object> propertyMap;
	
	/**
	 * Creates a new empty JsonObject.
	 */
	public JsonObject() {
		this.propertyMap = new HashMap<String, Object>();
	}
	
	/**
	 * Sets the value of an entry in the JSON object. If there was already a value associated with
	 * the given key, then it is returned.
	 * @param key The key of the entry
	 * @param value The new value of the entry
	 * @return This method will return the old value of the entry with the given key, if any.
	 */
	public Object put(String key, Object value) {
		// Check type of value
		if(
			value == null
			|| value instanceof Character
			|| value instanceof JsonObject
			|| value instanceof JsonArray
			|| value instanceof String
			|| value instanceof Integer
			|| value instanceof Double
			|| value instanceof Float
			|| value instanceof Boolean
			|| value instanceof Byte
			|| value instanceof Short
			|| value instanceof Long
		) {
			return this.propertyMap.put(key, value);
		} else {
			throw new IllegalArgumentException("Parameter type for \"value\" not allowed: " + value.getClass().getName());
		}
	}
	
	/**
	 * Returns the value of the entry with the given key.
	 * @param key The key of the entry to read
	 * @return The value of the entry with the given key
	 */
	public Object get(String key) {
		return this.propertyMap.get(key);
	}
	
	/**
	 * Returns true of the JsonObject has a key-value pair with the given key, false otherwise.
	 * @param key The key of the key-value pair
	 * @return A boolean value representing whether the key exists in the object
	 */
	public boolean containsKey(String key) {
		return this.propertyMap.containsKey(key);
	}
	
	/**
	 * Returns a string representation of the contents of the object. This representation can be
	 * formatted to make it easily readable for people, or compressed so that its representation is
	 * smaller in terms of memory space. The string returned by this method will always be a valid
	 * JSON string that can be parsed using a JsonParser.
	 * @param indentationLevel The indentation level of the object. Set this to the current
	 * indentation level of the object in the string representation. For example, the root object
	 * will have an indentation level of 0, an array inside it will have an indentation level of 1
	 * and so on. If you do not know what value to use there, just use 0, which is the default.
	 * Also, this value is ignored if newLines is set to false, and if indentString's length is 0.
	 * @param indentString The string used to indent a new line for 1 level. This is generally four
	 * spaces, or one tab character ('\t').
	 * @param newLines Defines whether or not the JSON representation of the object will be
	 * formatted so that it is more readable. If this is set to false, the string representation of
	 * the object that will be returned will have no newline characters, and will be of minimum
	 * length. The value of indentationLevel and indentString parameters will also have no impact.
	 * @return A JSON/String representation of the contents of the object.
	 */
	public String toString(int indentationLevel, String indentString, boolean newLines) {
		if(newLines && indentString == null) {
			indentString = new String();
		}
		
		StringBuilder sb = new StringBuilder();
		
		sb.append('{');
		
		boolean firstItem = true;
		
		for(String key : this.propertyMap.keySet()) {
			if(firstItem) {
				firstItem = false;
			} else {
				sb.append(',');
			}
			
			if(newLines) {
				sb.append('\n');
				
				for(int i = 0; i < indentationLevel + 1; i++) {
					sb.append(indentString);
				}
			}
			
			sb.append(JsonStringUtils.toString(key, indentationLevel + 1, indentString, newLines));
			sb.append(':');
			
			if(newLines) {
				sb.append(' ');
			}
			
			sb.append(JsonStringUtils.toString(this.propertyMap.get(key), indentationLevel + 1, indentString, newLines));
		}
		
		if(newLines) {
			sb.append('\n');
			
			for(int i = 0; i < indentationLevel; i++) {
				sb.append(indentString);
			}
		}
		
		sb.append('}');
		
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return this.toString(0, null, false);
	}
}
