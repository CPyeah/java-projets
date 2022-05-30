package org.cp.javaelasticsearch;

import java.util.List;
import org.cp.javaelasticsearch.model.Product;
import org.cp.javaelasticsearch.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SearchTest {

	@Autowired
	ProductRepository repository;

	@Test
	public void search() {
		List<Product> productList = repository.findAllByNameContaining("19");
		System.out.println(productList.size());
	}

}
