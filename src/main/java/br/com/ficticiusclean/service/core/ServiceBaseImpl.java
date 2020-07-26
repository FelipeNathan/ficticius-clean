package br.com.ficticiusclean.service.core;

import br.com.ficticiusclean.model.EntityBase;
import br.com.ficticiusclean.repository.RepositoryBase;

import javax.inject.Inject;
import java.util.List;

public abstract class ServiceBaseImpl<T extends EntityBase> implements ServiceBase<T> {

    @Inject
    protected RepositoryBase<T> repositoryBase;

    @Override
    public List<T> findAll() {
        return repositoryBase.findAll();
    }

    @Override
    public T findOne(Long id) {
        return repositoryBase.getOne(id);
    }
}
