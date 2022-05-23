package org.cp.javaredis;

import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Test;
import org.redisson.api.GeoEntry;
import org.redisson.api.GeoUnit;
import org.redisson.api.RGeo;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 在一个地图应用中，车的数据、餐馆的数据、人的数据可能会有百万千万条，如果使用
 * Redis 的 Geo 数据结构，它们将全部放在一个 zset 集合中。在 Redis 的集群环境中，集合
 * 可能会从一个节点迁移到另一个节点，如果单个 key 的数据过大，会对集群的迁移工作造成
 * 较大的影响，在集群环境中单个 key 对应的数据量不宜超过 1M，否则会导致集群迁移出现
 * 卡顿现象，影响线上服务的正常运行。
 * 所以，这里建议 Geo 的数据使用单独的 Redis 实例部署，不使用集群环境。
 * 如果数据量过亿甚至更大，就需要对 Geo 数据进行拆分，按国家拆分、按省拆分，按
 * 市拆分，在人口特大城市甚至可以按区拆分。这样就可以显著降低单个 zset 集合的大小。
 */
@SpringBootTest
public class GeoHashTest {

	@Autowired
	RedissonClient redissonClient;

	@Test
	public void test() throws ExecutionException, InterruptedException {
		RGeo<Object> geo = redissonClient.getGeo("geo");
		GeoEntry juejin = new GeoEntry(116.48105, 39.996794, "juejin");
		geo.add(juejin);
		geo.add(116.514203, 39.905409, "ireader");
		geo.add(116.489033, 40.007669, "meituan");
		geo.add(116.562108, 39.787602, "jd");
		geo.add(116.334255, 40.027400, "xiaomi");
		geo.add(1.2, "test");

		// 两点距离
		System.out.println(geo.dist("meituan", "jd", GeoUnit.METERS));
		// 坐标
		System.out.println(geo.pos("xiaomi"));
		// hash
		System.out.println(geo.hash("ireader"));
		// 附近
		System.out.println(geo.radiusWithPositionAsync("jd", 20, GeoUnit.KILOMETERS).get());
		// 附近
		System.out.println(
				geo.radiusWithPositionAsync(116.334255, 40.027400, 20, GeoUnit.KILOMETERS).get());
	}

}
