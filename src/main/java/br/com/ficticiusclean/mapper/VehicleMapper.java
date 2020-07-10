package br.com.ficticiusclean.mapper;

import br.com.ficticiusclean.dto.VehicleDTO;
import br.com.ficticiusclean.model.Vehicle;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {VehicleBrandMapper.class})
public abstract class VehicleMapper implements BaseMapper<VehicleDTO, Vehicle> {

    @Override
    @Mapping(source = "brand.name", target = "brand")
    public abstract VehicleDTO toDTO(Vehicle vehicle);

    @Override
    @InheritInverseConfiguration
    public abstract Vehicle toEntity(VehicleDTO vehicleDTO);
}
