package br.com.ficticiusclean.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.ficticiusclean.dto.VehicleConsumptionDTO;
import br.com.ficticiusclean.dto.VehicleDTO;
import br.com.ficticiusclean.exception.EntityNotFoundException;
import br.com.ficticiusclean.exception.InvalidEntityException;
import br.com.ficticiusclean.model.Vehicle;
import br.com.ficticiusclean.model.VehicleBrand;
import br.com.ficticiusclean.service.core.ServiceBaseMapperImpl;

@Service
@Transactional
public class VehicleService extends ServiceBaseMapperImpl<Vehicle, VehicleDTO> {

	@Inject
	private VehicleBrandService brandService;

	public VehicleDTO createVehicle(VehicleDTO dto) {

		validateDTO(dto);

		Vehicle vehicle = this.mapper.toEntity(dto);

		VehicleBrand brand = brandService.findOneByName(dto.getBrand());

		if (brand == null)
			throw new EntityNotFoundException(dto.getBrand());

		vehicle.setBrand(brand);

		vehicle = this.repositoryBase.save(vehicle);

		dto.setId(vehicle.getId());
		return dto;
	}

	public List<VehicleConsumptionDTO> calculateConsumption(BigDecimal gasPrice, BigDecimal kmInCity, BigDecimal kmInRoad) {

		return findAllDTO().stream().map(vehicle -> {
			VehicleConsumptionDTO vehicleConsumption = VehicleConsumptionDTO.builder()
					.name(vehicle.getName())
					.brand(vehicle.getBrand())
					.model(vehicle.getModel())
					.year(vehicle.getManufacturingDate())
					.build();

			calculateConsumptionByVehicle(vehicle, vehicleConsumption, gasPrice, kmInCity, kmInRoad);
			return vehicleConsumption;
		}).sorted(Comparator.comparing(VehicleConsumptionDTO::getPriceConsumptionTotal).reversed()).collect(Collectors.toList());
	}

	public void calculateConsumptionByVehicle(VehicleDTO vehicle, VehicleConsumptionDTO vehicleConsumption, BigDecimal gasPrice, BigDecimal kmInCity, BigDecimal kmInRoad) {

		if (kmInCity == null)
			kmInCity = BigDecimal.ZERO;

		if (kmInRoad == null)
			kmInRoad = BigDecimal.ZERO;

		BigDecimal totalGasConsumptionInCity = (vehicle.getGasConsumptionCity().compareTo(BigDecimal.ZERO) == 0) ? BigDecimal.ZERO : kmInCity.divide(vehicle.getGasConsumptionCity(), 2, RoundingMode.HALF_EVEN);
		BigDecimal totalGasConsumptionInRoad = (vehicle.getGasConsumptionRoad().compareTo(BigDecimal.ZERO) == 0) ? BigDecimal.ZERO : kmInRoad.divide(vehicle.getGasConsumptionRoad(), 2, RoundingMode.HALF_EVEN);

		BigDecimal totalGasConsumption = totalGasConsumptionInCity.add(totalGasConsumptionInRoad);
		vehicleConsumption.setGasConsumptionTotal(totalGasConsumption.setScale(2, RoundingMode.HALF_EVEN));
		vehicleConsumption.setPriceConsumptionTotal(totalGasConsumption.multiply(gasPrice).setScale(2, RoundingMode.HALF_EVEN));
	}

	public void validateDTO(VehicleDTO dto) {

		if (StringUtils.isEmpty(dto.getBrand()))
			throw new InvalidEntityException("Brand is required");

		if (StringUtils.isEmpty(dto.getModel()))
			throw new InvalidEntityException("Model is required");

		if (StringUtils.isEmpty(dto.getName()))
			throw new InvalidEntityException("Name is required");
	}
}
