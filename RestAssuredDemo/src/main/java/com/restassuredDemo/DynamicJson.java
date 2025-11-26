package com.restassuredDemo;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.Payload;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJson {

	String ID = "";
	@Test(dataProvider = "booksData")
	public void addBook(String isbn, String aisle) {
		RestAssured.baseURI = "http://216.10.245.166";
		
		String response  = given().header("Content-Type","application/json").body(Payload.addBook(isbn,aisle)).when()
		.post("Library/Addbook.php").then().assertThat().statusCode(200).extract().response().asString();
		System.out.println(response);
		
		JsonPath js = new JsonPath(response);
		String Id = js.get("ID");
		ID = Id;
		System.out.println(Id);
	}
	
	@DataProvider(name = "booksData")
	public Object[][] getData() {
		//Array - Collection of elements
		//Multidimensional array - Collection of arrays
		Object[][] books = new Object[][] {{"jjjvh","987"},{"rghf","876"},{"poiy","123"}};
		return books;
	}
//	@Test
//	public void deleteBook() {
//		RestAssured.baseURI = "http://216.10.245.166";
//		given().header("Content-Type","application/json").body("\n"
//				+ " \n"
//				+ "\"ID\" : \""+ID+"\n"
//				+ " \n"
//				+ "}")
//		.when().post("/Library/DeleteBook.php")
//		.then().assertThat().statusCode(200);
//		
//	}
}
