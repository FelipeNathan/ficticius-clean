package br.com.ficticiusclean.service.core;

import br.com.ficticiusclean.model.EntityBase;

import java.util.List;

public interface ServiceBase <TEntity extends EntityBase>  {

    List<TEntity> findAll();

    TEntity findOne(Long id);
}
