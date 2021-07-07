package io.vendapro.payment.po;

import lombok.Getter;

@Getter
public class ProductItem {

	 private String id;
     private String source_id;
     private String name;
     private long price;	
	private long count;
	private long total;
}
