package org.cp.javaredis;

import java.util.concurrent.TimeUnit;
import org.cp.javaredis.model.Person;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RKeys;
import org.redisson.api.RList;
import org.redisson.api.RMap;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RSet;
import org.redisson.api.RSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BasicApi {

	@Autowired
	private RedissonClient redissonClient;


	@Test
	public void keysTest() {
		RKeys keys = redissonClient.getKeys();
		keys.getKeysStream().forEach(System.out::println);
	}

	//String
	@Test
	public void testString() {
		RBucket<String> hello = redissonClient.getBucket("hello");
		System.out.println(hello.get());
		hello.set("world ! ", 5, TimeUnit.MINUTES);
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
		RList<Person> list = redissonClient.getList("list");
		list.add(Person.tom());
		list.add(Person.tom());
		System.out.println(list.size());
		list.forEach(System.out::println);
	}

	// Map
	@Test
	public void testMap() {
		RMap<String, Object> myMap = redissonClient.getMap("myMap");
		myMap.put("Tom", Person.tom());
		RMap<Object, Object> map = redissonClient.getMap("myMap");
		System.out.println(map.get("Tom"));
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
		szset.add(11.1, 1);
		szset.add(1.1, 2);
		szset.forEach(System.out::println);
	}


}
