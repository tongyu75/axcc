package com.axcc;

import com.axcc.utils.redis.RedisUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@SpringBootTest
public class RedisTest {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Before
	public void init() {
		logger.info("开始测试-----------------");
	}

	//@Autowired
	//ShardedJedisPool jedisPool;

	@Test
	public void test() {
		logger.info("测试啦-----------------" + RedisUtils.setex("bbb", 10, "2222"));
		logger.info("测试啦-----------------" + RedisUtils.exists("bbb"));
		logger.info("测试啦-----------------" + RedisUtils.get("1123"));
	}

	@After
	public void after() {
		logger.info("测试结束-----------------");
	}


}
