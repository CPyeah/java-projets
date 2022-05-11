package org.cp.javamysql.service;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cp.javamysql.entity.User;
import org.cp.javamysql.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

	private final UserRepository userRepository;

	public Iterable<User> all() {
		return userRepository.findAll();
	}

	public User getById(Long id) {
		Optional<User> optionalUser = userRepository.findById(id);
		return optionalUser.orElseThrow(() -> new RuntimeException("user not found"));
	}

	public User create(User user) {
		user.setCreateTime(LocalDateTime.now());
		return userRepository.save(user);
	}

	public User update(User user) {
		User userFromDB = getById(user.getId());
		BeanUtils.copyProperties(user, userFromDB);
		userFromDB.setUpdateTime(LocalDateTime.now());
		return userRepository.save(userFromDB);
	}

	public void delete(Long id) {
		userRepository.deleteById(id);
	}

}
