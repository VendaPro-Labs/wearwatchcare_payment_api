package io.vendapro.payment.po;

import com.squareup.square.exceptions.ApiException;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResult {
	
	private String code;
	
	private String message;
	
	
	private String refId;
	private String status;

	public PaymentResult(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	
	
}
