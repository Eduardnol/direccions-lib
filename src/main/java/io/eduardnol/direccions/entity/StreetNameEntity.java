package io.eduardnol.direccions.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "md_street_name")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StreetNameEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idStreetName;

    @Column(name = "nom", length = 255, nullable = false)
    private String nom;

    @ManyToOne
    @JoinColumn(name = "id_municipi", nullable = false)
    private MunicipiEntity municipi;
}
