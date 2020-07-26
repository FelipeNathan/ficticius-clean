package br.com.ficticiusclean.service.core;

import br.com.ficticiusclean.model.EntityBase;

import java.util.List;

public interface ServiceBase <T extends EntityBase>  {

    List<T> findAll();

    T findOne(Long id);
}
