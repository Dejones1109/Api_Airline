package com.its.airport.api.controller;

import com.its.airport.api.communication.airport.AirportClient;
import com.its.airport.api.communication.airport.AirportCommunication;
import com.its.airport.api.communication.dto.AirportRequest;
import com.its.airport.api.communication.dto.DepartureFlights;
import com.its.airport.api.communication.dto.FlightFare;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

;

@RestController
@Log4j2
@RequestMapping("/api")

public class AirportController {


    @Autowired
    AirportClient airportClient;
    @Autowired
    AirportCommunication airportCommunication;


    @GetMapping("checkRoute")
    public Map<String, Integer> isCheckRoute(@RequestParam String startPoint, @RequestParam String endPoint) {
        return airportClient.isCheckRoute(startPoint, endPoint);
    }

    @PostMapping("searchAirport")
    public DepartureFlights[] searchAirport(@RequestBody AirportRequest request) {

        return airportCommunication.searchAirport(request);
    }

    @PostMapping("getTotalPrice")
    public Map<String, FlightFare> totalPrice(@RequestBody AirportRequest request) {

        return airportClient.getTotalPriceCheap(request.getItineraryType(),request.getDepartureAirportCode(),request.getDestinationAirportCode(),request.getDepartureDate(),request.getReturnDate(),request.getAdult(),request.getChildren(),request.getInfant());
    }

}
