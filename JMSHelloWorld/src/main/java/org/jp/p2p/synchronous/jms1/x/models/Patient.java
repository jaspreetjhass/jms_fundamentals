package org.jp.p2p.synchronous.jms1.x.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Patient implements Serializable {

	private Integer id;
	private String name;
	private String insuranceProvider;
	private Double copay;
	private Double amountPayable;

}
