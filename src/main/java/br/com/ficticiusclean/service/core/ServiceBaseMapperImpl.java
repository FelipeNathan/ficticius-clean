package br.com.ficticiusclean.service.core;

import br.com.ficticiusclean.dto.DTOBase;
import br.com.ficticiusclean.mapper.BaseMapper;
import br.com.ficticiusclean.model.EntityBase;

import javax.inject.Inject;
import java.util.List;

public abstract class ServiceBaseMapperImpl<T extends EntityBase, D extends DTOBase> extends ServiceBaseImpl<T> implements ServiceBaseMapper<T, D> {

    @Inject
    protected BaseMapper<D, T> mapper;

    @Override
    public List<D> findAllDTO() {
        return toDTOs(findAll());
    }

    @Override
    public D toDTO(T model) {
        return mapper.toDTO(model);
    }

    @Override
    public List<D> toDTOs(List<T> models) {
        return mapper.toDTOs(models);
    }

    @Override
    public D findDtoById(Long id) {
        return toDTO(findOne(id));
    }
}
