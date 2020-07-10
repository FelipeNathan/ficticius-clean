package br.com.ficticiusclean.repository;

import br.com.ficticiusclean.model.VehicleBrand;

public interface VehicleBrandRepository extends RepositoryBase<VehicleBrand> {

    VehicleBrand findOneByName(String name);
}
