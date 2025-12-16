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
    private Long idStreetName;
    private String nomVia;
    private ComboDTO municipi;
    private ComboDTO comarca;
    private ComboDTO provincia;
    private ComboDTO comunitatAutonoma;
    private ComboDTO pais;
}
