package org.cp.javaelasticsearch.model;

import java.math.BigDecimal;
import java.util.Random;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "product")
public class Product {

	@Id
	private String id;

	@Field(type = FieldType.Text)
	private String spuName;

	@Field(type = FieldType.Text)
	private String description;

	@Field(type = FieldType.Keyword)
	private String brandName;

	@Field(type = FieldType.Double)
	private BigDecimal price;

	public static Product get() {
	    Product product = new Product();
		Random random = new Random();
		int i = random.nextInt(1000);
		product.setSpuName("name_" + i);
	    product.setDescription("desc_" + i);
	    product.setBrandName("brand" + i);
	    product.setPrice(BigDecimal.valueOf(i));
	    return product;
	}
}
