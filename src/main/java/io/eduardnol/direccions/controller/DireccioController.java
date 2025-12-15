package io.eduardnol.direccions.controller;

import io.eduardnol.direccions.dto.CheckCodiPostalDTO;
import io.eduardnol.direccions.dto.ComarcaDTO;
import io.eduardnol.direccions.dto.ComboCodeDTO;
import io.eduardnol.direccions.dto.ComboDTO;
import io.eduardnol.direccions.dto.StreetDetailDTO;
import io.eduardnol.direccions.dto.StreetSearchResultDTO;
import io.eduardnol.direccions.service.DireccioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/direccions")
@AllArgsConstructor
public class DireccioController implements DireccioApi {

    private final DireccioService direccioService;

    @GetMapping("/pais")
    @Override
    public List<ComboCodeDTO> getAllPais() {
        return direccioService.getAllPais();
    }

    @GetMapping("/comunitat-autonoma/{idPais}")
    @Override
    public List<ComboCodeDTO> getComunitatAutonomaByPais(@PathVariable Long idPais) {
        return direccioService.getComunitatAutonomaByPais(idPais);
    }

    @GetMapping("/provincia/{idComunitatAutonoma}")
    @Override
    public List<ComboDTO> getProvinciaByComunitatAutonoma(@PathVariable Long idComunitatAutonoma) {
        return direccioService.getProvinciaByComunitatAutonoma(idComunitatAutonoma);
    }

    @GetMapping("/municipi/{idProvincia}")
    @Override
    public List<ComboDTO> getMunicipiByIdProvincia(@PathVariable Long idProvincia) {
        return direccioService.getMunicipiByProvincia(idProvincia);
    }

    @GetMapping("/tipus-via")
    @Override
    public List<ComboDTO> getTipusVia() {
        return direccioService.getTipusVia();
    }

    @Override
    @PostMapping("/codi-postal/check")
    public Long checkCodiPostal(@RequestBody CheckCodiPostalDTO checkCodiPostalDTO) {
        return direccioService.checkCodiPostal(checkCodiPostalDTO);
    }

    @Override
    @GetMapping("/comarca/{idMunicipi}")
    public ResponseEntity<ComarcaDTO> getComarcaByIdMunicipi(@PathVariable Long idMunicipi) {
        ComarcaDTO comarcaDTO = direccioService.getComarcaByIdMunicipi(idMunicipi);
        if (comarcaDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(comarcaDTO);
    }

    @Override
    @GetMapping("/search/streets")
    public List<StreetSearchResultDTO> searchStreets(@RequestParam("q") @jakarta.validation.constraints.NotBlank String searchText) {
        return direccioService.searchStreets(searchText);
    }

    @Override
    @GetMapping("/street/{idStreetName}")
    public ResponseEntity<StreetDetailDTO> getStreetDetailsById(@PathVariable Long idStreetName) {
        StreetDetailDTO streetDetailDTO = direccioService.getStreetDetailsById(idStreetName);
        if (streetDetailDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(streetDetailDTO);
    }
}
