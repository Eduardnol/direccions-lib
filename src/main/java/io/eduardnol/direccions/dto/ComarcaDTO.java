package io.eduardnol.direccions.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ComarcaDTO {
    private Long id;
    private String codi;
    private String nom;
}
