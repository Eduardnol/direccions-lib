package io.eduardnol.direccions.mapper;

import io.eduardnol.direccions.dto.ComarcaDTO;
import io.eduardnol.direccions.entity.ComarcaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ComarcaMapper {
    @Mapping(target = "id", source = "idComarca")
    ComarcaDTO toComarcaDTO(ComarcaEntity comarcaEntity);
}
