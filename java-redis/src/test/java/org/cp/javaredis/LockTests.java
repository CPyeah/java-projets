package org.cp.javaredis;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.redisson.RedissonFairLock;
import org.redisson.RedissonSpinLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LockTests {

	@Autowired
	RedissonClient redisson;

	@Test
	public void lockTest() throws InterruptedException {
		RLock lock = redisson.getLock("my-lock");
		try {
			lock.lock();
			// do somethings
		} finally {
			lock.unlock();
		}
	}

	@Test
	public void fairlyLockTest() {
		RedissonFairLock lock = (RedissonFairLock) redisson.getFairLock("flock");
		try {
			lock.lock(10, TimeUnit.MINUTES);
			// do somethings
		} finally {
			lock.unlock();
		}
	}

	@Test
	public void spinLockTest() {
		RedissonSpinLock lock = (RedissonSpinLock) redisson.getSpinLock("slock");
		try {
			lock.lock(10, TimeUnit.MINUTES);
			// do somethings
		} finally {
			lock.unlock();
		}
	}

	@Test
	public void reentrantLockTest() {
		RLock lock = redisson.getLock("slock");
		try {
			lock.lock();
			lock.lock();
			lock.lock();
			System.out.println(lock.getHoldCount());
			// do somethings
		} finally {
			lock.unlock();
			lock.unlock();
			lock.unlock();
		}
	}

}
