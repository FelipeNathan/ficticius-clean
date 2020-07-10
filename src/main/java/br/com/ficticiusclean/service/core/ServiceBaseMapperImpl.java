package br.com.ficticiusclean.service.core;

import br.com.ficticiusclean.dto.DTOBase;
import br.com.ficticiusclean.mapper.BaseMapper;
import br.com.ficticiusclean.model.EntityBase;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import java.util.List;

public abstract class ServiceBaseMapperImpl<TEntity extends EntityBase, TDTO extends DTOBase> extends ServiceBaseImpl<TEntity> implements ServiceBaseMapper<TEntity, TDTO> {

    @Inject
    protected BaseMapper<TDTO, TEntity> mapper;

    @Override
    public List<TDTO> findAllDTO() {
        return toDTOs(findAll());
    }

    @Override
    public TDTO toDTO(TEntity model) {
        return mapper.toDTO(model);
    }

    @Override
    public List<TDTO> toDTOs(List<TEntity> models) {
        return mapper.toDTOs(models);
    }

    @Override
    public TDTO findDtoById(Long id) {
        return toDTO(findOne(id));
    }
}
