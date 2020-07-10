package br.com.ficticiusclean.mapper;

import br.com.ficticiusclean.dto.VehicleBrandDTO;
import br.com.ficticiusclean.model.VehicleBrand;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class VehicleBrandMapper implements BaseMapper<VehicleBrandDTO, VehicleBrand> {
}
