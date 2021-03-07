 package com.testerhome.wework.contact;
import static io.restassured.RestAssured.given;

import java.util.*;

import com.jayway.jsonpath.JsonPath;
import com.testerhome.wework.Wework;
import com.testerhome.wework.WeworkConfig;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
//部门管理
public class Department extends Contact{
	public Response list(String id) {
		//reset();
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		//继承contact后，可以直接使用requestSpecification进行初始化，进行获取token的步骤
		//后面可以用可以自己继续追加参数
		return templateFromYaml("/api/list.yaml", map);
	}
	/*
	public Response list(String id) {
		String token = Wework.obtainToken();
		//继承contact后，可以直接使用requestSpecification进行初始化，进行获取token的步骤
		//后面可以用可以自己继续追加参数
		Response response = requestSpecification
				.queryParam("id", id)
		.when().get("https://qyapi.weixin.qq.com/cgi-bin/department/list")
		.then().extract().response();
		reset();
		return response;
		
		//return given().log().all()
			//	.param("access_token",token)
				//.param("id", id)
		//.when().get("https://qyapi.weixin.qq.com/cgi-bin/department/list")
		//.then().log().all().extract().response();
		
	}
	*/
	//创建部门
	public Response create(String name,String parentid) {
		/*
		String body = JsonPath.parse(this.getClass()
				.getResourceAsStream("/data/create.json"))
				.set("$.name", name)
				.set("$.parentid", parentid).jsonString();
				//return given().log().all()
					//contentType这一行，是修改编码格式，使得中文字符也可以创建成功
				//.contentType(ContentType.JSON)
				//.queryParam("access_token", Wework.obtainToken())
		//上面三行替换为requestSpecification
		 */
		//让用例更清晰
		HashMap<String,Object> map = new HashMap<>();
		map.put("name",name);
		map.put("parentid",parentid);
		map.put("_file","/data/create.json");
		//把file加到map里，会自动修改需要的值
		return templateFromYaml("/api/create.yaml", map);		
		/*
		//法二：
		//转成map后，用数据模板读数据，得到body
		String body = template("/data/create.json",map);
		//经过上几步，先用map把模板里的值改好，然后获得修改好的body,再追加到map里，作为参数传到post方法里
		map.put("_body", body);
		return templateFromYaml("/api/create.yaml", map);
		*/
		/*
		return 	getDefaultRequestSpecification()
				//声明默认requet，会自动带上token			
				.body(body)
				.post("https://qyapi.weixin.qq.com/cgi-bin/department/create")
				.then().extract().response();
		//then后的log().all().statusCode(200)也全删掉了
		 */
	}
	//重载
	public Response create(HashMap<String,Object> map) {
		map.put("_file","/data/create.json");
		return templateFromYaml("/api/create.yaml", map);
	}
	
	//更新部门
	public Response update(String id,String name) {
		HashMap<String,Object> map = new HashMap<>();
		map.put("_file","/data/update.json");
		map.put("name",name);
		map.put("id",id);
		//把file加到map里，会自动修改需要的值
		return templateFromYaml("/api/update.yaml", map);
	}
	//重构更新
	public Response update(HashMap<String,Object> map) {
		//HarTemplate是新的解析引擎，解析HAR文件，并拼装成一个个的请求（也就是requestSpecification）,
		//从原来的数据中提取body、url以及请求方式等信息
		//读完后，再用map修改
		/*
		return templateFromHar(
				"demo.har.json",
				"https://work.weixin.qq.com/wework_admin/party?action=addparty",
				map
			);
		*/
		map.put("_file","/data/update.json");
		//把file加到map里，会自动修改需要的值
		return templateFromYaml("/api/create.yaml", map);


	}
	//删除部门
	public Response delete(String id) {
		/*
		return getDefaultRequestSpecification()
			.queryParam("id", id)
		.when().get("https://qyapi.weixin.qq.com/cgi-bin/department/delete")
		.then().log().all().extract().response();
		*/
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		return templateFromYaml("/api/delete.yaml", map);
	}
	//删除所有部门
	//首先获取所有存在的列表的id
	//然后迭代删除
	//慎用，不然可能误删。以后在测试时，最好把自己的测试规定在某个部门内进行增删改查，此时可以使用这个函数避免新增过多脏数据
	
	public Response deleteAll() {
		//reset();
		List<Integer> idList = list("")
				.then().log().all()
				.extract().path("department.id");
		//System.out.println(idList);
		idList.forEach(id->delete(id.toString()));
		return null;
	}
	
}
