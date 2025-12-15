package io.eduardnol.direccions.service;

import io.eduardnol.direccions.dto.CheckCodiPostalDTO;
import io.eduardnol.direccions.dto.ComarcaDTO;
import io.eduardnol.direccions.dto.ComboCodeDTO;
import io.eduardnol.direccions.dto.ComboDTO;
import io.eduardnol.direccions.dto.StreetDetailDTO;
import io.eduardnol.direccions.dto.StreetSearchResultDTO;

import java.util.List;

public interface DireccioService {
    List<ComboCodeDTO> getAllPais();
    List<ComboCodeDTO> getComunitatAutonomaByPais(Long idPais);
    List<ComboDTO> getProvinciaByComunitatAutonoma(Long idComunitatAutonoma);
    List<ComboDTO> getMunicipiByProvincia(Long idProvincia);
    List<ComboDTO> getTipusVia();
    Long checkCodiPostal(CheckCodiPostalDTO checkCodiPostalDTO);
    ComarcaDTO getComarcaByIdMunicipi(Long idMunicipi);
    List<StreetSearchResultDTO> searchStreets(String searchText);
    StreetDetailDTO getStreetDetailsById(Long idStreetName);
}
