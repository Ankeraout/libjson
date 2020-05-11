package fr.ankeraout.libjson.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import fr.ankeraout.libjson.JsonArray;
import fr.ankeraout.libjson.JsonLexerException;
import fr.ankeraout.libjson.JsonObject;
import fr.ankeraout.libjson.JsonParser;
import fr.ankeraout.libjson.JsonParserException;

public class JsonParserTest {

	@Test
	public void test_number() throws JsonLexerException, JsonParserException {
		String input = "{\"test1\":0,\"test2\":-1,\"test3\":2.0,\"test4\":-3.0,\"test5\":4.0E1,\"test6\":5.0E-1,\"test7\":6E1}";
		
		Object obj = JsonParser.parse(input);

		assertTrue("The returned object is not a JsonObject.", obj instanceof JsonObject);
		
		JsonObject jo = (JsonObject)obj;
		
		assertEquals(0.0, jo.get("test1"));
		assertEquals(-1.0, jo.get("test2"));
		assertEquals(2.0, jo.get("test3"));
		assertEquals(-3.0, jo.get("test4"));
		assertEquals(40.0, jo.get("test5"));
		assertEquals(0.5, jo.get("test6"));
		assertEquals(60.0, jo.get("test7"));
	}
	
	@Test
	public void test_string() throws JsonLexerException, JsonParserException {
		String input = "{\"test1\":\"\\u0065\", \"test2\":\"\\n\"}";
		
		Object obj = JsonParser.parse(input);

		assertTrue("The returned object is not a JsonObject.", obj instanceof JsonObject);
		
		JsonObject jo = (JsonObject)obj;
		
		assertEquals("e", jo.get("test1"));
		assertEquals("\n", jo.get("test2"));
	}
	
	@Test
	public void test_boolean() throws JsonLexerException, JsonParserException {
		String input = "{\"test1\":true, \"test2\":false}";
		
		Object obj = JsonParser.parse(input);

		assertTrue("The returned object is not a JsonObject.", obj instanceof JsonObject);
		
		JsonObject jo = (JsonObject)obj;
		
		assertEquals(true, jo.get("test1"));
		assertEquals(false, jo.get("test2"));
	}
	
	@Test
	public void test_null() throws JsonLexerException, JsonParserException {
		String input = "{\"test1\":null}";
		
		Object obj = JsonParser.parse(input);

		assertTrue("The returned object is not a JsonObject.", obj instanceof JsonObject);
		
		JsonObject jo = (JsonObject)obj;
		
		assertNull(jo.get("test1"));
	}
	
	@Test
	public void test_array() throws JsonLexerException, JsonParserException {
		String input = "{\"test1\":[null]}";
		
		Object obj = JsonParser.parse(input);

		assertTrue("The returned object is not a JsonObject.", obj instanceof JsonObject);
		
		JsonObject jo = (JsonObject)obj;
		
		assertTrue(jo.get("test1") instanceof JsonArray);
		
		JsonArray array = (JsonArray)jo.get("test1");
		
		assertNull(array.get(0));
	}
	
	@Test
	public void test_array2() throws JsonLexerException, JsonParserException {
		String input = "[null]";
		
		Object obj = JsonParser.parse(input);

		assertTrue("The returned object is not a JsonArray.", obj instanceof JsonArray);
		
		JsonArray ja = (JsonArray)obj;
		
		assertNull(ja.get(0));
	}
	
	@Test
	public void test_array_of_string() throws JsonLexerException, JsonParserException {
		String input = "[\"a\",\"b\"]";
		
		Object obj = JsonParser.parse(input);

		assertTrue("The returned object is not a JsonArray.", obj instanceof JsonArray);
		
		JsonArray ja = (JsonArray)obj;
		
		assertEquals("a", ja.get(0));
		assertEquals("b", ja.get(1));
	}
	
	@Test
	public void test_array_of_objects() throws JsonLexerException, JsonParserException {
		String input = "[{\"a\":\"correct\"},{\"b\":true}]";
		
		Object obj = JsonParser.parse(input);

		assertTrue("The returned object is not a JsonArray.", obj instanceof JsonArray);
		
		JsonArray ja = (JsonArray)obj;

		assertTrue(ja.get(0) instanceof JsonObject);
		assertTrue(ja.get(1) instanceof JsonObject);
		
		JsonObject[] jo = new JsonObject[] { (JsonObject)ja.get(0), (JsonObject)ja.get(1) };
		
		assertEquals("correct", jo[0].get("a"));
		assertEquals(true, jo[1].get("b"));
	}
	
	@Test
	public void test_array_empty() throws JsonLexerException, JsonParserException {
		String input = "[]";
		
		Object obj = JsonParser.parse(input);

		assertTrue("The returned object is not a JsonArray.", obj instanceof JsonArray);
		
		JsonArray ja = (JsonArray)obj;

		assertEquals(0, ja.size());
	}
	
	@Test
	public void test_array_of_arrays() throws JsonLexerException, JsonParserException {
		String input = "[[\"a\"],[3,3.14],[]]";
		
		Object obj = JsonParser.parse(input);

		assertTrue("The returned object is not a JsonArray.", obj instanceof JsonArray);
		
		JsonArray ja = (JsonArray)obj;
		
		assertEquals(3, ja.size());

		assertTrue(ja.get(0) instanceof JsonArray);
		assertTrue(ja.get(1) instanceof JsonArray);
		assertTrue(ja.get(2) instanceof JsonArray);
		
		JsonArray[] jo = new JsonArray[] { (JsonArray)ja.get(0), (JsonArray)ja.get(1), (JsonArray)ja.get(2) };
		
		assertEquals(1, jo[0].size());
		assertEquals(2, jo[1].size());
		assertEquals(0, jo[2].size());
		assertEquals(1, jo[0].size());
		assertEquals(2, jo[1].size());
		assertEquals("a", jo[0].get(0));
		assertEquals(3.0, jo[1].get(0));
		assertEquals(3.14, jo[1].get(1));
	}
	
	@Test
	public void test_array_of_null() throws JsonLexerException, JsonParserException {
		String input = "[null, null]";
		
		Object obj = JsonParser.parse(input);

		assertTrue("The returned object is not a JsonArray.", obj instanceof JsonArray);
		
		JsonArray ja = (JsonArray)obj;

		assertEquals(2, ja.size());
		
		assertNull(ja.get(0));
		assertNull(ja.get(1));
	}
	
	@Test
	public void test_empty_array_in_object() throws JsonLexerException, JsonParserException {
		String input = "{\"test1\":[]}";
		
		Object obj = JsonParser.parse(input);

		assertTrue("The returned object is not a JsonObject.", obj instanceof JsonObject);
		
		JsonObject jo = (JsonObject)obj;
		
		assertTrue(jo.get("test1") instanceof JsonArray);
		
		JsonArray ja = (JsonArray)jo.get("test1");
		
		assertEquals(0, ja.size());
	}
	
	@Test
	public void test_empty_object() throws JsonLexerException, JsonParserException {
		String input = "{}";
		
		Object obj = JsonParser.parse(input);

		assertTrue("The returned object is not a JsonObject.", obj instanceof JsonObject);
	}
	
	@Test
	public void test_empty_object_in_object() throws JsonLexerException, JsonParserException {
		String input = "{\"test1\":{}}";
		
		Object obj = JsonParser.parse(input);

		assertTrue("The returned object is not a JsonObject.", obj instanceof JsonObject);
		
		JsonObject jo = (JsonObject)obj;
		
		assertTrue(jo.get("test1") instanceof JsonObject);
	}
	
	@Test
	public void test_empty_object_in_array() throws JsonLexerException, JsonParserException {
		String input = "[{}]";
		
		Object obj = JsonParser.parse(input);

		assertTrue("The returned object is not a JsonArray.", obj instanceof JsonArray);
		
		JsonArray ja = (JsonArray)obj;
		
		assertTrue(ja.get(0) instanceof JsonObject);
	}
	
	@Test
	public void test_object_in_object() throws JsonLexerException, JsonParserException {
		String input = "{\"a\":{\"b\":null}}";
		
		Object obj = JsonParser.parse(input);

		assertTrue("The returned object is not a JsonObject.", obj instanceof JsonObject);
		
		JsonObject ja = (JsonObject)obj;
		
		assertTrue(ja.get("a") instanceof JsonObject);
	}
	
	@Test
	public void test_example_1() throws JsonLexerException, JsonParserException {
		// Example from https://json.org/example.html
		String input = "{\n" + 
			"    \"glossary\": {\n" + 
			"        \"title\": \"example glossary\",\n" + 
			"		\"GlossDiv\": {\n" + 
			"            \"title\": \"S\",\n" + 
			"			\"GlossList\": {\n" + 
			"                \"GlossEntry\": {\n" + 
			"                    \"ID\": \"SGML\",\n" + 
			"					\"SortAs\": \"SGML\",\n" + 
			"					\"GlossTerm\": \"Standard Generalized Markup Language\",\n" + 
			"					\"Acronym\": \"SGML\",\n" + 
			"					\"Abbrev\": \"ISO 8879:1986\",\n" + 
			"					\"GlossDef\": {\n" + 
			"                        \"para\": \"A meta-markup language, used to create markup languages such as DocBook.\",\n" + 
			"						\"GlossSeeAlso\": [\"GML\", \"XML\"]\n" + 
			"                    },\n" + 
			"					\"GlossSee\": \"markup\"\n" + 
			"                }\n" + 
			"            }\n" + 
			"        }\n" + 
			"    }\n" + 
			"}";
		
		// This should not throw any exception.
		JsonParser.parse(input);
	}
	
	@Test
	public void test_example_2() throws JsonLexerException, JsonParserException {
		// Example from https://json.org/example.html
		String input = "{\"menu\": {\n" + 
				"  \"id\": \"file\",\n" + 
				"  \"value\": \"File\",\n" + 
				"  \"popup\": {\n" + 
				"    \"menuitem\": [\n" + 
				"      {\"value\": \"New\", \"onclick\": \"CreateNewDoc()\"},\n" + 
				"      {\"value\": \"Open\", \"onclick\": \"OpenDoc()\"},\n" + 
				"      {\"value\": \"Close\", \"onclick\": \"CloseDoc()\"}\n" + 
				"    ]\n" + 
				"  }\n" + 
				"}}";
		
		// This should not throw any exception.
		JsonParser.parse(input);
	}
	
	@Test
	public void test_example_3() throws JsonLexerException, JsonParserException {
		// Example from https://json.org/example.html
		String input = "{\"widget\": {\n" + 
				"    \"debug\": \"on\",\n" + 
				"    \"window\": {\n" + 
				"        \"title\": \"Sample Konfabulator Widget\",\n" + 
				"        \"name\": \"main_window\",\n" + 
				"        \"width\": 500,\n" + 
				"        \"height\": 500\n" + 
				"    },\n" + 
				"    \"image\": { \n" + 
				"        \"src\": \"Images/Sun.png\",\n" + 
				"        \"name\": \"sun1\",\n" + 
				"        \"hOffset\": 250,\n" + 
				"        \"vOffset\": 250,\n" + 
				"        \"alignment\": \"center\"\n" + 
				"    },\n" + 
				"    \"text\": {\n" + 
				"        \"data\": \"Click Here\",\n" + 
				"        \"size\": 36,\n" + 
				"        \"style\": \"bold\",\n" + 
				"        \"name\": \"text1\",\n" + 
				"        \"hOffset\": 250,\n" + 
				"        \"vOffset\": 100,\n" + 
				"        \"alignment\": \"center\",\n" + 
				"        \"onMouseUp\": \"sun1.opacity = (sun1.opacity / 100) * 90;\"\n" + 
				"    }\n" + 
				"}}    ";
		
		// This should not throw any exception.
		JsonParser.parse(input);
	}
	
	@Test
	public void test_example_4() throws JsonLexerException, JsonParserException {
		// Example from https://json.org/example.html
		String input = "{\"web-app\": {\n" + 
				"  \"servlet\": [   \n" + 
				"    {\n" + 
				"      \"servlet-name\": \"cofaxCDS\",\n" + 
				"      \"servlet-class\": \"org.cofax.cds.CDSServlet\",\n" + 
				"      \"init-param\": {\n" + 
				"        \"configGlossary:installationAt\": \"Philadelphia, PA\",\n" + 
				"        \"configGlossary:adminEmail\": \"ksm@pobox.com\",\n" + 
				"        \"configGlossary:poweredBy\": \"Cofax\",\n" + 
				"        \"configGlossary:poweredByIcon\": \"/images/cofax.gif\",\n" + 
				"        \"configGlossary:staticPath\": \"/content/static\",\n" + 
				"        \"templateProcessorClass\": \"org.cofax.WysiwygTemplate\",\n" + 
				"        \"templateLoaderClass\": \"org.cofax.FilesTemplateLoader\",\n" + 
				"        \"templatePath\": \"templates\",\n" + 
				"        \"templateOverridePath\": \"\",\n" + 
				"        \"defaultListTemplate\": \"listTemplate.htm\",\n" + 
				"        \"defaultFileTemplate\": \"articleTemplate.htm\",\n" + 
				"        \"useJSP\": false,\n" + 
				"        \"jspListTemplate\": \"listTemplate.jsp\",\n" + 
				"        \"jspFileTemplate\": \"articleTemplate.jsp\",\n" + 
				"        \"cachePackageTagsTrack\": 200,\n" + 
				"        \"cachePackageTagsStore\": 200,\n" + 
				"        \"cachePackageTagsRefresh\": 60,\n" + 
				"        \"cacheTemplatesTrack\": 100,\n" + 
				"        \"cacheTemplatesStore\": 50,\n" + 
				"        \"cacheTemplatesRefresh\": 15,\n" + 
				"        \"cachePagesTrack\": 200,\n" + 
				"        \"cachePagesStore\": 100,\n" + 
				"        \"cachePagesRefresh\": 10,\n" + 
				"        \"cachePagesDirtyRead\": 10,\n" + 
				"        \"searchEngineListTemplate\": \"forSearchEnginesList.htm\",\n" + 
				"        \"searchEngineFileTemplate\": \"forSearchEngines.htm\",\n" + 
				"        \"searchEngineRobotsDb\": \"WEB-INF/robots.db\",\n" + 
				"        \"useDataStore\": true,\n" + 
				"        \"dataStoreClass\": \"org.cofax.SqlDataStore\",\n" + 
				"        \"redirectionClass\": \"org.cofax.SqlRedirection\",\n" + 
				"        \"dataStoreName\": \"cofax\",\n" + 
				"        \"dataStoreDriver\": \"com.microsoft.jdbc.sqlserver.SQLServerDriver\",\n" + 
				"        \"dataStoreUrl\": \"jdbc:microsoft:sqlserver://LOCALHOST:1433;DatabaseName=goon\",\n" + 
				"        \"dataStoreUser\": \"sa\",\n" + 
				"        \"dataStorePassword\": \"dataStoreTestQuery\",\n" + 
				"        \"dataStoreTestQuery\": \"SET NOCOUNT ON;select test='test';\",\n" + 
				"        \"dataStoreLogFile\": \"/usr/local/tomcat/logs/datastore.log\",\n" + 
				"        \"dataStoreInitConns\": 10,\n" + 
				"        \"dataStoreMaxConns\": 100,\n" + 
				"        \"dataStoreConnUsageLimit\": 100,\n" + 
				"        \"dataStoreLogLevel\": \"debug\",\n" + 
				"        \"maxUrlLength\": 500}},\n" + 
				"    {\n" + 
				"      \"servlet-name\": \"cofaxEmail\",\n" + 
				"      \"servlet-class\": \"org.cofax.cds.EmailServlet\",\n" + 
				"      \"init-param\": {\n" + 
				"      \"mailHost\": \"mail1\",\n" + 
				"      \"mailHostOverride\": \"mail2\"}},\n" + 
				"    {\n" + 
				"      \"servlet-name\": \"cofaxAdmin\",\n" + 
				"      \"servlet-class\": \"org.cofax.cds.AdminServlet\"},\n" + 
				" \n" + 
				"    {\n" + 
				"      \"servlet-name\": \"fileServlet\",\n" + 
				"      \"servlet-class\": \"org.cofax.cds.FileServlet\"},\n" + 
				"    {\n" + 
				"      \"servlet-name\": \"cofaxTools\",\n" + 
				"      \"servlet-class\": \"org.cofax.cms.CofaxToolsServlet\",\n" + 
				"      \"init-param\": {\n" + 
				"        \"templatePath\": \"toolstemplates/\",\n" + 
				"        \"log\": 1,\n" + 
				"        \"logLocation\": \"/usr/local/tomcat/logs/CofaxTools.log\",\n" + 
				"        \"logMaxSize\": \"\",\n" + 
				"        \"dataLog\": 1,\n" + 
				"        \"dataLogLocation\": \"/usr/local/tomcat/logs/dataLog.log\",\n" + 
				"        \"dataLogMaxSize\": \"\",\n" + 
				"        \"removePageCache\": \"/content/admin/remove?cache=pages&id=\",\n" + 
				"        \"removeTemplateCache\": \"/content/admin/remove?cache=templates&id=\",\n" + 
				"        \"fileTransferFolder\": \"/usr/local/tomcat/webapps/content/fileTransferFolder\",\n" + 
				"        \"lookInContext\": 1,\n" + 
				"        \"adminGroupID\": 4,\n" + 
				"        \"betaServer\": true}}],\n" + 
				"  \"servlet-mapping\": {\n" + 
				"    \"cofaxCDS\": \"/\",\n" + 
				"    \"cofaxEmail\": \"/cofaxutil/aemail/*\",\n" + 
				"    \"cofaxAdmin\": \"/admin/*\",\n" + 
				"    \"fileServlet\": \"/static/*\",\n" + 
				"    \"cofaxTools\": \"/tools/*\"},\n" + 
				" \n" + 
				"  \"taglib\": {\n" + 
				"    \"taglib-uri\": \"cofax.tld\",\n" + 
				"    \"taglib-location\": \"/WEB-INF/tlds/cofax.tld\"}}}";
		
		// This should not throw any exception.
		JsonParser.parse(input);
	}
	
	@Test
	public void test_example_5() throws JsonLexerException, JsonParserException {
		// Example from https://json.org/example.html
		String input = "{\"menu\": {\n" + 
				"    \"header\": \"SVG Viewer\",\n" + 
				"    \"items\": [\n" + 
				"        {\"id\": \"Open\"},\n" + 
				"        {\"id\": \"OpenNew\", \"label\": \"Open New\"},\n" + 
				"        null,\n" + 
				"        {\"id\": \"ZoomIn\", \"label\": \"Zoom In\"},\n" + 
				"        {\"id\": \"ZoomOut\", \"label\": \"Zoom Out\"},\n" + 
				"        {\"id\": \"OriginalView\", \"label\": \"Original View\"},\n" + 
				"        null,\n" + 
				"        {\"id\": \"Quality\"},\n" + 
				"        {\"id\": \"Pause\"},\n" + 
				"        {\"id\": \"Mute\"},\n" + 
				"        null,\n" + 
				"        {\"id\": \"Find\", \"label\": \"Find...\"},\n" + 
				"        {\"id\": \"FindAgain\", \"label\": \"Find Again\"},\n" + 
				"        {\"id\": \"Copy\"},\n" + 
				"        {\"id\": \"CopyAgain\", \"label\": \"Copy Again\"},\n" + 
				"        {\"id\": \"CopySVG\", \"label\": \"Copy SVG\"},\n" + 
				"        {\"id\": \"ViewSVG\", \"label\": \"View SVG\"},\n" + 
				"        {\"id\": \"ViewSource\", \"label\": \"View Source\"},\n" + 
				"        {\"id\": \"SaveAs\", \"label\": \"Save As\"},\n" + 
				"        null,\n" + 
				"        {\"id\": \"Help\"},\n" + 
				"        {\"id\": \"About\", \"label\": \"About Adobe CVG Viewer...\"}\n" + 
				"    ]\n" + 
				"}}";
		
		// This should not throw any exception.
		JsonParser.parse(input);
	}
	
	@Test(expected = JsonParserException.class)
	public void test_exception_dataAfterEnd() throws JsonLexerException, JsonParserException {
		JsonParser.parse("{}true");
	}
	
	@Test(expected = JsonParserException.class)
	public void test_exception_start() throws JsonLexerException, JsonParserException {
		JsonParser.parse("true");
	}
	
	@Test(expected = JsonParserException.class)
	public void test_exception_arrayBadSyntax() throws JsonLexerException, JsonParserException {
		JsonParser.parse("[}");
	}
	
	@Test(expected = JsonParserException.class)
	public void test_exception_arrayBadSyntax_2() throws JsonLexerException, JsonParserException {
		JsonParser.parse("[true false]");
	}
	
	@Test(expected = JsonParserException.class)
	public void test_exception_arrayBadSyntax_3() throws JsonLexerException, JsonParserException {
		JsonParser.parse("[true, }]");
	}
	
	@Test(expected = JsonParserException.class)
	public void test_exception_objectBadSyntax() throws JsonLexerException, JsonParserException {
		JsonParser.parse("{true}");
	}
	
	@Test(expected = JsonParserException.class)
	public void test_exception_objectBadSyntax_2() throws JsonLexerException, JsonParserException {
		JsonParser.parse("{\"key\" \"value\"}");
	}
	
	@Test(expected = JsonParserException.class)
	public void test_exception_objectBadSyntax_3() throws JsonLexerException, JsonParserException {
		JsonParser.parse("{\"key\":}");
	}
	
	@Test(expected = JsonParserException.class)
	public void test_exception_objectBadSyntax_4() throws JsonLexerException, JsonParserException {
		JsonParser.parse("{\"key\":3 true}");
	}
	
	@Test(expected = JsonParserException.class)
	public void test_exception_objectBadSyntax_5() throws JsonLexerException, JsonParserException {
		JsonParser.parse("{\"key\":3, true}");
	}
	
	@Test(expected = JsonParserException.class)
	public void test_exception_objectBadSyntax_6() throws JsonLexerException, JsonParserException {
		JsonParser.parse("{\"key\"");
	}
	
}
