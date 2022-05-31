package org.cp.javaelasticsearch.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Data
@Document(indexName = "product")
@Setting(shards = 3, replicas = 2)
public class Product {

	@Id
	@Field(type = FieldType.Text, index = false)
	private String id;

	@Field(type = FieldType.Keyword, ignoreAbove = 128)
	private String name;

	@Field(type = FieldType.Text, analyzer = "ik_smart", searchAnalyzer = "ik_smart")
	private String description;

	@Field(type = FieldType.Keyword)
	private String brandName;

	@Field(type = FieldType.Long)
	private Long stock;

	@Field(type = FieldType.Double)
	private BigDecimal price;

	@Field(type = FieldType.Date, format = {}, pattern = {"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "epoch_millis"})
	private LocalDateTime updateTime;

	@Field(type = FieldType.Nested)
	private List<Sku> skuList;

	public static Product get() {
		Random random = new Random();
		int i = random.nextInt(1000);
		Product product = new Product();
		product.setName("name " + i);
		product.setDescription("desc " + i);
		product.setBrandName("desc " + i);
		product.setStock((long) i);
		product.setPrice(BigDecimal.valueOf(i));
		product.setUpdateTime(LocalDateTime.now());
		product.setSkuList(Collections.singletonList(Sku.get()));
		return product;
	}

	public org.springframework.data.elasticsearch.core.document.Document toDocument() {
		return null;
	}
}
