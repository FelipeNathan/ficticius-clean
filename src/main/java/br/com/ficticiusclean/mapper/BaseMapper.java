package br.com.ficticiusclean.mapper;

import java.util.List;

public interface BaseMapper<D, M> {

    D toDTO(M model);

    List<D> toDTOs(List<M> models);

    M toEntity(D dto);

    List<M> toEntities(List<D> dto);
}
