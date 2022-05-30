package org.cp.javaelasticsearch.model;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class Sku {

	private String skuName;

	private BigDecimal skuPrice;

	public static Sku get() {
	    Sku sku = new Sku();
	    sku.setSkuName("sku name");
	    sku.setSkuPrice(new BigDecimal("19.1"));
	    return sku;
	}

}
