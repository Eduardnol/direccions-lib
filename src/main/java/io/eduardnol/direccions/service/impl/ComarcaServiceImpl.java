package io.eduardnol.direccions.service.impl;

import io.eduardnol.direccions.dto.ComarcaDTO;
import io.eduardnol.direccions.dto.ComboDTO;
import io.eduardnol.direccions.entity.MunicipiEntity;
import io.eduardnol.direccions.mapper.ComarcaMapper;
import io.eduardnol.direccions.mapper.MunicipiMapper;
import io.eduardnol.direccions.repository.ComarcaRepository;
import io.eduardnol.direccions.repository.MunicipiRepository;
import io.eduardnol.direccions.service.ComarcaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class ComarcaServiceImpl implements ComarcaService {

    private final ComarcaRepository comarcaRepository;
    private final MunicipiRepository municipiRepository;
    private final ComarcaMapper comarcaMapper;
    private final MunicipiMapper municipiMapper;

    @Override
    public List<ComarcaDTO> getAllComarques() {
        return comarcaRepository.findAll().stream()
                .map(comarcaMapper::toComarcaDTO)
                .toList();
    }

    @Override
    public ComarcaDTO getComarcaByIdMunicipi(Long idMunicipi) {
        MunicipiEntity municipi = municipiRepository.findById(idMunicipi).orElse(null);
        if (municipi == null || municipi.getComarca() == null) {
            log.warn("Municipi with id {} not found or has no comarca", idMunicipi);
            return null;
        }
        return comarcaMapper.toComarcaDTO(municipi.getComarca());
    }

    @Override
    public List<ComboDTO> getMunicipisByIdComarca(Long idComarca) {
        return municipiRepository.findAllByComarcaIdComarcaOrderByNom(idComarca).stream()
                .map(municipiMapper::toComboDTO)
                .toList();
    }
}
