package io.eduardnol.direccions.repository;

import io.eduardnol.direccions.entity.MunicipiEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MunicipiRepository extends JpaRepository<MunicipiEntity, Long> {
    List<MunicipiEntity> findAllByProvinciaIdProvinciaOrderByNom(Long idProvincia);
    List<MunicipiEntity> findAllByComarcaIdComarcaOrderByNom(Long idComarca);
    Optional<MunicipiEntity> findByCodi(String codi);
    Optional<MunicipiEntity> findByNomIgnoreCase(String nom);
    Optional<MunicipiEntity> findFirstByNom(String nom);
    
    // New methods for the requirements
    Page<MunicipiEntity> findAllByOrderByNom(Pageable pageable);
    List<MunicipiEntity> findAllByOrderByNom();
    List<MunicipiEntity> findAllByProvinciaComunitatAutonomaIdComunitatAutonomaOrderByNom(Long idComunitatAutonoma);
}
