package io.eduardnol.direccions.controller;

import io.eduardnol.direccions.dto.CheckCodiPostalDTO;
import io.eduardnol.direccions.dto.ComarcaDTO;
import io.eduardnol.direccions.dto.ComboCodeDTO;
import io.eduardnol.direccions.dto.ComboDTO;
import io.eduardnol.direccions.dto.StreetDetailDTO;
import io.eduardnol.direccions.dto.StreetSearchResultDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DireccioApi {
    List<ComboCodeDTO> getAllPais();
    List<ComboCodeDTO> getComunitatAutonomaByPais(Long idPais);
    List<ComboDTO> getProvinciaByComunitatAutonoma(Long idComunitatAutonoma);
    List<ComboDTO> getMunicipiByIdProvincia(Long idProvincia);
    List<ComboDTO> getTipusVia();
    Long checkCodiPostal(CheckCodiPostalDTO checkCodiPostalDTO);
    ResponseEntity<ComarcaDTO> getComarcaByIdMunicipi(Long idMunicipi);
    List<StreetSearchResultDTO> searchStreets(String searchText);
    ResponseEntity<StreetDetailDTO> getStreetDetailsById(Long idDireccio);
}
