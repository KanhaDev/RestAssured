package com.restassuredDemo;

import org.testng.annotations.Test;

import files.Payload;

import static io.restassured.RestAssured.*;

import java.io.File;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class BugTest {

	String ID;
	
	@Test
	public void createBug() {

		RestAssured.baseURI = "https://kanhaiyaverma7255.atlassian.net";
		String base64Token = "a2FuaGFpeWF2ZXJtYTcyNTVAZ21haWwuY29tOkFUQVRUM3hGZkdGMFo4X3B1SHBmcTBmYlM0c3J1S0dzcklTaGVrSzVBUlJJQXJKVEtuNVltZ1prMl9rMXN0b3J2WmRfazJFdHBfWnAzeUw0RHRac1ZiM0x6ZW92NnJqMkhoTUFCVGEwaG5SYTVsaVRxVURYckpuLVBMUDVzeC1RRFRSbWMyMHlZLVI4V1JEY05GNEEwbl8xSEZ4TFRpeW16My1DVXVSVVY1Mm5RMmplWVVCLUgtZz01OTc2RkNDQQ==";
		String responseString = given().header("Content-Type", "application/json")
				.header("Authorization", "Basic "+base64Token).body(Payload.createBugPayload()).when()
				.post("/rest/api/3/issue").then().assertThat().statusCode(201).log().all().extract().asString();

		JsonPath js = new JsonPath(responseString);
		ID = js.get("id");
		System.out.println(ID);
		
		String attachmentString = given().pathParam("key", ID)
		.header("X-Atlassian-Token","no-check")
		.header("Authorization", "Basic "+base64Token)
		.multiPart("file",new File("/Users/kanhaiya/Downloads/ImageBackground.jpg"))
		.when().post("rest/api/3/issue/{key}/attachments")
		.then().log().all().assertThat().statusCode(200)
		.extract().asString();
		
		JsonPath js2 = new JsonPath(attachmentString);
		String fileName = js2.getString("filename");
		System.out.println(fileName);
	}
	

}
