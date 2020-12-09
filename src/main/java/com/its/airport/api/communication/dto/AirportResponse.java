package com.its.airport.api.communication.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AirportResponse<T> {
    @JsonProperty("message")
    String message;
    @JsonProperty("margin")
    Double margin;
    @JsonProperty("data")
    T data;
}

