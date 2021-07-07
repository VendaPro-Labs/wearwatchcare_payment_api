package io.vendapro.payment.po;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class PaymentRequest {
	private String token;
	
	private long totalAmount;

	private ProductItem[] items;
	
}
