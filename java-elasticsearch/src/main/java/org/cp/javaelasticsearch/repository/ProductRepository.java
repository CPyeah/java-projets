package org.cp.javaelasticsearch.repository;

import java.util.List;
import org.cp.javaelasticsearch.model.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ElasticsearchRepository<Product, String> {

	List<Product> findAllByNameContaining(String name);

}
