package io.vendapro.payment.services;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.squareup.square.Environment;
import com.squareup.square.SquareClient;
import com.squareup.square.models.RetrieveLocationResponse;

import io.vendapro.payment.ApplicationSquareConfig;
import lombok.Getter;

@Component
@Getter
public class SquareClientAccess {
	
	@Autowired
	ApplicationSquareConfig applicationSquareConfig;
	
	SquareClient squareClient;

    @PostConstruct
	public void init() {
		 this.squareClient = new SquareClient.Builder()
				 .environment( Environment.fromString( this.applicationSquareConfig.getENVIRONMENT() ))
				 .accessToken(this.applicationSquareConfig.getACCESS_TOKEN() )
				 .build();
	}

	
	
	public String getCurrency() {
		if (this.getRetrieveLocationResponse().isPresent() ) {
			return this.getRetrieveLocationResponse().get().getLocation().getCurrency();
		}
		else return "USD";
	}
	
	public String getCountry() {
		if (this.getRetrieveLocationResponse().isPresent() ) {
			return this.getRetrieveLocationResponse().get().getLocation().getCountry();
		}
		else return "US";
		
	}
	
	public Optional<RetrieveLocationResponse> getRetrieveLocationResponse() {
		if (retrieveLocationResponse == null) {
			
			try {
				this.retrieveLocationResponse = callRetrieveLocationResponse().get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			
		}
		return Optional.ofNullable(this.retrieveLocationResponse);	
	}
	
	public CompletableFuture<RetrieveLocationResponse>  callRetrieveLocationResponse() {
		return this.squareClient.getLocationsApi().retrieveLocationAsync(this.applicationSquareConfig.getLOCATION_ID())
		.thenApply(  result -> {
			return result;
		} )
		.exceptionally(e -> {
			e.printStackTrace();
			return null;
		}) ;
	}
	
	
	private RetrieveLocationResponse retrieveLocationResponse;
}
