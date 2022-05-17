# Redis

> [官网](https://redis.io/)

## 数据结构

- String
- List
- Hash
- Set
- Sorted Set
- **HyperLogLog**
- **Geo**
- **Pub/Sub**
- **BloomFilter**
- **RedisSearch**
- **Redis-ML**

## 分布式锁的实现

使用`setnx`命令来争抢，设置过期时间，防止死锁
