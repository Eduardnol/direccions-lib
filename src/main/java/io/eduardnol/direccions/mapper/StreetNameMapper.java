package io.eduardnol.direccions.mapper;

import io.eduardnol.direccions.dto.StreetDetailDTO;
import io.eduardnol.direccions.dto.StreetSearchResultDTO;
import io.eduardnol.direccions.entity.StreetNameEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StreetNameMapper {
    
    @Mapping(target = "idStreetName", source = "idStreetName")
    @Mapping(target = "nomVia", source = "nom")
    @Mapping(target = "municipi", source = "municipi.nom")
    @Mapping(target = "provincia", source = "municipi.provincia.nom")
    StreetSearchResultDTO toStreetSearchResultDTO(StreetNameEntity streetNameEntity);
    
    @Mapping(target = "idStreetName", source = "idStreetName")
    @Mapping(target = "nomVia", source = "nom")
    @Mapping(target = "municipi", source = "municipi.nom")
    @Mapping(target = "comarca", source = "municipi.comarca")
    @Mapping(target = "provincia", source = "municipi.provincia.nom")
    @Mapping(target = "comunitatAutonoma", source = "municipi.provincia.comunitatAutonoma.nom")
    @Mapping(target = "pais", source = "municipi.provincia.comunitatAutonoma.pais.nom")
    StreetDetailDTO toStreetDetailDTO(StreetNameEntity streetNameEntity);
}
