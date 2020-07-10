package br.com.ficticiusclean.service.core;

import br.com.ficticiusclean.dto.DTOBase;
import br.com.ficticiusclean.model.EntityBase;

import java.util.List;

public interface ServiceBaseMapper<TEntity extends EntityBase, TDTO extends DTOBase> extends ServiceBase<TEntity> {

    List<TDTO> findAllDTO();

    TDTO toDTO(TEntity model);

    List<TDTO> toDTOs(List<TEntity> models);

    TDTO findDtoById(Long id);
}
