package io.eduardnol.direccions.repository;

import io.eduardnol.direccions.entity.DireccioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DireccioRepository extends JpaRepository<DireccioEntity, Long> {
}
