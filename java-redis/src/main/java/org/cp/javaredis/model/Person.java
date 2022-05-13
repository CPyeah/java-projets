package org.cp.javaredis.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Person implements Serializable {

	private String name;

	private Integer ages;

	private LocalDateTime createTime;

	public static Person tom() {
		Person person = new Person();
		person.setName("Tom");
		person.setAges(4);
		person.setCreateTime(LocalDateTime.now());
		return person;
	}

}
