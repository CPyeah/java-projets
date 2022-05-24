package org.cp.javaredis;

import org.junit.jupiter.api.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ScanTest {

	@Autowired
	RedissonClient redissonClient;

	@Test
	public void addKeys() {
		for (int i = 0; i < 1000; i++) {
			RBucket<String> key = redissonClient.getBucket("key_" + i);
			key.set("value_" + i);
		}
	}

	@Test
	public void scanTest() {
		RKeys keys = redissonClient.getKeys();
		keys.getKeysByPattern("key_99*").forEach(System.out::println);
		System.out.println("--------------------");
		keys.getKeysWithLimit("key_99*", 3).forEach(System.out::println);

	}

}
