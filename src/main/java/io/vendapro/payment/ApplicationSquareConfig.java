package io.vendapro.payment;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import lombok.Data;
import lombok.Getter;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix="square")
@Getter
@Data
@Validated
public class ApplicationSquareConfig implements Serializable  {

       @NotNull
	   private String ENVIRONMENT;
       @NotNull
	   private String APPLICATION_ID;
       @NotNull
	   private String ACCESS_TOKEN;
       @NotNull
	   private String LOCATION_ID;
	   
	   
}
