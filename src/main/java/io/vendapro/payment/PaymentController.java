package io.vendapro.payment;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.squareup.square.api.PaymentsApi;
import com.squareup.square.exceptions.ApiException;
import com.squareup.square.models.CreatePaymentRequest;
import com.squareup.square.models.Money;

import io.vendapro.payment.po.PaymentRequest;
import io.vendapro.payment.po.PaymentResult;
import io.vendapro.payment.po.SquareEnvionment;
import io.vendapro.payment.services.SquareClientAccess;

@RestController
public class PaymentController {

	@Autowired
	ApplicationSquareConfig applicationSquareConfig;
	
	@Autowired
	SquareClientAccess squareClientAccess;
	
	@GetMapping("/square")
	public SquareEnvionment  getSquareRunningEnvrionment() {
		SquareEnvionment squareEnvionment = new SquareEnvionment();
		squareEnvionment.setLocationId(applicationSquareConfig.getLOCATION_ID());
		squareEnvionment.setAppId(applicationSquareConfig.getAPPLICATION_ID());
		squareEnvionment.setCurrency(this.squareClientAccess.getCurrency());
		squareEnvionment.setContry(this.squareClientAccess.getCountry());
		return squareEnvionment;
	}
	
	@PostMapping("/process-payment")
	public @ResponseBody PaymentResult processPayment(@RequestBody PaymentRequest paymentRequest) {
		
		String currency = this.squareClientAccess.getCurrency();
		
		Money bodyAmountMoney = new Money.Builder()
				.amount(paymentRequest.getMoneyAmount())
				.currency(currency)
				.build();
		
		CreatePaymentRequest createPaymentRequest = new CreatePaymentRequest.Builder(
				paymentRequest.getToken(),
				UUID.randomUUID().toString(),
				bodyAmountMoney)
				.build();
			
		
		PaymentsApi paymentApi = this.squareClientAccess.getSquareClient().getPaymentsApi();
		
		return paymentApi.createPaymentAsync(createPaymentRequest)
			.thenApply( result -> {
				return new PaymentResult("SUCCESS", null);
			})
			.exceptionally(e -> {
				ApiException ae = (ApiException) e.getCause();
				return new PaymentResult("FAILURE", ae);
			})
			.join();
		
		
	}
 	
}
