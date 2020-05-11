package fr.ankeraout.libjson;

/**
 * This class contains utilities for transforming a JSON array or object into its String form.
 * @author Ankeraout
 *
 */
public final class JsonStringUtils {
	/**
	 * Private constructor to prevent class instantiation.
	 */
	private JsonStringUtils() {
		
	}
	
	/**
	 * Transforms the given object into its string representation. This method is used by
	 * JsonObject and JsonArray classes to create the String representation of object or array
	 * values.
	 * @param o The object to convert to its String representation
	 * @param indentationLevel The indentation level of the string representation
	 * @param indentString The string representing one level of indentation
	 * @param newLines Whether or not the string representation of the object given in parameter
	 * should have new line characters in the returned value.
	 * @return The string representation of the given object.
	 */
	public static String toString(Object o, int indentationLevel, String indentString, boolean newLines) {
		StringBuilder sb = new StringBuilder();
		
		if(o instanceof Character) {
			char c = (char)o;
			o = "" + c;
		}
		
		if(o == null) {
			sb.append("null");
		} else if(o instanceof JsonObject) {
			sb.append(((JsonObject)o).toString(indentationLevel, indentString, newLines));
		} else if(o instanceof JsonArray) {
			sb.append(((JsonArray)o).toString(indentationLevel, indentString, newLines));
		} else if(o instanceof String) {
			sb.append('"');
			
			for(char c : ((String)o).toCharArray()) {
				if(c == '"') {
					sb.append("\\\"");
				} else if(c == '\\') {
					sb.append("\\\\");
				} else if(c == '/') {
					sb.append("\\/");
				} else if(c == '\b') {
					sb.append("\\b");
				} else if(c == '\f') {
					sb.append("\\f");
				} else if(c == '\n') {
					sb.append("\\n");
				} else if(c == '\r') {
					sb.append("\\r");
				} else if(c == '\t') {
					sb.append("\\t");
				} else if((c >= 0x0000 && c <= 0x001f) || c == 0x007f || (c >= 0x0080 && c <= 0x009f)) {
					sb.append("\\u");
					
					for(int i = 0; i < 4; i++) {
						int digit = (c >> (4 * (3 - i))) & 0x0f;
						sb.append("0123456789abcdef".charAt(digit));
					}
				} else {
					sb.append(c);
				}
			}
			
			sb.append('"');
		} else if(o instanceof Integer || o instanceof Double || o instanceof Float || o instanceof Boolean || o instanceof Byte || o instanceof Short || o instanceof Long) {
			sb.append(o.toString());
		}
		
		return sb.toString();
	}
}
