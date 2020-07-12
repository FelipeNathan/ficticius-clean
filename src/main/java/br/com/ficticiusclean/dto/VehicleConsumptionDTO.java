package br.com.ficticiusclean.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleConsumptionDTO {

	private String name;

	private String brand;

	private String model;

	private Long year;

	private BigDecimal gasConsumptionTotal;

	private BigDecimal priceConsumptionTotal;
}
