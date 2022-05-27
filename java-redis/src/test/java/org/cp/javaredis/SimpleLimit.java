package org.cp.javaredis;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SimpleLimit {

	@Autowired
	RedissonClient redissonClient;

	@Test
	public void test() throws InterruptedException {
		// redisson用了zset来记录请求的信息，这样可以非常巧妙的通过比较score，也就是请求的时间戳，来判断当前请求距离上一个请求有没有超过一个令牌生产周期。
		// 如果超过了，则说明令牌桶中的令牌需要生产，之前用掉了多少个就生产多少个，而之前用掉了多少个令牌的信息也在zset中保存了。
		RRateLimiter limiter = redissonClient.getRateLimiter("limiter_1");
		// 1号限流器， 每一秒 允许一个请求
		limiter.trySetRate(RateType.PER_CLIENT, 1, 1, RateIntervalUnit.SECONDS);

		for (int i = 0; i < 10; i++) {
			new Thread(() -> {
				// 阻塞
//				limiter.acquire(1);
				boolean result = limiter.tryAcquire(1, 100, TimeUnit.MILLISECONDS);
				if (!result) {
					// 快速失败
					System.out.println(Thread.currentThread().getName() + " failed !");
					return;
				}
				System.out.println(Thread.currentThread().getName() + " -> " + LocalDateTime.now());
				// do something
			}).start();
		}
		Thread.sleep(12000);
	}

}
