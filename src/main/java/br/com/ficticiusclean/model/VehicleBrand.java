package br.com.ficticiusclean.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleBrand extends EntityBase {

    private static final long serialVersionUID = -1687544158046146620L;

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "brand")
    private List<Vehicle> vehicles;
}
