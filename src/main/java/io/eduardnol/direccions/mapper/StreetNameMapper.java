package io.eduardnol.direccions.mapper;

import io.eduardnol.direccions.dto.ComboDTO;
import io.eduardnol.direccions.dto.StreetDetailDTO;
import io.eduardnol.direccions.dto.StreetSearchResultDTO;
import io.eduardnol.direccions.entity.StreetNameEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StreetNameMapper {
    
    @Mapping(target = "idStreetName", source = "idStreetName")
    @Mapping(target = "nomVia", source = "nom")
    @Mapping(target = "municipi", source = "municipi.nom")
    @Mapping(target = "provincia", source = "municipi.provincia.nom")
    StreetSearchResultDTO toStreetSearchResultDTO(StreetNameEntity streetNameEntity);
    
    @Mapping(target = "idStreetName", source = "idStreetName")
    @Mapping(target = "nomVia", source = "nom")
    @Mapping(target = "municipi", source = "streetNameEntity", qualifiedByName = "mapMunicipi")
    @Mapping(target = "comarca", source = "streetNameEntity", qualifiedByName = "mapComarca")
    @Mapping(target = "provincia", source = "streetNameEntity", qualifiedByName = "mapProvincia")
    @Mapping(target = "comunitatAutonoma", source = "streetNameEntity", qualifiedByName = "mapComunitatAutonoma")
    @Mapping(target = "pais", source = "streetNameEntity", qualifiedByName = "mapPais")
    StreetDetailDTO toStreetDetailDTO(StreetNameEntity streetNameEntity);
    
    @Named("mapMunicipi")
    default ComboDTO mapMunicipi(StreetNameEntity streetNameEntity) {
        if (streetNameEntity == null || streetNameEntity.getMunicipi() == null) {
            return null;
        }
        return ComboDTO.builder()
                .key(streetNameEntity.getMunicipi().getIdMunicipi())
                .value(streetNameEntity.getMunicipi().getNom())
                .build();
    }
    
    @Named("mapComarca")
    default ComboDTO mapComarca(StreetNameEntity streetNameEntity) {
        if (streetNameEntity == null || streetNameEntity.getMunicipi() == null || streetNameEntity.getMunicipi().getComarca() == null) {
            return null;
        }
        return ComboDTO.builder()
                .key(streetNameEntity.getMunicipi().getComarca().getIdComarca())
                .value(streetNameEntity.getMunicipi().getComarca().getNom())
                .build();
    }
    
    @Named("mapProvincia")
    default ComboDTO mapProvincia(StreetNameEntity streetNameEntity) {
        if (streetNameEntity == null || streetNameEntity.getMunicipi() == null || streetNameEntity.getMunicipi().getProvincia() == null) {
            return null;
        }
        return ComboDTO.builder()
                .key(streetNameEntity.getMunicipi().getProvincia().getIdProvincia())
                .value(streetNameEntity.getMunicipi().getProvincia().getNom())
                .build();
    }
    
    @Named("mapComunitatAutonoma")
    default ComboDTO mapComunitatAutonoma(StreetNameEntity streetNameEntity) {
        if (streetNameEntity == null || streetNameEntity.getMunicipi() == null || 
            streetNameEntity.getMunicipi().getProvincia() == null || 
            streetNameEntity.getMunicipi().getProvincia().getComunitatAutonoma() == null) {
            return null;
        }
        return ComboDTO.builder()
                .key(streetNameEntity.getMunicipi().getProvincia().getComunitatAutonoma().getIdComunitatAutonoma())
                .value(streetNameEntity.getMunicipi().getProvincia().getComunitatAutonoma().getNom())
                .build();
    }
    
    @Named("mapPais")
    default ComboDTO mapPais(StreetNameEntity streetNameEntity) {
        if (streetNameEntity == null || streetNameEntity.getMunicipi() == null || 
            streetNameEntity.getMunicipi().getProvincia() == null || 
            streetNameEntity.getMunicipi().getProvincia().getComunitatAutonoma() == null ||
            streetNameEntity.getMunicipi().getProvincia().getComunitatAutonoma().getPais() == null) {
            return null;
        }
        return ComboDTO.builder()
                .key(streetNameEntity.getMunicipi().getProvincia().getComunitatAutonoma().getPais().getIdPais())
                .value(streetNameEntity.getMunicipi().getProvincia().getComunitatAutonoma().getPais().getNom())
                .build();
    }
}
