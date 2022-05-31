package org.cp.javaelasticsearch;

import java.util.ArrayList;
import java.util.List;
import org.cp.javaelasticsearch.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateResponse;

@SpringBootTest
public class CURDTest2 {

	@Autowired
	private ElasticsearchRestTemplate elasticsearchRestTemplate;


	@Test
	public void create() {
		Product product = elasticsearchRestTemplate.save(Product.get());
		Assertions.assertNotNull(product);
	}

	@Test
	public void createMore() {
		List<Product> products = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			products.add(Product.get());
		}
		elasticsearchRestTemplate.save(products);
	}

	@Test
	public void listAll() {
		Query query = elasticsearchRestTemplate.matchAllQuery();
		SearchHits<Product> search = elasticsearchRestTemplate.search(query, Product.class);
	}

	@Test
	public void getById() {
		Product product = elasticsearchRestTemplate.get("ID", Product.class);
		Assertions.assertNotNull(product);
	}

	@Test
	public void update() {
		Product oldProduct = elasticsearchRestTemplate.get("ID", Product.class);
		Assertions.assertNotNull(oldProduct);

		Product newProduct = Product.get();
		Document document = newProduct.toDocument();
		UpdateQuery updateQuery = UpdateQuery.builder("ID").withDocument(document).build();
		UpdateResponse product = elasticsearchRestTemplate
				.update(updateQuery, IndexCoordinates.of("product"));

	}

	@Test
	public void delete() {
		String id = elasticsearchRestTemplate.delete("ID", Product.class);
	}

}
