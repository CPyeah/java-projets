package org.cp.javaredis;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Random;
import org.junit.jupiter.api.Test;
import org.redisson.api.RHyperLogLog;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HyperLogLogTests {

	@Autowired
	RedissonClient redisson;

	@Test
	public void hllTest() {
		RHyperLogLog<String> today = redisson.getHyperLogLog("UV_" + LocalDate.now());
		Random random = new Random(50000);
		for (int i = 0; i < 100000; i++) {
			String userId = "id_" + random.nextInt(50000);
			today.add(userId);
		}
		System.out.println(today.count());
	}

	@Test
	public void compareTest() {
		RHyperLogLog<String> today = redisson.getHyperLogLog("UV_" + LocalDate.now());
		today.delete();
		HashSet<String> set = new HashSet<>();
		Random random = new Random();
		for (int i = 0; i < 1000; i++) {
			String userId = "id_" + random.nextInt(500);
			today.add(userId);
			set.add(userId);
		}
		System.out.println("RHyperLogLog: " + today.count());
		System.out.println("set count: " + set.size());
	}

	@Test
	public void mergeTest() {
		RHyperLogLog<String> today = redisson.getHyperLogLog("UV_" + LocalDate.now());
		RHyperLogLog<String> nextDay = redisson.getHyperLogLog("UV_" + LocalDate.now().plusDays(1));
		HashSet<String> set = new HashSet<>();
		Random random = new Random();
		for (int i = 0; i < 1000; i++) {
			String userId = "id_" + random.nextInt(1000);
			today.add(userId);
			set.add(userId);
		}
		for (int i = 0; i < 1000; i++) {
			String userId = "id_" + random.nextInt(2000);
			nextDay.add(userId);
			set.add(userId);
		}
		today.mergeWith(nextDay.getName());
		System.out.println("RHyperLogLog: " + today.count());
		System.out.println("set count: " + set.size());
	}

}
