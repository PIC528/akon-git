package com.testerhome.wework.contact;

import com.testerhome.wework.Wework;
import com.testerhome.wework.Api;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;
//目标：父类完成基础的初始化，所有子类继承过来，再追加自己特有的参数。
//基类在初始化的时候能够把token等内容处理好
public class Contact extends Api{
	String random = String.valueOf(System.currentTimeMillis());
	public Contact() {
		reset();
	}
	//reset方法：回到最初始的状态
	public void reset() {
		requestSpecification = given()
		.log().all()
		.queryParam("access_token", Wework.obtainToken())
		.contentType(ContentType.JSON);
	}
}
