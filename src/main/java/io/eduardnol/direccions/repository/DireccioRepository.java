package io.eduardnol.direccions.repository;

import io.eduardnol.direccions.entity.DireccioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DireccioRepository extends JpaRepository<DireccioEntity, Long> {
    
    @Query("SELECT d FROM DireccioEntity d " +
           "WHERE LOWER(d.nomVia) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    List<DireccioEntity> searchByNomVia(@Param("searchText") String searchText);
}
