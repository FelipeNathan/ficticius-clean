package br.com.ficticiusclean.service.core;

import br.com.ficticiusclean.dto.DTOBase;
import br.com.ficticiusclean.model.EntityBase;

import java.util.List;

public interface ServiceBaseMapper<T extends EntityBase, D extends DTOBase> extends ServiceBase<T> {

    List<D> findAllDTO();

    D toDTO(T model);

    List<D> toDTOs(List<T> models);

    D findDtoById(Long id);
}
