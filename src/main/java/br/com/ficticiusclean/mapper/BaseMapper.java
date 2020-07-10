package br.com.ficticiusclean.mapper;

import java.util.List;

public interface BaseMapper<DTO, Model> {

    DTO toDTO(Model model);

    List<DTO> toDTOs(List<Model> models);

    Model toEntity(DTO dto);

    List<Model> toEntities(List<DTO> dto);
}
