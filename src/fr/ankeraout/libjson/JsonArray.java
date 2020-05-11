package fr.ankeraout.libjson;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class represents a JSON array of data. All the elements in the array are ordered.
 * @author Ankeraout
 *
 */
public class JsonArray extends ArrayList<Object> {
	private static final long serialVersionUID = 4846506197285896705L;
	
	/**
	 * Creates a new empty JSON array
	 */
	public JsonArray() {
		super();
	}
	
	/**
	 * Creates a new JSON array and adds the elements of the given collection in it.
	 * @param c
	 */
	public JsonArray(Collection<Object> c) {
		super(c);
	}
	
	/**
	 * Returns a string representation of the contents of the array. This representation can be
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
	 * @param newLines Defines whether or not the JSON representation of the array will be
	 * formatted so that it is more readable. If this is set to false, the string representation of
	 * the array that will be returned will have no newline characters, and will be of minimum
	 * length. The value of indentationLevel and indentString parameters will also have no impact.
	 * @return A JSON/String representation of the contents of the array.
	 */
	public String toString(int indentationLevel, String indentString, boolean newLines) {
		if(newLines && indentString == null) {
			indentString = new String();
		}
		
		String tmpstr = new String();
		
		tmpstr += '[';
		
		if(newLines) {
			tmpstr += '\n';
		}
		
		for(int i = 0; i < this.size(); i++) {
			Object o = this.get(i);
			
			if(newLines) {
				for(int j = 0; j < indentationLevel + 1; j++) {
					tmpstr += indentString;
				}
			}
			
			tmpstr += JsonStringUtils.toString(o, indentationLevel + 1, indentString, newLines);
			
			if(i < this.size() - 1) {
				tmpstr += ',';
			}
			
			if(newLines) {
				tmpstr += '\n';
			}
		}
		
		if(newLines) {
			for(int i = 0; i < indentationLevel; i++) {
				tmpstr += indentString;
			}
		}
		
		tmpstr += ']';
		
		return tmpstr;
	}
	
	private static void checkElementType(Object element) {
		// Check type of value
		if(
			element != null
			&& !(element instanceof Character)
			&& !(element instanceof JsonObject)
			&& !(element instanceof JsonArray)
			&& !(element instanceof String)
			&& !(element instanceof Integer)
			&& !(element instanceof Double)
			&& !(element instanceof Float)
			&& !(element instanceof Boolean)
			&& !(element instanceof Byte)
			&& !(element instanceof Short)
			&& !(element instanceof Long)
		) {
			throw new IllegalArgumentException("Parameter type not allowed: " + element.getClass().getName());
		}
	}
	
	private static void checkElementsType(Collection<? extends Object> elements) {
		for(Object element : elements) {
			JsonArray.checkElementType(element);
		}
	}
	
	@Override
	public void add(int index, Object element) {
		JsonArray.checkElementType(element);
		super.add(index, element);
	}
	
	@Override
	public boolean add(Object e) {
		JsonArray.checkElementType(e);
		return super.add(e);
	}
	
	@Override
	public boolean addAll(Collection<? extends Object> c) {
		JsonArray.checkElementsType(c);
		return super.addAll(c);
	}
	
	@Override
	public boolean addAll(int index, Collection<? extends Object> c) {
		JsonArray.checkElementsType(c);
		return super.addAll(index, c);
	}
	
	@Override
	public String toString() {
		return this.toString(0, null, false);
	}
}
