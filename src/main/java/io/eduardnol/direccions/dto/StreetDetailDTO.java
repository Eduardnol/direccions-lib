package io.eduardnol.direccions.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StreetDetailDTO {
    private Long idDireccio;
    private String nomVia;
    private String numero;
    private String pis;
    private String tipusVia;
    private String codiPostal;
    private String municipi;
    private String comarca;
    private String provincia;
    private String comunitatAutonoma;
    private String pais;
}
