package io.eduardnol.direccions.service;

import io.eduardnol.direccions.dto.ComarcaDTO;
import io.eduardnol.direccions.dto.ComboDTO;
import io.eduardnol.direccions.entity.ComarcaEntity;
import io.eduardnol.direccions.entity.MunicipiEntity;
import io.eduardnol.direccions.mapper.ComarcaMapper;
import io.eduardnol.direccions.mapper.MunicipiMapper;
import io.eduardnol.direccions.repository.ComarcaRepository;
import io.eduardnol.direccions.repository.MunicipiRepository;
import io.eduardnol.direccions.service.impl.ComarcaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ComarcaServiceTest {

    @Mock
    private ComarcaRepository comarcaRepository;

    @Mock
    private MunicipiRepository municipiRepository;

    @Mock
    private ComarcaMapper comarcaMapper;

    @Mock
    private MunicipiMapper municipiMapper;

    @InjectMocks
    private ComarcaServiceImpl comarcaService;

    private ComarcaEntity comarca1;
    private ComarcaEntity comarca2;
    private ComarcaDTO comarcaDTO1;
    private ComarcaDTO comarcaDTO2;
    private MunicipiEntity municipi1;
    private MunicipiEntity municipi2;
    private ComboDTO combo1;
    private ComboDTO combo2;

    @BeforeEach
    void setUp() {
        comarca1 = ComarcaEntity.builder()
                .idComarca(1L)
                .codi("BAR")
                .nom("Barcelonès")
                .build();

        comarca2 = ComarcaEntity.builder()
                .idComarca(2L)
                .codi("MAR")
                .nom("Maresme")
                .build();

        comarcaDTO1 = ComarcaDTO.builder()
                .id(1L)
                .codi("BAR")
                .nom("Barcelonès")
                .build();

        comarcaDTO2 = ComarcaDTO.builder()
                .id(2L)
                .codi("MAR")
                .nom("Maresme")
                .build();

        municipi1 = MunicipiEntity.builder()
                .idMunicipi(1L)
                .nom("Barcelona")
                .comarca(comarca1)
                .build();

        municipi2 = MunicipiEntity.builder()
                .idMunicipi(2L)
                .nom("Badalona")
                .comarca(comarca1)
                .build();

        combo1 = ComboDTO.builder().key(1L).value("Barcelona").build();
        combo2 = ComboDTO.builder().key(2L).value("Badalona").build();
    }

    @Test
    void getAllComarques_ShouldReturnAllComarques() {
        when(comarcaRepository.findAll()).thenReturn(Arrays.asList(comarca1, comarca2));
        when(comarcaMapper.toComarcaDTO(comarca1)).thenReturn(comarcaDTO1);
        when(comarcaMapper.toComarcaDTO(comarca2)).thenReturn(comarcaDTO2);

        List<ComarcaDTO> result = comarcaService.getAllComarques();

        assertEquals(2, result.size());
        assertEquals("Barcelonès", result.get(0).getNom());
        assertEquals("Maresme", result.get(1).getNom());
    }

    @Test
    void getComarcaByIdMunicipi_ShouldReturnComarca() {
        when(municipiRepository.findById(1L)).thenReturn(Optional.of(municipi1));
        when(comarcaMapper.toComarcaDTO(comarca1)).thenReturn(comarcaDTO1);

        ComarcaDTO result = comarcaService.getComarcaByIdMunicipi(1L);

        assertNotNull(result);
        assertEquals("BAR", result.getCodi());
        assertEquals("Barcelonès", result.getNom());
    }

    @Test
    void getComarcaByIdMunicipi_WhenMunicipiNotFound_ShouldReturnNull() {
        when(municipiRepository.findById(99L)).thenReturn(Optional.empty());

        ComarcaDTO result = comarcaService.getComarcaByIdMunicipi(99L);

        assertNull(result);
    }

    @Test
    void getMunicipisByIdComarca_ShouldReturnMunicipis() {
        when(municipiRepository.findAllByComarcaIdComarcaOrderByNom(1L))
                .thenReturn(Arrays.asList(municipi2, municipi1));
        when(municipiMapper.toComboDTO(municipi2)).thenReturn(combo2);
        when(municipiMapper.toComboDTO(municipi1)).thenReturn(combo1);

        List<ComboDTO> result = comarcaService.getMunicipisByIdComarca(1L);

        assertEquals(2, result.size());
        assertEquals("Badalona", result.get(0).getValue());
        assertEquals("Barcelona", result.get(1).getValue());
    }
}
