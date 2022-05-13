package org.cp.javaredis;

import java.util.concurrent.TimeUnit;
import org.cp.javaredis.model.Person;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BasicApi {

	@Autowired
	private RedissonClient redissonClient;

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

	// Set

	// ZSet


}
