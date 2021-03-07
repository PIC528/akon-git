package com.testerhome.wework.contact;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.testerhome.wework.Api;

import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

class ApiTest {

	@Test
	void templateFromYamltest() {
		Api api = new Api();
		api.templateFromYaml("/api/list.yaml",null);
	}
	@Test
	void request() {
		RequestSpecification req = given().log().all();
		req.queryParam("id", 1);
		req.get("http://www.baidu.com");
	}
	

}
