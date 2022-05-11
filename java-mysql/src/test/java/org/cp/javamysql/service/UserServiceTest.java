package org.cp.javamysql.service;

import org.cp.javamysql.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceTest {

	@Autowired
	UserService userService;

	@Test
	void all() {
		userService.all().forEach(System.out::print);
	}

	@Test
	void getById() {
		User user = userService.getById(2L);
		Assertions.assertNotNull(user);
	}

	@Test
	void create() {
		User tom = User.builder()
				.name("tom")
				.email("tom@qq.com")
				.build();
		User user = userService.create(tom);
		Assertions.assertEquals("tom", user.getName());
	}

	@Test
	void update() {
		User newTom = User.builder()
				.id(2L)
				.name("newTom")
				.email("tom@gmail.com")
				.build();
		User update = userService.update(newTom);
		Assertions.assertEquals("newTom", update.getName());
	}

	@Test
	void delete() {
		userService.delete(1L);
	}
}