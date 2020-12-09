package com.its.airport.api.communication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class AirportRequest {
    @JsonProperty("itineraryType")
    Integer itineraryType;
    @JsonProperty("departureAirportCode")
    String departureAirportCode;
    @JsonProperty("destinationAirportCode")
    String destinationAirportCode;
    @JsonProperty("departureDate")
    String departureDate;
    @JsonProperty("returnDate")
    String returnDate;
    @JsonProperty("adult")
    Integer adult;
    @JsonProperty("children")
    Integer children;
    @JsonProperty("infant")
    Integer infant;
    @JsonProperty("airlineCode")
    String airlineCode;
}
