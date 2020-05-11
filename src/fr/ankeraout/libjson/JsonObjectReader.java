package fr.ankeraout.libjson;

import fr.ankeraout.libjson.exception.KeyNotFoundException;
import fr.ankeraout.libjson.exception.WrongTypeException;

/**
 * This class is a wrapper for JsonObject providing tools for reading it.
 * 
 * @author Ankeraout
 */
public class JsonObjectReader {
	/**
	 * The JsonObject that will be used for reading
	 */
	private JsonObject jsonObject;
	
	/**
	 * Creates a new JsonObjectReader for reading the given JsonObject.
	 * @param jsonObject The JsonObject to read
	 */
	public JsonObjectReader(JsonObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	
	/**
	 * Returns a boolean value representing whether the given key exists in the JsonObject or not.
	 * @param key The key to test
	 * @return true if the key exists, false otherwise.
	 */
	public boolean containsKey(String key) {
		return this.jsonObject.containsKey(key);
	}
	
	/**
	 * Gets a value in the JsonObject and tests its type.
	 * @param key The key that is used to get the value
	 * @param expectedType The expected type of the value
	 * @return The value associated with the given key
	 * @throws KeyNotFoundException If the key does not exist in the JsonObject
	 * @throws WrongTypeException If the type of the value is not the expected type
	 */
	public Object get(String key, Class<?> expectedType) throws KeyNotFoundException, WrongTypeException {
		if(!this.containsKey(key)) {
			throw new KeyNotFoundException(key);
		}
		
		Object value = this.jsonObject.get(key);
		
		if(value == null) {
			if(expectedType != null) {
				throw new WrongTypeException(key, expectedType, null);
			}
		} else {
			if(value.getClass() != expectedType) {
				throw new WrongTypeException(key, expectedType, value.getClass());
			}
		}
		
		return value;
	}
	
	/**
	 * Gets the character value in the JsonObject associated with the given key.
	 * @param key The key associated with the value to return
	 * @return The value associated with the given key
	 * @throws KeyNotFoundException If the key does not exist in the JsonObject
	 * @throws WrongTypeException If the value is not a character.
	 */
	public Character getChar(String key) throws KeyNotFoundException, WrongTypeException {
		return (Character)this.get(key, Character.class);
	}
	
	/**
	 * Gets the JsonObject value in the JsonObject associated with the given key.
	 * @param key The key associated with the value to return
	 * @return The value associated with the given key
	 * @throws KeyNotFoundException If the key does not exist in the JsonObject
	 * @throws WrongTypeException If the value is not a JsonObject.
	 */
	public JsonObject getJsonObject(String key) throws KeyNotFoundException, WrongTypeException {
		return (JsonObject)this.get(key, JsonObject.class);
	}

	/**
	 * Gets the JsonArray value in the JsonObject associated with the given key.
	 * @param key The key associated with the value to return
	 * @return The value associated with the given key
	 * @throws KeyNotFoundException If the key does not exist in the JsonObject
	 * @throws WrongTypeException If the value is not a JsonArray.
	 */
	public JsonArray getJsonArray(String key) throws KeyNotFoundException, WrongTypeException {
		return (JsonArray)this.get(key, JsonArray.class);
	}

	/**
	 * Gets the String value in the JsonObject associated with the given key.
	 * @param key The key associated with the value to return
	 * @return The value associated with the given key
	 * @throws KeyNotFoundException If the key does not exist in the JsonObject
	 * @throws WrongTypeException If the value is not a String.
	 */
	public String getString(String key) throws KeyNotFoundException, WrongTypeException {
		return (String)this.get(key, String.class);
	}

	/**
	 * Gets the integer value in the JsonObject associated with the given key.
	 * @param key The key associated with the value to return
	 * @return The value associated with the given key
	 * @throws KeyNotFoundException If the key does not exist in the JsonObject
	 * @throws WrongTypeException If the value is not an Integer.
	 */
	public Integer getInt(String key) throws KeyNotFoundException, WrongTypeException {
		return (Integer)this.get(key, Integer.class);
	}

	/**
	 * Gets the Double value in the JsonObject associated with the given key.
	 * @param key The key associated with the value to return
	 * @return The value associated with the given key
	 * @throws KeyNotFoundException If the key does not exist in the JsonObject
	 * @throws WrongTypeException If the value is not a Double.
	 */
	public Double getDouble(String key) throws KeyNotFoundException, WrongTypeException {
		return (Double)this.get(key, Double.class);
	}

	/**
	 * Gets the Float value in the JsonObject associated with the given key.
	 * @param key The key associated with the value to return
	 * @return The value associated with the given key
	 * @throws KeyNotFoundException If the key does not exist in the JsonObject
	 * @throws WrongTypeException If the value is not a Float.
	 */
	public Float getFloat(String key) throws KeyNotFoundException, WrongTypeException {
		return (Float)this.get(key, Float.class);
	}

	/**
	 * Gets the Boolean value in the JsonObject associated with the given key.
	 * @param key The key associated with the value to return
	 * @return The value associated with the given key
	 * @throws KeyNotFoundException If the key does not exist in the JsonObject
	 * @throws WrongTypeException If the value is not a Boolean.
	 */
	public Boolean getBoolean(String key) throws KeyNotFoundException, WrongTypeException {
		return (Boolean)this.get(key, Boolean.class);
	}

	/**
	 * Gets the Byte value in the JsonObject associated with the given key.
	 * @param key The key associated with the value to return
	 * @return The value associated with the given key
	 * @throws KeyNotFoundException If the key does not exist in the JsonObject
	 * @throws WrongTypeException If the value is not a Byte.
	 */
	public Byte getByte(String key) throws KeyNotFoundException, WrongTypeException {
		return (Byte)this.get(key, Byte.class);
	}

	/**
	 * Gets the Short value in the JsonObject associated with the given key.
	 * @param key The key associated with the value to return
	 * @return The value associated with the given key
	 * @throws KeyNotFoundException If the key does not exist in the JsonObject
	 * @throws WrongTypeException If the value is not a Short.
	 */
	public Short getShort(String key) throws KeyNotFoundException, WrongTypeException {
		return (Short)this.get(key, Short.class);
	}

	/**
	 * Gets the Long value in the JsonObject associated with the given key.
	 * @param key The key associated with the value to return
	 * @return The value associated with the given key
	 * @throws KeyNotFoundException If the key does not exist in the JsonObject
	 * @throws WrongTypeException If the value is not a Long.
	 */
	public Long getLong(String key) throws KeyNotFoundException, WrongTypeException {
		return (Long)this.get(key, Long.class);
	}
	
	/**
	 * Returns the type of the value associated with the given key.
	 * @param key The key associated with the value
	 * @return The type of the value associated with the given key
	 * @throws KeyNotFoundException If the key does not exist in the JsonObject.
	 */
	public Class<?> getValueType(String key) throws KeyNotFoundException {
		if(!this.containsKey(key)) {
			throw new KeyNotFoundException(key); 
		}
		
		return this.jsonObject.get(key).getClass();
	}
}
