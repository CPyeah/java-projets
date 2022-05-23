package org.cp.javaredis;

import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FunnelLimit {

	@Autowired
	RedissonClient redissonClient;

	@Test
	public void test() {
	}

}
