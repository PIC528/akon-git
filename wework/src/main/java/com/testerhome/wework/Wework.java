package com.testerhome.wework;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import io.restassured.response.Response;

//import io.restassured.RestAssured;

public class Wework {
	 private static String token;
	 public  static String getToken(String secret) {
		 return given().log().all()
				 .queryParam("corpid",WeworkConfig.getInstance().corpid)
				 .queryParam("corpsecret",secret)
				 .when().get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
				 .then().log().all().statusCode(200).body("errcode", equalTo(0))
				 .extract().path("access_token");
	 }
	 //通讯录的secret和企业微信不一样，需要另外获取token
	 public  static String getContactToken(String contactsecret) {
		 return given().log().all()
				 .queryParam("corpid",WeworkConfig.getInstance().corpid)
				 .queryParam("corpsecret", contactsecret)
				 .when().get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
				 .then().log().all().statusCode(200).body("errcode", equalTo(0))
				 .extract().path("access_token");
	 }
	 //由于其他活动会不断需要token值，因此需要一个方法，将token保存下来，而不必每次进行一次获取
	 //这里仍然可以使用单例模式
	 //通过静态方法把token封装起来
	 public static String obtainToken() {
		 //这里因为要用通讯录，先传过去通讯录的token,后面会做重构
		 //下一步任务todo:支持多个token选择
		 if(token == null) {
			 token = getToken(WeworkConfig.getInstance().contactSecret);
		 }
		 return token;
	 }
}
