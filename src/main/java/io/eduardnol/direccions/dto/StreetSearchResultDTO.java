package io.eduardnol.direccions.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StreetSearchResultDTO {
    private Long idStreetName;
    private String nomVia;
    private String municipi;
    private String provincia;
}
