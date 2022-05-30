package org.cp.javaelasticsearch;

import java.util.ArrayList;
import java.util.List;
import org.cp.javaelasticsearch.model.Product;
import org.cp.javaelasticsearch.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@SpringBootTest
public class CURDTest {

	@Autowired
	private ProductRepository repository;

	@Test
	public void create() {
		Product product = repository.save(Product.get());
		Assertions.assertNotNull(product);
	}

	@Test
	public void createMore() {
		List<Product> products = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			products.add(Product.get());
		}
		repository.saveAll(products);
	}

	@Test
	public void listAll() {
		Iterable<Product> all = repository.findAll();
		all.forEach(System.out::println);
	}

	@Test
	public void page() {
		Page<Product> page = repository.findAll(Pageable.ofSize(4).withPage(0));
		System.out.println(page);
	}

	@Test
	public void getById() {
		Page<Product> page = repository.findAll(Pageable.ofSize(1).withPage(0));
		Product product = page.getContent().get(0);
		Product product1 = repository.findById(product.getId()).orElse(null);
		Assertions.assertNotNull(product1);
		System.out.println(product1);
		Assertions.assertEquals(product.getId(), product1.getId());
	}

	@Test
	public void update() {
		Page<Product> page = repository.findAll(Pageable.ofSize(1).withPage(0));
		Product product = page.getContent().get(0);
		Product newProduct = Product.get();
		newProduct.setId(product.getId());
		Product save = repository.save(newProduct);
		Assertions.assertEquals(product.getId(), save.getId());
		Assertions.assertNotEquals(product.getSpuName(), save.getSpuName());
		System.out.println(product);
		System.out.println(save);
	}

	@Test
	public void delete() {
		Page<Product> page = repository.findAll(Pageable.ofSize(1).withPage(0));
		Product product = page.getContent().get(0);
		repository.delete(product);

		Product product1 = repository.findById(product.getId()).orElse(null);
		Assertions.assertNull(product1);
	}

}
