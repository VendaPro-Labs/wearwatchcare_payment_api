package io.vendapro.payment.po;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class SquareEnvionment implements  Serializable{
	private String locationId;
	private String appId;
	private String currency;
	private String country;
}
