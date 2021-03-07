package com.testerhome.wework.contact;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.testerhome.wework.WeworkConfig;

class WeworkConfigTest {

	@Test
	void load() {
		//WeworkConfig.load("/config/WeworkConfig.yaml");
		System.out.println(WeworkConfig.load("/config/WeworkConfig.yaml").agentId);
	}
	
	@Test
	void getInstance() {
		WeworkConfig.getInstance();
	}

}
