package org.cp.javaredis;

import org.junit.jupiter.api.Test;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BloomTest {

	@Autowired
	RedissonClient redissonClient;

	@Test
	public void test() {
		RBloomFilter<String> bloom = redissonClient.getBloomFilter("bloom");
		bloom.tryInit(1000, 0.1);
		for (int i = 0; i < 100; i++) {
			bloom.add("user_id_" + i);
		}
		for (int i = 80; i < 120; i++) {
			boolean contains = bloom.contains("user_id_" + i);
			System.out.println("user_id_" + i + "  ->  " + contains);
		}
	}
}
