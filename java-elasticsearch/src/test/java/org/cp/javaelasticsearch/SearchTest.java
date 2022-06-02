package org.cp.javaelasticsearch;

import java.util.List;
import org.cp.javaelasticsearch.model.Product;
import org.cp.javaelasticsearch.repository.ProductRepository;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;

@SpringBootTest
public class SearchTest {

	@Autowired
	ProductRepository repository;

	@Autowired
	ElasticsearchRestTemplate elasticsearchRestTemplate;

	@Test
	public void search() {
		List<Product> productList = repository.findAllByNameContaining("19");
		System.out.println(productList.size());
	}

	@Test
	public void searchTest() {
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
