package br.com.ficticiusclean.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Calendar;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO extends DTOBase {

    private Long id;

    private String name;

    private String brand;

    private String model;

    private Long manufacturingDate;

    private BigDecimal gasConsumptionCity;

    private BigDecimal gasConsumptionRoad;
}
