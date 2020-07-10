package br.com.ficticiusclean.controller.core;

import br.com.ficticiusclean.dto.DTOBase;
import br.com.ficticiusclean.model.EntityBase;
import br.com.ficticiusclean.service.core.ServiceBaseMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import javax.inject.Inject;
import java.util.List;

public abstract class ControllerBase<TEntity extends EntityBase, TDTO extends DTOBase, TService extends ServiceBaseMapper<TEntity, TDTO>> {

    @Inject
    protected TService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TDTO>> findAll() {
        return new ResponseEntity<>(this.service.findAllDTO(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TDTO> findOne(Long id) {
        return new ResponseEntity<>(this.service.findDtoById(id), HttpStatus.OK);
    }

}
