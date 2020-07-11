package br.com.ficticiusclean.service;

import br.com.ficticiusclean.dto.VehicleBrandDTO;
import br.com.ficticiusclean.exception.InvalidEntityException;
import br.com.ficticiusclean.model.VehicleBrand;
import br.com.ficticiusclean.repository.VehicleBrandRepository;
import br.com.ficticiusclean.service.core.ServiceBaseMapperImpl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

@Service
@Transactional
public class VehicleBrandService extends ServiceBaseMapperImpl<VehicleBrand, VehicleBrandDTO> {

	public VehicleBrandDTO createBrand(VehicleBrandDTO dto) {

		validateDTO(dto);

		VehicleBrand brand = this.mapper.toEntity(dto);
		brand = this.repositoryBase.save(brand);

		dto.setId(brand.getId());
		return dto;
	}

	public void validateDTO(VehicleBrandDTO dto) {

		if (StringUtils.isEmpty(dto.getName()))
			throw new InvalidEntityException("Name is required");
	}

	public VehicleBrand findOneByName(String name) {

		return getRepository().findOneByName(name);
	}

	public VehicleBrandRepository getRepository() {

		return (VehicleBrandRepository) repositoryBase;
	}
}
