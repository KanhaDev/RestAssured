package files;

public class Payload {

	public static String addPlace() {
		return "{\n" + "  \"location\": {\n" + "    \"lat\": -38.383494,\n" + "    \"lng\": 33.427362\n" + "  },\n"
				+ "  \"accuracy\": 50,\n" + "  \"name\": \"Rahul House\",\n"
				+ "  \"phone_number\": \"(+91) 9876678876\",\n"
				+ "  \"address\": \"3rd floor, JahangirNagar, Patna\",\n" + "  \"types\": [\n"
				+ "    \"Kirana shop\",\n" + "    \"shop\"\n" + "  ],\n" + "  \"website\": \"http://google.com\",\n"
				+ "  \"language\": \"Bhojpuri\"\n" + "}\n" + "";
	}

	public static String putPlace(String placeId,String address) {
		return "{\n" + "\"place_id\":\"" + placeId + "\",\n" + "\"name\":\"KLova\",\n"
				+ "\"address\":\""+address+"\",\n" + "\"key\":\"qaclick123\"\n" + "}";
	}
	
	public static String coursePrice() {
		return "{\n"
				+ "  \"dashboard\":{\n"
				+ "    \"purchaseAmount\" : 910,\n"
				+ "    \"website\" : \"rahulshettyacademy.com\"\n"
				+ "  },\n"
				+ "  \"courses\": [\n"
				+ "    {\n"
				+ "      \"title\" : \"Selenium Python\",\n"
				+ "      \"price\" : 50,\n"
				+ "      \"copies\" : 6\n"
				+ "    },\n"
				+ "    {\n"
				+ "      \"title\" : \"Cypress\",\n"
				+ "      \"price\" : 40,\n"
				+ "      \"copies\" : 4\n"
				+ "    },\n"
				+ "    {\n"
				+ "      \"title\" : \"RPA\",\n"
				+ "      \"price\" : 45,\n"
				+ "      \"copies\" : 10\n"
				+ "    }\n"
				+ "  ]\n"
				+ "}";
	}
	
	public static String addBook(String isbn,String aisle) {
		return "{\n"
				+ "\n"
				+ "\"name\":\"Learn RestAssured with Java-TestNG\",\n"
				+ "\"isbn\":\""+isbn+"\",\n"
				+ "\"aisle\":\""+aisle+"\",\n"
				+ "\"author\":\"Kanha Verma\"\n"
				+ "}";
	}
	
	public static String createBugPayload() {
		return "{\n"
				+ "    \"fields\": {\n"
				+ "       \"project\":\n"
				+ "       {\n"
				+ "          \"key\": \"SCRUM\"\n"
				+ "       },\n"
				+ "       \"summary\": \"Links is not working- Automation\",\n"
				+ "       \"issuetype\": {\n"
				+ "          \"name\": \"Bug\"\n"
				+ "       }\n"
				+ "   }\n"
				+ "}";
	}
}
