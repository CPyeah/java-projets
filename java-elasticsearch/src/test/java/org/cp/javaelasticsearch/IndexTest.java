package org.cp.javaelasticsearch;

import java.util.Map;
import org.cp.javaelasticsearch.model.Product;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.cluster.ClusterHealth;
import org.springframework.data.elasticsearch.core.index.Settings;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;

@SpringBootTest
public class IndexTest {

	@Autowired
	private ElasticsearchRestTemplate elasticsearchRestTemplate;


	@Test
	public void simpleCreate() {
		boolean b = elasticsearchRestTemplate.indexOps(Product.class).create();
		System.out.println(b);
	}

	@Test
	public void health() {
		ClusterHealth health = elasticsearchRestTemplate.cluster().health();
		System.out.println(health);
	}

	@Test
	public void index() {
		// 拿到对应索引的操作引用
		IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(Product.class);
		// 查看索引是否存在
		boolean exists = indexOperations.exists();
		if (exists) {
			// 删除索引（删除操作要慎重）
			indexOperations.delete();
		}
		// 创建索引
//		indexOperations.create(); //这个没有配置mapping
		indexOperations.createWithMapping();

		// 查看索引的Setting
		Settings settings = indexOperations.getSettings();
		// 查看索引的Mapping
		Map<String, Object> mapping = indexOperations.getMapping();
	}

	@Test
	public void test() {
		// 查询所有
		Query searchQuery = Query.findAll();
		IndexCoordinates indexCoordinates = IndexCoordinates.of("product");
		SearchHits<Product> result = elasticsearchRestTemplate
				.search(searchQuery, Product.class, indexCoordinates);
		System.out.println(result);

		// 按名称 搜索
		MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("spuName", "473");
		searchQuery = new NativeSearchQueryBuilder()
				.withQuery(matchQueryBuilder)
				.build();
		result = elasticsearchRestTemplate
				.search(searchQuery, Product.class, indexCoordinates);
		System.out.println(result);

		// 按价格搜索
		Criteria criteria = new Criteria("price")
				.greaterThan(500.0)
				.lessThan(800.0);

		searchQuery = new CriteriaQuery(criteria);

		result = elasticsearchRestTemplate
				.search(searchQuery, Product.class, indexCoordinates);
		System.out.println(result);

	}

}
