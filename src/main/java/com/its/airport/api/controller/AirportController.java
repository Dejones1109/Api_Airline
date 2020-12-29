package com.its.airport.api.controller;

import com.its.airport.api.communication.airport.AirportClient;
import com.its.airport.api.communication.airport.AirportCommunication;
import com.its.airport.api.communication.dto.AirportRequest;
import com.its.airport.api.communication.dto.DepartureFlights;
import com.its.airport.api.communication.dto.FlightFare;
import com.its.airport.api.repositories.HouseHoldRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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

    @Autowired
    HouseHoldRepository houseHoldRepository;
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
    @PostMapping("orderTicket")
    public  Object getOrderTicket(@RequestBody AirportRequest request){
        return  airportClient.getOrderTicet(request);
    }
    @GetMapping("ho_khau")
    public Map<String,String> getContent(@RequestParam String thu_tuc, @RequestParam String yeu_cau, @RequestParam String cap_xu_ly){
        Map<String,String> content = new HashMap<>();
        try {
            log.info("thu_tuc:{}",thu_tuc);
            log.info("yeu_cau:{}",yeu_cau);
            log.info("cap_xu_ly:{}",cap_xu_ly);
            if(thu_tuc==null || yeu_cau== null||cap_xu_ly==null){
               content.put("error","the field not null");
            }else {
                content.put("noi_dung",houseHoldRepository.content(thu_tuc, yeu_cau, cap_xu_ly));
                log.info("noi_dung:{}",houseHoldRepository.content(thu_tuc, yeu_cau, cap_xu_ly));
            }
        }catch (Exception e){
            log.error("error",e);
        content.put("error",e.getMessage());
        }

        return content;
    }

}
