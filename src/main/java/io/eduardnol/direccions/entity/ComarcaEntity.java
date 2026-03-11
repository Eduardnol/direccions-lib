package io.eduardnol.direccions.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "md_comarques")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ComarcaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComarca;

    @Column(name = "codi", length = 10, nullable = false, unique = true)
    private String codi;

    @Column(name = "nom", length = 255, nullable = false)
    private String nom;
}
