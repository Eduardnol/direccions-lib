package io.eduardnol.direccions.controller;

import io.eduardnol.direccions.dto.ComarcaDTO;
import io.eduardnol.direccions.dto.ComboDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ComarcaApi {
    List<ComarcaDTO> getAllComarques();
    ResponseEntity<ComarcaDTO> getComarcaByIdMunicipi(Long idMunicipi);
    List<ComboDTO> getMunicipisByIdComarca(Long idComarca);
}
