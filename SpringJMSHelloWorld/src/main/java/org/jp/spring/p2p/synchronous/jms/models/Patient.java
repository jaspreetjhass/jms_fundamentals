package org.jp.spring.p2p.synchronous.jms.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {

	private Integer id;
	private String name;
	private String insuranceProvider;
	private Double copay;
	private Double amountPayable;
}
