package io.eduardnol.direccions.service;

import io.eduardnol.direccions.dto.StreetDetailDTO;
import io.eduardnol.direccions.dto.StreetSearchResultDTO;
import io.eduardnol.direccions.entity.*;
import io.eduardnol.direccions.mapper.DireccioMapper;
import io.eduardnol.direccions.repository.DireccioRepository;
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
    private DireccioRepository direccioRepository;

    @Mock
    private DireccioMapper direccioMapper;

    @InjectMocks
    private DireccioServiceImpl direccioService;

    @Test
    void searchStreets_WithValidText_ShouldReturnResults() {
        // Given
        String searchText = "Gran Via";
        
        TipusViaEntity tipusVia = TipusViaEntity.builder()
                .idTipusVia(1L)
                .nom("Calle")
                .build();
        
        MunicipiEntity municipi = MunicipiEntity.builder()
                .idMunicipi(1L)
                .nom("Barcelona")
                .build();
        
        ProvinciaEntity provincia = ProvinciaEntity.builder()
                .idProvincia(1L)
                .nom("Barcelona")
                .build();

        DireccioEntity direccio = DireccioEntity.builder()
                .idDireccio(1L)
                .nomVia("Gran Via")
                .tipusVia(tipusVia)
                .municipi(municipi)
                .provincia(provincia)
                .build();

        StreetSearchResultDTO expectedResult = StreetSearchResultDTO.builder()
                .idDireccio(1L)
                .nomVia("Gran Via")
                .tipusVia("Calle")
                .municipi("Barcelona")
                .provincia("Barcelona")
                .build();

        when(direccioRepository.searchByNomVia(searchText)).thenReturn(Arrays.asList(direccio));
        when(direccioMapper.toStreetSearchResultDTO(direccio)).thenReturn(expectedResult);

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
        Long idDireccio = 1L;
        
        PaisEntity pais = PaisEntity.builder()
                .idPais(1L)
                .nom("España")
                .build();
        
        ComunitatAutonomaEntity comunitatAutonoma = ComunitatAutonomaEntity.builder()
                .idComunitatAutonoma(1L)
                .nom("Cataluña")
                .build();
        
        ProvinciaEntity provincia = ProvinciaEntity.builder()
                .idProvincia(1L)
                .nom("Barcelona")
                .build();
        
        MunicipiEntity municipi = MunicipiEntity.builder()
                .idMunicipi(1L)
                .nom("Barcelona")
                .comarca("Barcelonès")
                .build();
        
        CodiPostalEntity codiPostal = new CodiPostalEntity(1L, "08001");
        
        TipusViaEntity tipusVia = TipusViaEntity.builder()
                .idTipusVia(1L)
                .nom("Calle")
                .build();

        DireccioEntity direccio = DireccioEntity.builder()
                .idDireccio(1L)
                .nomVia("Gran Via")
                .numero("123")
                .pis("2-1")
                .pais(pais)
                .comunitatAutonoma(comunitatAutonoma)
                .provincia(provincia)
                .municipi(municipi)
                .codiPostal(codiPostal)
                .tipusVia(tipusVia)
                .build();

        StreetDetailDTO expectedDetail = StreetDetailDTO.builder()
                .idDireccio(1L)
                .nomVia("Gran Via")
                .numero("123")
                .pis("2-1")
                .tipusVia("Calle")
                .codiPostal("08001")
                .municipi("Barcelona")
                .comarca("Barcelonès")
                .provincia("Barcelona")
                .comunitatAutonoma("Cataluña")
                .pais("España")
                .build();

        when(direccioRepository.findById(idDireccio)).thenReturn(Optional.of(direccio));
        when(direccioMapper.toStreetDetailDTO(direccio)).thenReturn(expectedDetail);

        // When
        StreetDetailDTO result = direccioService.getStreetDetailsById(idDireccio);

        // Then
        assertNotNull(result);
        assertEquals("Gran Via", result.getNomVia());
        assertEquals("Barcelona", result.getMunicipi());
        assertEquals("08001", result.getCodiPostal());
        assertEquals("Cataluña", result.getComunitatAutonoma());
    }

    @Test
    void getStreetDetailsById_WithInvalidId_ShouldReturnNull() {
        // Given
        Long idDireccio = 999L;
        when(direccioRepository.findById(idDireccio)).thenReturn(Optional.empty());

        // When
        StreetDetailDTO result = direccioService.getStreetDetailsById(idDireccio);

        // Then
        assertNull(result);
    }
}
