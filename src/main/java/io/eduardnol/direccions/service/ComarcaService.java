package io.eduardnol.direccions.service;

import io.eduardnol.direccions.dto.ComarcaDTO;
import io.eduardnol.direccions.dto.ComboDTO;

import java.util.List;

public interface ComarcaService {
    List<ComarcaDTO> getAllComarques();
    ComarcaDTO getComarcaByIdMunicipi(Long idMunicipi);
    List<ComboDTO> getMunicipisByIdComarca(Long idComarca);
}
