package org.cp.javamysql.repository;

import org.cp.javamysql.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
