package com.testerhome.wework;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class templateFromYaml {

	@Test
	void templateFromYamltest() {
		Api api = new Api();
		api.templateFromYaml("/api/list.yaml",null);
	}
	
	

}
