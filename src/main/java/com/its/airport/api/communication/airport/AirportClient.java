package com.its.airport.api.communication.airport;

import com.its.airport.api.communication.dto.AirportRequest;
import com.its.airport.api.communication.dto.DepartureFlights;
import com.its.airport.api.communication.dto.FlightFare;
import com.its.airport.api.repositories.AirportRepository;
import com.its.airport.api.repositories.RouteRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Component
public class AirportClient {
    @Autowired
    AirportRepository airportRepository;
    @Autowired
    RouteRepository routeRepository;
    @Autowired
    AirportCommunication airportCommunication;

    public Map<String, Integer> isCheckRoute(String startPoint, String endPoint) {
        try {
            Map<String, Integer> mapRoute = new HashMap<>();
            int startPointId = airportRepository.searchAirportID(startPoint.toLowerCase());
            log.info("startPointId:{}", startPointId);
            int endPointId = airportRepository.searchAirportID(endPoint.toLowerCase());
            log.info("endPointId:{}", endPointId);
            int status = routeRepository.searchRouteName(startPointId, endPointId);
            mapRoute.put("status", status);
            return mapRoute;
        } catch (Exception e) {
            log.error("error:{}", e.getMessage());
            return null;
        }
    }

    //    @Scheduled(cron = "0 0 0 1 1/1 *")
//    public String searchAirport() {
//        try {
//            List<Route> routeList = routeRepository.findAll();
//            for (Route route : routeList) {
//                log.info("oldRoute:{}", route);
//
//                String airportCodeStart = airportRepository.searchAirportCode(route.getStartPointId());
//                String airportCodeEnd = airportRepository.searchAirportCode(route.getEndPointId());
//                AirportRequest request = new AirportRequest();
//                request.setDepartureAirportCode(airportCodeStart);
//                request.setDestinationAirportCode(airportCodeEnd);
//                request.setItineraryType(1);
//
//                request.setDepartureDate("2020-12-31");
//                request.setReturnDate("2020-12-31");
//                request.setAdult(1);
//                request.setChildren(0);
//                request.setInfant(0);
//                request.setAirlineCode("VN");
//                log.info("AirRequest:{}", request);
//                DepartureFlights[] departureFlightsList = airportCommunication.searchAirport(request);
//
//                for (DepartureFlights departureFlights : departureFlightsList) {
//                    Flight flight = departureFlights.g();
//                    log.info("flight:{}", flight);
//                    FlightFare[] flightFares = flight.getFlightFares();
//                    log.info("flightFares:{}", flightFares.length);
//                    if (flightFares.length == 0) {
//                        route.setStatus(0);
//                        routeRepository.saveAndFlush(route);
//                    }
//
//                    log.info("status:{}", route.getStatus());
//                    log.info("newRoute:{}", route);
//                }
//
//
//            }
//            log.info("update to success");
//            return "update to success";
//        } catch (Exception e) {
//            log.error("error:{}", e);
//            return "error";
//        }
//    }
    public Map<String, FlightFare> getTotalPriceCheap(int itineraryType,String departureAirportCode, String destinationAirportCode, String departureDate, String returnDate, int adult, int children, int infant) {
        Map<String, FlightFare> mapTotalPrice = new HashMap<>();
        List<FlightFare[]> listDepartureFlights = new ArrayList<>();
        List<FlightFare[]> listReturnFlights = new ArrayList<>();

        AirportRequest request = new AirportRequest();

            request.setItineraryType(itineraryType);


        request.setDepartureAirportCode(departureAirportCode);
        request.setDestinationAirportCode(destinationAirportCode);
        request.setDepartureDate(departureDate);
        request.setReturnDate(returnDate);
        request.setAdult(adult);
        request.setChildren(children);
        request.setInfant(infant);
        String[] code = {"VN", "VJ", "QH"};
        for (String key : code) {
            request.setAirlineCode(key);
            Long timestartRequest = System.currentTimeMillis();
            log.info("key:{}", key);
            DepartureFlights[] departureFlights = airportCommunication.searchAirport(request);
            log.info("departureFlight:{}",departureFlights);
            Long timeResponse = System.currentTimeMillis();
            for (DepartureFlights departureFlight : departureFlights) {
              if (departureFlight.getDepartureFlights().getFlightFares().length != 0 || departureFlight.getReturnFlights().getFlightFares().length != 0) {
                    listDepartureFlights.add(departureFlight.getDepartureFlights().getFlightFares());
                    listReturnFlights.add(departureFlight.getReturnFlights().getFlightFares());
                }
            }
            log.info("api request-response:{}", timeResponse - timestartRequest);

        }
       log.info("listDepartureFlight:{}",listDepartureFlights);
       log.info("listReturnFlight:{}",listReturnFlights);
        FlightFare departureFlight = getTotalPriceMix(listDepartureFlights);
        mapTotalPrice.put("departureFlight", departureFlight);
        FlightFare returnFlight = getTotalPriceMix(listReturnFlights);
        if(returnFlight.getTotalPrice()!=0.0){
            mapTotalPrice.put("returnFlight", returnFlight);
        }


        return mapTotalPrice;

    }

    private FlightFare getTotalPriceMix(List<FlightFare[]> list) {
        FlightFare flightFareMin = new FlightFare();
        flightFareMin.setTotalPrice(0.0);
        for (FlightFare[] listFlight : list) {

            for (FlightFare flightFare : listFlight) {
                if (flightFareMin.getTotalPrice() == 0) {
                    flightFareMin.setAirlineCode(flightFare.getAirlineCode());
                    flightFareMin.setTotalPrice(flightFare.getTotalPrice());
                } else {
                    if (flightFare.getTotalPrice() <= flightFareMin.getTotalPrice()) {

                        flightFareMin.setAirlineCode(flightFare.getAirlineCode());
                        flightFareMin.setTotalPrice(flightFare.getTotalPrice());
                    }
                }

            }
        }


        return flightFareMin;
    }
}
