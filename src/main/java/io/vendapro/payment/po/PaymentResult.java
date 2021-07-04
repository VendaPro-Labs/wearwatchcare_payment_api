package io.vendapro.payment.po;

import com.squareup.square.exceptions.ApiException;

import lombok.Getter;

@Getter
public class PaymentResult {

	private String code;
	
	private ApiException apiException;

	public PaymentResult(String code, ApiException apiException) {
		super();
		this.code = code;
		this.apiException = apiException;
	}
	
	
}
