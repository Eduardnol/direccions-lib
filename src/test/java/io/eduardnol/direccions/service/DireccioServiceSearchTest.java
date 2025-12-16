package io.eduardnol.direccions.service;

import io.eduardnol.direccions.dto.ComboDTO;
import io.eduardnol.direccions.dto.StreetDetailDTO;
import io.eduardnol.direccions.dto.StreetSearchResultDTO;
import io.eduardnol.direccions.entity.*;
import io.eduardnol.direccions.mapper.StreetNameMapper;
import io.eduardnol.direccions.repository.StreetNameRepository;
import io.eduardnol.direccions.service.impl.DireccioServiceImpl;
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
class DireccioServiceSearchTest {

    @Mock
    private StreetNameRepository streetNameRepository;

    @Mock
    private StreetNameMapper streetNameMapper;

    @InjectMocks
    private DireccioServiceImpl direccioService;

    @Test
    void searchStreets_WithValidText_ShouldReturnResults() {
        // Given
        String searchText = "Gran Via";
        
        ProvinciaEntity provincia = ProvinciaEntity.builder()
                .idProvincia(1L)
                .nom("Barcelona")
                .build();
        
        MunicipiEntity municipi = MunicipiEntity.builder()
                .idMunicipi(1L)
                .nom("Barcelona")
                .provincia(provincia)
                .build();

        StreetNameEntity streetName = StreetNameEntity.builder()
                .idStreetName(1L)
                .nom("Gran Via")
                .municipi(municipi)
                .build();

        StreetSearchResultDTO expectedResult = StreetSearchResultDTO.builder()
                .idStreetName(1L)
                .nomVia("Gran Via")
                .municipi("Barcelona")
                .provincia("Barcelona")
                .build();

        when(streetNameRepository.searchByNom(searchText)).thenReturn(Arrays.asList(streetName));
        when(streetNameMapper.toStreetSearchResultDTO(streetName)).thenReturn(expectedResult);

        // When
        List<StreetSearchResultDTO> result = direccioService.searchStreets(searchText);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Gran Via", result.get(0).getNomVia());
        assertEquals("Barcelona", result.get(0).getMunicipi());
    }

    @Test
    void searchStreets_WithEmptyText_ShouldReturnEmptyList() {
        // When
        List<StreetSearchResultDTO> result = direccioService.searchStreets("");

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void searchStreets_WithNullText_ShouldReturnEmptyList() {
        // When
        List<StreetSearchResultDTO> result = direccioService.searchStreets(null);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getStreetDetailsById_WithValidId_ShouldReturnDetails() {
        // Given
        Long idStreetName = 1L;
        
        PaisEntity pais = PaisEntity.builder()
                .idPais(1L)
                .nom("España")
                .build();
        
        ComunitatAutonomaEntity comunitatAutonoma = ComunitatAutonomaEntity.builder()
                .idComunitatAutonoma(1L)
                .nom("Cataluña")
                .pais(pais)
                .build();
        
        ProvinciaEntity provincia = ProvinciaEntity.builder()
                .idProvincia(1L)
                .nom("Barcelona")
                .comunitatAutonoma(comunitatAutonoma)
                .build();
        
        MunicipiEntity municipi = MunicipiEntity.builder()
                .idMunicipi(1L)
                .nom("Barcelona")
                .comarca("Barcelonès")
                .provincia(provincia)
                .build();

        StreetNameEntity streetName = StreetNameEntity.builder()
                .idStreetName(1L)
                .nom("Gran Via")
                .municipi(municipi)
                .build();

        StreetDetailDTO expectedDetail = StreetDetailDTO.builder()
                .idStreetName(1L)
                .nomVia("Gran Via")
                .municipi(ComboDTO.builder().key(1L).value("Barcelona").build())
                .comarca(ComboDTO.builder().key(null).value("Barcelonès").build())
                .provincia(ComboDTO.builder().key(1L).value("Barcelona").build())
                .comunitatAutonoma(ComboDTO.builder().key(1L).value("Cataluña").build())
                .pais(ComboDTO.builder().key(1L).value("España").build())
                .build();

        when(streetNameRepository.findById(idStreetName)).thenReturn(Optional.of(streetName));
        when(streetNameMapper.toStreetDetailDTO(streetName)).thenReturn(expectedDetail);

        // When
        StreetDetailDTO result = direccioService.getStreetDetailsById(idStreetName);

        // Then
        assertNotNull(result);
        assertEquals("Gran Via", result.getNomVia());
        assertEquals("Barcelona", result.getMunicipi().getValue());
        assertEquals("Barcelonès", result.getComarca().getValue());
        assertEquals("Cataluña", result.getComunitatAutonoma().getValue());
    }

    @Test
    void getStreetDetailsById_WithInvalidId_ShouldReturnNull() {
        // Given
        Long idStreetName = 999L;
        when(streetNameRepository.findById(idStreetName)).thenReturn(Optional.empty());

        // When
        StreetDetailDTO result = direccioService.getStreetDetailsById(idStreetName);

        // Then
        assertNull(result);
    }
}
