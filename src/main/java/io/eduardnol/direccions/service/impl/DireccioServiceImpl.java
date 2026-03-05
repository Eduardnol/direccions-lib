package io.eduardnol.direccions.service.impl;

import io.eduardnol.direccions.dto.CheckCodiPostalDTO;
import io.eduardnol.direccions.dto.ComarcaDTO;
import io.eduardnol.direccions.dto.ComboCodeDTO;
import io.eduardnol.direccions.dto.ComboDTO;
import io.eduardnol.direccions.dto.PageResponseDTO;
import io.eduardnol.direccions.dto.StreetDetailDTO;
import io.eduardnol.direccions.dto.StreetSearchResultDTO;
import io.eduardnol.direccions.entity.CodiPostalEntity;
import io.eduardnol.direccions.entity.DireccioEntity;
import io.eduardnol.direccions.entity.MunicipiEntity;
import io.eduardnol.direccions.entity.StreetNameEntity;
import io.eduardnol.direccions.mapper.*;
import io.eduardnol.direccions.repository.*;
import io.eduardnol.direccions.service.DireccioService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class DireccioServiceImpl implements DireccioService {

    private final MunicipiRepository municipiRepository;
    private final ProvinciaRepository provinciaRepository;
    private final PaisRepository paisRepository;
    private final CodiPostalRepository codiPostalRepository;
    private final TipusViaRepository tipusViaRepository;
    private final ComunitatAutonomaRepository comunitatAutonomaRepository;
    private final DireccioRepository direccioRepository;
    private final StreetNameRepository streetNameRepository;
    private final PaisMapper paisMapper;
    private final ComunitatAutonomaMapper comunitatAutonomaMapper;
    private final ProvinciaMapper provinciaMapper;
    private final MunicipiMapper municipiMapper;
    private final TipusViaMapper tipusViaMapper;
    private final StreetNameMapper streetNameMapper;
    private final ComarcaMapper comarcaMapper;

    @Override
    public List<ComboCodeDTO> getAllPais() {
        return paisRepository.findAll().stream().map(paisMapper::toDTOComboCode).toList();
    }

    @Override
    public List<ComboCodeDTO> getComunitatAutonomaByPais(Long idPais) {
        return comunitatAutonomaRepository.findAllByPaisIdPais(idPais).stream()
                .map(comunitatAutonomaMapper::toComboCodeDTO).toList();
    }

    @Override
    public List<ComboDTO> getProvinciaByComunitatAutonoma(Long idComunitatAutonoma) {
        return provinciaRepository.findAllByComunitatAutonomaIdComunitatAutonoma(idComunitatAutonoma).stream()
                .map(provinciaMapper::toComboDTO).toList();
    }

    @Override
    public List<ComboDTO> getMunicipiByProvincia(Long idProvincia) {
        return municipiRepository.findAllByProvinciaIdProvinciaOrderByNom(idProvincia).stream()
                .map(municipiMapper::toComboDTO).toList();
    }

    @Override
    public List<ComboDTO> getTipusVia() {
        return tipusViaRepository.findAll().stream().map(tipusViaMapper::toComboDTO).toList();
    }

    @Override
    public Long checkCodiPostal(CheckCodiPostalDTO checkCodiPostalDTO) {
        Optional<CodiPostalEntity> codiPostal = codiPostalRepository
                .findByCodiPostalAndMunicipiIdMunicipi(checkCodiPostalDTO.getCodiPostal(), 
                        checkCodiPostalDTO.getIdMunicipi());
        return codiPostal.isPresent() ? codiPostal.get().getIdCodiPostal() : null;
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
    public List<StreetSearchResultDTO> searchStreets(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            return List.of();
        }
        List<StreetNameEntity> streetNames = streetNameRepository.searchByNom(searchText.trim());
        return streetNames.stream()
                .map(streetNameMapper::toStreetSearchResultDTO)
                .toList();
    }

    @Override
    public StreetDetailDTO getStreetDetailsById(Long idStreetName) {
        Optional<StreetNameEntity> streetName = streetNameRepository.findById(idStreetName);
        if (streetName.isEmpty()) {
            log.warn("StreetName with id {} not found", idStreetName);
            return null;
        }
        return streetNameMapper.toStreetDetailDTO(streetName.get());
    }

    @Override
    public PageResponseDTO<ComboDTO> getAllMunicipiPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<MunicipiEntity> municipiPage = municipiRepository.findAllByOrderByNom(pageable);
        
        List<ComboDTO> content = municipiPage.getContent().stream()
                .map(municipiMapper::toComboDTO)
                .toList();
        
        return PageResponseDTO.<ComboDTO>builder()
                .content(content)
                .page(municipiPage.getNumber())
                .size(municipiPage.getSize())
                .totalElements(municipiPage.getTotalElements())
                .totalPages(municipiPage.getTotalPages())
                .first(municipiPage.isFirst())
                .last(municipiPage.isLast())
                .build();
    }

    @Override
    public List<ComboDTO> getAllMunicipi() {
        return municipiRepository.findAllByOrderByNom().stream()
                .map(municipiMapper::toComboDTO)
                .toList();
    }

    @Override
    public List<ComboDTO> getMunicipiByComunitatAutonoma(Long idComunitatAutonoma) {
        return municipiRepository.findAllByProvinciaComunitatAutonomaIdComunitatAutonomaOrderByNom(idComunitatAutonoma)
                .stream()
                .map(municipiMapper::toComboDTO)
                .toList();
    }
}
