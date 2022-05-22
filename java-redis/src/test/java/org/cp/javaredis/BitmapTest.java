package org.cp.javaredis;

import org.junit.jupiter.api.Test;
import org.redisson.api.RBitSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BitmapTest {

	@Autowired
	RedissonClient redissonClient;

	@Test
	public void test() {
		RBitSet bitset = redissonClient.getBitSet("bitset");
		bitset.set(0, true);
		System.out.println(bitset.get(0));
		System.out.println(bitset.get(1));
		System.out.println(bitset.incrementAndGetInteger(0, 1));
	}

}
