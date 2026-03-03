package io.eduardnol.direccions.controller;

import io.eduardnol.direccions.dto.ComarcaDTO;
import io.eduardnol.direccions.dto.ComboDTO;
import io.eduardnol.direccions.service.ComarcaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comarques")
@AllArgsConstructor
public class ComarcaController implements ComarcaApi {

    private final ComarcaService comarcaService;

    @Override
    @GetMapping
    public List<ComarcaDTO> getAllComarques() {
        return comarcaService.getAllComarques();
    }

    @Override
    @GetMapping("/municipi/{idMunicipi}")
    public ResponseEntity<ComarcaDTO> getComarcaByIdMunicipi(@PathVariable Long idMunicipi) {
        ComarcaDTO comarcaDTO = comarcaService.getComarcaByIdMunicipi(idMunicipi);
        if (comarcaDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(comarcaDTO);
    }

    @Override
    @GetMapping("/{idComarca}/municipis")
    public List<ComboDTO> getMunicipisByIdComarca(@PathVariable Long idComarca) {
        return comarcaService.getMunicipisByIdComarca(idComarca);
    }
}
