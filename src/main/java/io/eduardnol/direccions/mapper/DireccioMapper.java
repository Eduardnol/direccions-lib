package io.eduardnol.direccions.mapper;

import io.eduardnol.direccions.dto.StreetDetailDTO;
import io.eduardnol.direccions.dto.StreetSearchResultDTO;
import io.eduardnol.direccions.entity.DireccioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DireccioMapper {
    
    @Mapping(target = "idDireccio", source = "idDireccio")
    @Mapping(target = "nomVia", source = "nomVia")
    @Mapping(target = "tipusVia", source = "tipusVia.nom")
    @Mapping(target = "municipi", source = "municipi.nom")
    @Mapping(target = "provincia", source = "provincia.nom")
    StreetSearchResultDTO toStreetSearchResultDTO(DireccioEntity direccioEntity);
    
    @Mapping(target = "idDireccio", source = "idDireccio")
    @Mapping(target = "nomVia", source = "nomVia")
    @Mapping(target = "numero", source = "numero")
    @Mapping(target = "pis", source = "pis")
    @Mapping(target = "tipusVia", source = "tipusVia.nom")
    @Mapping(target = "codiPostal", source = "codiPostal.codiPostal")
    @Mapping(target = "municipi", source = "municipi.nom")
    @Mapping(target = "comarca", source = "municipi.comarca")
    @Mapping(target = "provincia", source = "provincia.nom")
    @Mapping(target = "comunitatAutonoma", source = "comunitatAutonoma.nom")
    @Mapping(target = "pais", source = "pais.nom")
    StreetDetailDTO toStreetDetailDTO(DireccioEntity direccioEntity);
}
