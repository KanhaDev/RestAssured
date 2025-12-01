package com.restassuredDemo;

import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.LoginRequest;
import pojo.LoginResponse;
import pojo.Order;
import pojo.OrderRequest;
import pojo.OrderResponse;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ECommerceAPITest {

	@Test
	public void generateToken() {

		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setBasePath("api/ecom").setContentType(ContentType.JSON).build();
		
		ResponseSpecification res  = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		LoginRequest login = new LoginRequest();
		login.setUserEmail("kanhaiyaverma7255@gmail.com");
		login.setUserPassword("Abhishek123");
		
		LoginResponse  response  = given().log().all().spec(req).body(login).when().post("auth/login")
		.then().log().all().spec(res).extract().response().as(LoginResponse.class);
		
		String token = response.getToken();
		String userId = response.getUserId();
		
		System.out.println("Token for Login: "+token);
		System.out.println("User Id: "+userId);
		
		//Create Product
		
		RequestSpecification createProductReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setBasePath("api/ecom").addHeader("Authorization", token).build();
		
		Response responseProductCreated  = given().log().all().spec(createProductReq).formParam("productName", "Brand New Laptop").formParam("productAddedBy", userId)
		.formParam("productCategory", "Electronics").formParam("productSubCategory", "Laptop")
		.formParam("productPrice", "51200").formParam("productDescription", "Apple MackBook")
		.formParam("productFor", "men")
		.multiPart("productImage",new File("/Users/kanhaiya/Desktop/laptopImage.png"))
		.when().post("product/add-product").then().log().all()
		.extract().response();
		
		JsonPath jsPath = new JsonPath(responseProductCreated.asString());
		
		String productId = jsPath.getString("productId");
		System.out.println("Product Id: "+ productId);
		
		//Create Order
		
		Order orders = new Order();
		orders.setCountry("India");
		orders.setProductOrderedId(productId);
		List<Order> orderList = new ArrayList<>();
		orderList.add(orders);
		OrderRequest orderReq = new OrderRequest();
		orderReq.setOrders(orderList);
		
		RequestSpecification createOrderReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setBasePath("api/ecom").setContentType(ContentType.JSON).addHeader("Authorization", token).build();
		
		ResponseSpecification createOrderRes = new ResponseSpecBuilder().expectContentType(ContentType.JSON).expectStatusCode(201).expectBody("productOrderId[0]", equalTo(productId)).build();
		
		OrderResponse orderResponse  = given().log().all().spec(createOrderReq).body(orderReq)
		.when().post("order/create-order")
		.then().spec(createOrderRes).log().all().extract().as(OrderResponse.class);
		
		String orderId = orderResponse.getOrders().get(0);
		
		String actualProductId = orderResponse.getProductOrderId().get(0);
		
		System.out.println("Order Id: "+ orderId);
		System.out.println("Product Id: "+ actualProductId);
		
		
		//Delete Order
		
		RequestSpecification deleteOrderReqSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setBasePath("api/ecom").addHeader("Authorization", token).build();
		ResponseSpecification deleteOrderResSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
		
		RequestSpecification deleteOrderReq = given().spec(deleteOrderReqSpec).log().all().pathParam("orderId", orderId);
		
		String deleteOrder = deleteOrderReq.when().delete("order/delete-order/{orderId}")
		.then().spec(deleteOrderResSpec).extract().response().asString();
		
		System.out.println(deleteOrder);
		
		//Delete Product
		
		RequestSpecification deleteProductReqSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setBasePath("api/ecom").addHeader("Authorization", token).build();
		ResponseSpecification deleteProductResSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
		
		RequestSpecification deleteProductReq = given().spec(deleteProductReqSpec).log().all().pathParam("productId", productId);
		
		String deleteProduct = deleteProductReq.when().delete("product/delete-product/{productId}")
		.then().spec(deleteProductResSpec).extract().response().asString();
		
		System.out.println(deleteProduct);
		
		
		
		
	}
}
