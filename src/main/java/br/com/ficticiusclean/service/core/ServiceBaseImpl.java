package br.com.ficticiusclean.service.core;

import br.com.ficticiusclean.model.EntityBase;
import br.com.ficticiusclean.repository.RepositoryBase;

import javax.inject.Inject;
import java.util.List;

public abstract class ServiceBaseImpl<TEntity extends EntityBase> implements ServiceBase<TEntity> {

    @Inject
    protected RepositoryBase<TEntity> repositoryBase;

    @Override
    public List<TEntity> findAll() {
        return repositoryBase.findAll();
    }

    @Override
    public TEntity findOne(Long id) {
        return repositoryBase.getOne(id);
    }
}
