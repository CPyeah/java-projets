package org.cp.javaelasticsearch;

import org.cp.javaelasticsearch.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

@SpringBootTest
public class IndexTest {

	@Autowired
	private ElasticsearchRestTemplate elasticsearchRestTemplate;

	@Test
	public void createIndex() {

		elasticsearchRestTemplate.indexOps(Product.class).createMapping(Product.class);
	}

	@Test
	public void listIndex() {
		System.out.println(elasticsearchRestTemplate.indexOps(Product.class).getMapping());
	}

}
