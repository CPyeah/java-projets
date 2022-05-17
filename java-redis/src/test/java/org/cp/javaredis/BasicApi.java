package org.cp.javaredis;

import java.util.concurrent.TimeUnit;
import org.cp.javaredis.model.Person;
import org.junit.jupiter.api.Test;
import org.redisson.RedissonList;
import org.redisson.RedissonMap;
import org.redisson.api.RBucket;
import org.redisson.api.RKeys;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RSet;
import org.redisson.api.RSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BasicApi {

	@Autowired
	RedissonClient redissonClient;

	@Test
	public void keysTest() {
		RKeys keys = redissonClient.getKeys();
		System.out.println(keys.count());// org.redisson.client.protocol.RedisCommands.DBSIZE 命令
		Iterable<String> keysByPattern = keys
				.getKeysByPattern("*"); // org.redisson.client.protocol.RedisCommands.SCAN
		keys.getKeysStream()
				.forEach(System.out::println);// org.redisson.client.protocol.RedisCommands.SCAN
	}

	//String
	@Test
	public void testString() {
		RBucket<String> hello = redissonClient.getBucket("hello");
		System.out.println(hello.get());// org.redisson.client.protocol.RedisCommands.GET
		hello.set("world ! ", 5,
				TimeUnit.MINUTES);// org.redisson.client.protocol.RedisCommands.PSETEX
		System.out.println(hello.get());
	}

	// Object
	@Test
	public void testObject() {
		RBucket<Person> hello = redissonClient.getBucket("Tom");
		System.out.println(hello.get());
		hello.set(Person.tom(), 5, TimeUnit.MINUTES);
		System.out.println(hello.get());

	}

	// List
	@Test
	public void testList() {
		RedissonList<Person> list = (RedissonList) redissonClient.getList("list");
		Person tom = Person.tom();
		list.add(tom);// RPUSH
		list.addBefore(tom, new Person());
		list.readAll(); // LRANGE
		list.fastSet(1, new Person());
		System.out.println(list.size());
		list.forEach(System.out::println);
	}

	// Map
	@Test
	public void testMap() {
		RedissonMap<String, Object> myMap = (RedissonMap) redissonClient.getMap("myMap");
		myMap.put("Tom", Person.tom());
		RedissonMap<String, Object> map = (RedissonMap) redissonClient.getMap("myMap");
		System.out.println(map.get("Tom")); // HGET
	}

	// Set
	@Test
	public void testSet() {
		RSet<Object> set = redissonClient.getSet("set");
		set.add(1);
		set.add(2);
		set = redissonClient.getSet("set");
		for (Object o : set) {
			System.out.println(o);
		}
	}

	// ZSet
	@Test
	public void testZSet() {
		RSortedSet<Object> zset = redissonClient.getSortedSet("zset");
		zset.add(1);
		zset.add(2);
		zset.forEach(System.out::println);

		RScoredSortedSet<Object> szset = redissonClient.getScoredSortedSet("szset");
		szset.add(11.1, 1);// ZADD
		szset.add(1.1, 2);
		szset.forEach(System.out::println);
	}

}
