package org.cp.javaredis;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class QueueTest {

	@Autowired
	RedissonClient redissonClient;

	// 简单队列
	@Test
	public void simple() {
		RQueue<Object> queue = redissonClient.getQueue("simple");

		new Thread(() -> {
			for (int i = 0; i < 50; i++) {
				queue.add(i);
			}
		}).start();
		for (int i = 0; i < 50; i++) {
			Object poll = queue.poll();
			if (poll == null) {
				i--;
			} else {
				System.out.println(poll);
			}
		}
	}

	// 阻塞队列
	@Test
	public void blockQueue() throws InterruptedException {
		RBlockingQueue<Object> queue = redissonClient.getBlockingQueue("block");

		new Thread(() -> {
			for (int i = 0; i < 50; i++) {
				try {
					Thread.sleep(100);
					queue.put(i);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		for (int i = 0; i < 50; i++) {
			// 阻塞take
			Object poll = queue.take();
			System.out.println(poll);
		}
	}

	// 延迟队列
	@Test
	public void delayQueue() throws InterruptedException {
		RBlockingQueue<Object> blockingQueue = redissonClient.getBlockingQueue("delayQueue1");
		RDelayedQueue<Object> delayQueue = redissonClient
				.getDelayedQueue(blockingQueue);
		for (int i = 0; i < 50; i++) {
			delayQueue.offerAsync(i, (500 - i * 10), TimeUnit.MILLISECONDS);
		}

		blockingQueue = redissonClient.getBlockingQueue("delayQueue");
		System.out.println(blockingQueue.size());
		redissonClient.getDelayedQueue(blockingQueue);
		for (int i = 0; i < blockingQueue.size(); i++) {
			Object poll = blockingQueue.take();
			System.out.println(poll);
		}
	}
}
