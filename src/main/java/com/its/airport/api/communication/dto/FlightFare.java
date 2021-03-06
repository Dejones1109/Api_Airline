package com.its.airport.api.communication.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
  public class FlightFare {
    @JsonProperty("totalPrice")
    Double totalPrice;
    @JsonProperty("airlineCode")
    String airlineCode;
}
