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

public abstract class ControllerBase<T extends EntityBase, D extends DTOBase, S extends ServiceBaseMapper<T, D>> {

    @Inject
    protected S service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<D>> findAll() {
        return new ResponseEntity<>(this.service.findAllDTO(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<D> findOne(Long id) {
        return new ResponseEntity<>(this.service.findDtoById(id), HttpStatus.OK);
    }

}
