package io.eduardnol.direccions.repository;

import io.eduardnol.direccions.entity.StreetNameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StreetNameRepository extends JpaRepository<StreetNameEntity, Long> {
    
    @Query("SELECT s FROM StreetNameEntity s " +
           "WHERE LOWER(s.nom) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    List<StreetNameEntity> searchByNom(@Param("searchText") String searchText);
}
