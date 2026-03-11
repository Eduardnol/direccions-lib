package io.eduardnol.direccions.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "md_municipi")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MunicipiEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMunicipi;

    @Column(name = "codi", length = 10)
    private String codi;

    @Column(name = "nom")
    private String nom;

    @ManyToOne
    @JoinColumn(name = "id_comarca")
    private ComarcaEntity comarca;

    @ManyToOne
    @JoinColumn(name = "id_provincia")
    private ProvinciaEntity provincia;

    public MunicipiEntity(Long idMunicipi) {
        this.idMunicipi = idMunicipi;
    }
}
