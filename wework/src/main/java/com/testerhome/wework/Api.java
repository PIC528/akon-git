package com.testerhome.wework;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.testerhome.wework.WeworkConfig;
import com.testerhome.wework.contact.Contact;

import java.io.IOException;
import java.util.*;

import static io.restassured.RestAssured.given;
import io.restassured.specification.RequestSpecification;
//目标：父类完成基础的初始化，所有子类继承过来，再追加自己特有的参数
public class Api {
	HashMap<String ,Object> query = new HashMap<>();
	//借助ResquestSpecification完成基础的初始化，保存resquestSpecification对象，就可以在很多地方复用。
	public RequestSpecification requestSpecification = given();
	public Response set() {
		//找到所有集合，对每一个entry(输入)做一个循环,遍历它的参数。
		requestSpecification =  given().log().all();
		query.entrySet().forEach(entry->{
			requestSpecification.queryParam(entry.getKey(),entry.getValue());
		});
		return requestSpecification.when().request("get","baidu.com");
	}
	//对于复杂的JSON，提供一个模板封装方法
	//给一个模板（文件），hashmap里存储着需要的值
	public static String template(String path, HashMap<String,Object> map) {
		DocumentContext  documentContext = JsonPath.parse(Api.class
				.getResourceAsStream(path));
		map.entrySet().forEach(entry->{
			documentContext.set(entry.getKey(), entry.getValue());
		});
		return documentContext.jsonString();
	}
	
	public  Response templateFromHar(String path,String urlPath, HashMap<String,Object> map) {
		//todo:支持从har自动生成接口定义并发送
		//path给定了HAR文件的路径
		//urlPath表明要取HAR文件里具体哪个接口的数据，比方说新建部门就是addparty这个接口
		//step1.从HAR中读取请求，根据map进行更新
		DocumentContext  documentContext = JsonPath.parse(Api.class
				.getResourceAsStream(path));
		map.entrySet().forEach(entry->{
			documentContext.set(entry.getKey(), entry.getValue());
		});
		//step2.将所有的请求数据构造完成（所有的get和post在里面也是有的）
		//在json文件中找到request的部分，获得method等数据
		//read:根据给定的文件，找到需要的内容
		String method = documentContext.read("method");
		String url = documentContext.read("url");
		return requestSpecification.when().request(method,url);
		//选择request是因为它没有具体要求是get或者post
	}
	public  Response templateFromSwagger(String path,String urlPath, HashMap<String,Object> map) {
		//todo:支持从swagger自动生成接口定义并发送
		//与har一样，只不过读的是swagger.json文件
		//这里的swagger文件并不是企业微信的，只是一个模板，这个方法也只是暂写
		DocumentContext  documentContext = JsonPath.parse(Api.class
				.getResourceAsStream(path));
		map.entrySet().forEach(entry->{
			documentContext.set(entry.getKey(), entry.getValue());
		});
		String method = documentContext.read("method");
		String url = documentContext.read("url");
		return requestSpecification.when().request(method,url);
	}
	
	public  Response templateFromYaml(String path,HashMap<String,Object> map) {
		//todo:根据yaml生成接口定义并发送
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		//获取实例并以yaml格式打印出来
			try {
				//读取一个默认的配置文件，交给WeworkConfig的一个实例，并把实例返回
				Restful restful = mapper.readValue(WeworkConfig.class.getResourceAsStream(path),Restful.class);
				//如果方法是get，就把map替换成get值，即getvalue
				if(restful.method.toLowerCase().contains("get")) {
					map.entrySet().forEach(entry->{
					restful.query.replace(entry.getKey(), entry.getValue().toString());
 				});
				}
				//借助这个方法重新拼装一个接口请求
				//借助foreach不断追加参数
				restful.query.entrySet().forEach(entry->{
						this.requestSpecification = 
						this.requestSpecification.queryParam(entry.getKey(), entry.getValue());
						//为保证传入的参数都被存储下来，所以需要重新追加赋值，
						//也就是“this.requestSpecification =”这部分的作业
				});
				//上面就以及获取了所有需要的参数，接下来就要发送请求了
				//需要方法和url
				return this.requestSpecification.log().all().request(restful.method,restful.url)
						.then().log().all().extract().response();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//如果文件不存在，抛出异常
				return null;
			}
			
	}
	
	public Response readApiFromYaml(String path, HashMap<String,Object> map) {
		//todo:动态调用
		return null;
	}
}
