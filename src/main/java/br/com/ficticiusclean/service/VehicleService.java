package br.com.ficticiusclean.service;

import br.com.ficticiusclean.dto.VehicleDTO;
import br.com.ficticiusclean.exception.InvalidEntityException;
import br.com.ficticiusclean.model.Vehicle;
import br.com.ficticiusclean.model.VehicleBrand;
import br.com.ficticiusclean.service.core.ServiceBaseMapperImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class VehicleService extends ServiceBaseMapperImpl<Vehicle, VehicleDTO> {

    @Inject
    private VehicleBrandService brandService;

    public VehicleDTO createVehicle(VehicleDTO dto) throws InvalidEntityException {

        validateDTO(dto);

        Vehicle vehicle = this.mapper.toEntity(dto);

        VehicleBrand brand = brandService.findOneByName(dto.getBrand());
        vehicle.setBrand(brand);

        vehicle = this.repositoryBase.save(vehicle);

        dto.setId(vehicle.getId());
        return dto;
    }

    public List<VehicleDTO> calculateConsumption(double gasPrice, double kmInCity, double kmInRoad) {

        List<VehicleDTO> vehicles = findAllDTO();

        vehicles.forEach(vehicle -> {

            double totalGasConsumptionInCity = kmInCity / vehicle.getGasConsumptionCity();
            double totalGasConsumptionInRoad = kmInRoad / vehicle.getGasConsumptionRoad();

            double totalGasConsumption = totalGasConsumptionInCity + totalGasConsumptionInRoad;
            vehicle.setGasConsumptionTotal(totalGasConsumption);
            vehicle.setPriceConsumptionTotal(totalGasConsumption * gasPrice);
        });

        vehicles.sort(Comparator.comparingDouble(VehicleDTO::getPriceConsumptionTotal).reversed());

        return vehicles;
    }

    private void validateDTO(VehicleDTO dto) throws InvalidEntityException {

        if (StringUtils.isEmpty(dto.getBrand()))
            throw new InvalidEntityException("Brand is required");

        if (StringUtils.isEmpty(dto.getModel()))
            throw new InvalidEntityException("Model is required");

        if (StringUtils.isEmpty(dto.getName()))
            throw new InvalidEntityException("Name is required");
    }
}
