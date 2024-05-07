package tyt.sales.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferDTO {
    @JsonIgnore
    private long id;
    private String name;
    @JsonIgnore
    private String offerType;


}
