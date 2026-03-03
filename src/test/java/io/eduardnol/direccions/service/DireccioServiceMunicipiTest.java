package io.eduardnol.direccions.service;

import io.eduardnol.direccions.dto.ComboDTO;
import io.eduardnol.direccions.dto.PageResponseDTO;
import io.eduardnol.direccions.entity.MunicipiEntity;
import io.eduardnol.direccions.entity.ProvinciaEntity;
import io.eduardnol.direccions.entity.ComunitatAutonomaEntity;
import io.eduardnol.direccions.mapper.ComarcaMapper;
import io.eduardnol.direccions.mapper.MunicipiMapper;
import io.eduardnol.direccions.repository.MunicipiRepository;
import io.eduardnol.direccions.service.impl.DireccioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DireccioServiceMunicipiTest {

    @Mock
    private MunicipiRepository municipiRepository;

    @Mock
    private ComarcaMapper comarcaMapper;

    @Mock
    private MunicipiMapper municipiMapper;

    @InjectMocks
    private DireccioServiceImpl direccioService;

    private ComunitatAutonomaEntity ca;
    private ProvinciaEntity provincia;
    private MunicipiEntity municipi1;
    private MunicipiEntity municipi2;
    private ComboDTO combo1;
    private ComboDTO combo2;

    @BeforeEach
    void setUp() {
        ca = ComunitatAutonomaEntity.builder()
                .idComunitatAutonoma(1L)
                .nom("Catalunya")
                .build();

        provincia = ProvinciaEntity.builder()
                .idProvincia(1L)
                .nom("Barcelona")
                .comunitatAutonoma(ca)
                .build();

        municipi1 = MunicipiEntity.builder()
                .idMunicipi(1L)
                .codi("08001")
                .nom("Barcelona")
                .provincia(provincia)
                .build();

        municipi2 = MunicipiEntity.builder()
                .idMunicipi(2L)
                .codi("08002")
                .nom("Badalona")
                .provincia(provincia)
                .build();

        combo1 = ComboDTO.builder()
                .key(1L)
                .value("Barcelona")
                .build();

        combo2 = ComboDTO.builder()
                .key(2L)
                .value("Badalona")
                .build();
    }

    @Test
    void getAllMunicipi_ShouldReturnListOfMunicipalities() {
        // Given
        when(municipiRepository.findAllByOrderByNom()).thenReturn(Arrays.asList(municipi1, municipi2));
        when(municipiMapper.toComboDTO(municipi1)).thenReturn(combo1);
        when(municipiMapper.toComboDTO(municipi2)).thenReturn(combo2);

        // When
        List<ComboDTO> result = direccioService.getAllMunicipi();

        // Then
        assertEquals(2, result.size());
        assertEquals("Barcelona", result.get(0).getValue());
        assertEquals("Badalona", result.get(1).getValue());
    }

    @Test
    void getAllMunicipiPaginated_ShouldReturnPagedResponse() {
        // Given
        Pageable pageable = PageRequest.of(0, 20);
        Page<MunicipiEntity> municipiPage = new PageImpl<>(Arrays.asList(municipi1), pageable, 1);

        when(municipiRepository.findAllByOrderByNom(any(Pageable.class))).thenReturn(municipiPage);
        when(municipiMapper.toComboDTO(municipi1)).thenReturn(combo1);

        // When
        PageResponseDTO<ComboDTO> result = direccioService.getAllMunicipiPaginated(0, 20);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(0, result.getPage());
        assertEquals(20, result.getSize());
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertTrue(result.isFirst());
        assertTrue(result.isLast());
        assertEquals("Barcelona", result.getContent().get(0).getValue());
    }

    @Test
    void getMunicipiByComunitatAutonoma_ShouldReturnListOfMunicipalities() {
        // Given
        when(municipiRepository.findAllByProvinciaComunitatAutonomaIdComunitatAutonomaOrderByNom(1L))
                .thenReturn(Arrays.asList(municipi1, municipi2));
        when(municipiMapper.toComboDTO(municipi1)).thenReturn(combo1);
        when(municipiMapper.toComboDTO(municipi2)).thenReturn(combo2);

        // When
        List<ComboDTO> result = direccioService.getMunicipiByComunitatAutonoma(1L);

        // Then
        assertEquals(2, result.size());
        assertEquals("Barcelona", result.get(0).getValue());
        assertEquals("Badalona", result.get(1).getValue());
    }
}
