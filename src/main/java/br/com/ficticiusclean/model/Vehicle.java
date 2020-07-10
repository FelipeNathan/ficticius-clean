package br.com.ficticiusclean.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle extends EntityBase {

    private static final long serialVersionUID = 8326591665858964370L;

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    private VehicleBrand brand;

    private String model;

    @Column(name = "manufacturing_date")
    private Long manufacturingDate;

    @Column(name = "gas_consumption_city")
    private double gasConsumptionCity;

    @Column(name = "gas_consumption_road")
    private double gasConsumptionRoad;
}
