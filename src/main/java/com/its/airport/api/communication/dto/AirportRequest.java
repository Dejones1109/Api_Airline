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
    @JsonProperty("cus_name")
    String customerName;
    @JsonProperty("phone")
    String phone;
    @JsonProperty("phone_order")
    String phoneOrder;

    @JsonProperty("phone_type")
    int phone_type;
    @JsonProperty("suitcase")
    int suitcase;
    @Override
    public Object clone() {
        AirportRequest request = new AirportRequest();
        request.setItineraryType(itineraryType);
        request.setDepartureAirportCode(departureAirportCode);
        request.setDestinationAirportCode(destinationAirportCode);
        request.setDepartureDate(departureDate);
        request.setReturnDate(returnDate);
        request.setAdult(adult);
        request.setChildren(children);
        request.setInfant(infant);
        return request;
    }
}
