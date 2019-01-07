package com.axcc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AxccApplicationTests.class)
@WebAppConfiguration
public class AxccApplicationTests {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Before
	public void init() {
		System.out.println("开始测试-----------------");
	}

	@Test
	public void test() {
		System.out.println("测试啦-----------------");
	}

	@After
	public void after() {
		System.out.println("测试结束-----------------");
	}


}
