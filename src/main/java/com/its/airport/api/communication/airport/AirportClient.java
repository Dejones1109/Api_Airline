package com.its.airport.api.communication.airport;

import com.its.airport.api.communication.dto.AirportRequest;
import com.its.airport.api.communication.dto.DepartureFlights;
import com.its.airport.api.communication.dto.FlightFare;
import com.its.airport.api.entities.OrderTicket;
import com.its.airport.api.repositories.AirportRepository;
import com.its.airport.api.repositories.OrderTickerRepository;
import com.its.airport.api.repositories.RouteRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

@Log4j2
@Component
public class AirportClient {
    @Autowired
    AirportRepository airportRepository;
    @Autowired
    RouteRepository routeRepository;
  @Autowired
  AirportCommunication airportCommunication;
    @Autowired
    OrderTickerRepository orderTickerRepository;

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
//    public Map<String, FlightFare> getTotalPriceCheap(int itineraryType,String departureAirportCode, String destinationAirportCode, String departureDate, String returnDate, int adult, int children, int infant) {
//        Map<String, FlightFare> mapTotalPrice = new HashMap<>();
//        List<FlightFare[]> listDepartureFlights = new ArrayList<>();
//        List<FlightFare[]> listReturnFlights = new ArrayList<>();
//
//        AirportRequest request = new AirportRequest();
//        request.setItineraryType(itineraryType);
//        request.setDepartureAirportCode(departureAirportCode);
//        request.setDestinationAirportCode(destinationAirportCode);
//        request.setDepartureDate(departureDate);
//        request.setReturnDate(returnDate);
//        request.setAdult(adult);
//        request.setChildren(children);
//        request.setInfant(infant);
//        String[] code = {"VN","VJ","QH"};
//        for (String key : code) {
//            request.setAirlineCode(key);
//            Long timestartRequest = System.currentTimeMillis();
//            log.info("key:{}", key);
//            DepartureFlights[] departureFlights = airportCommunication.searchAirport(request);
//            log.info("departureFlight:{}",departureFlights);
//            Long timeResponse = System.currentTimeMillis();
//            for (DepartureFlights departureFlight : departureFlights) {
//              if (departureFlight.getDepartureFlights().getFlightFares().length != 0 || departureFlight.getReturnFlights().getFlightFares().length != 0) {
//                    listDepartureFlights.add(departureFlight.getDepartureFlights().getFlightFares());
//                    listReturnFlights.add(departureFlight.getReturnFlights().getFlightFares());
//                }
//            }
//            log.info("api request-response:{}", timeResponse - timestartRequest);
//
//        }
//       log.info("listDepartureFlight:{}",listDepartureFlights);
//       log.info("listReturnFlight:{}",listReturnFlights);
//        FlightFare departureFlight = getTotalPriceMix(listDepartureFlights);
//        mapTotalPrice.put("departureFlight", departureFlight);
//        FlightFare returnFlight = getTotalPriceMix(listReturnFlights);
//        if(returnFlight.getTotalPrice()!=0.0){
//            mapTotalPrice.put("returnFlight", returnFlight);
//        }
//
//
//        return mapTotalPrice;
//
//    }

    public Map<String, FlightFare> getTotalPriceCheap(int itineraryType, String departureAirportCode, String destinationAirportCode, String departureDate, String returnDate, int adult, int children, int infant) {
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
        try {
            Long startTime = System.currentTimeMillis();

            ExecutorService executor = Executors.newFixedThreadPool(3);


            Future<FlightFare> f1 = (Future<FlightFare>) executor.submit(() -> {
                request.setAirlineCode("QH");
                DepartureFlights[] departureFlights = airportCommunication.searchAirport(request);
                for (DepartureFlights departureFlight : departureFlights) {
                    if (departureFlight.getDepartureFlights().getFlightFares().length != 0 || departureFlight.getReturnFlights().getFlightFares().length != 0) {
                        listDepartureFlights.add(departureFlight.getDepartureFlights().getFlightFares());
                        listReturnFlights.add(departureFlight.getReturnFlights().getFlightFares());
                    }
                }
                Long endTime3 = System.currentTimeMillis();
                log.info("Time processing 1: {}", endTime3 - startTime);
            });

            Future<FlightFare> f2 = (Future<FlightFare>) executor.submit(() -> {
                AirportRequest vnRequest = (AirportRequest) request.clone();
                vnRequest.setAirlineCode("VN");
                DepartureFlights[] departureFlightses = airportCommunication.searchAirport(vnRequest);
                for (DepartureFlights departureFlight : departureFlightses) {
                    if (departureFlight.getDepartureFlights().getFlightFares().length != 0 || departureFlight.getReturnFlights().getFlightFares().length != 0) {
                        listDepartureFlights.add(departureFlight.getDepartureFlights().getFlightFares());
                        listReturnFlights.add(departureFlight.getReturnFlights().getFlightFares());
                    }
                }
                Long endTime3 = System.currentTimeMillis();
                log.info("Time processing 2: {}", endTime3 - startTime);
            });

            Future<FlightFare> f3 = (Future<FlightFare>) executor.submit(() -> {
                AirportRequest vnRequest = (AirportRequest) request.clone();
                vnRequest.setAirlineCode("VJ");
                DepartureFlights[] departureFlightses = airportCommunication.searchAirport(vnRequest);
                for (DepartureFlights departureFlight : departureFlightses) {
                    if (departureFlight.getDepartureFlights().getFlightFares().length != 0 || departureFlight.getReturnFlights().getFlightFares().length != 0) {
                        listDepartureFlights.add(departureFlight.getDepartureFlights().getFlightFares());
                        listReturnFlights.add(departureFlight.getReturnFlights().getFlightFares());
                    }
                }
                Long endTime3 = System.currentTimeMillis();
                log.info("Time processing : {}", endTime3 - startTime);
            });

            try {
                f1.get();
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(AirportClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                f2.get();
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(AirportClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                f3.get();
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(AirportClient.class.getName()).log(Level.SEVERE, null, ex);
            }

            FlightFare departureFlight = getTotalPriceMix(listDepartureFlights);
            mapTotalPrice.put("departureFlight", departureFlight);
            FlightFare returnFlight = getTotalPriceMix(listReturnFlights);
            if (returnFlight.getTotalPrice() != 0.0) {
                mapTotalPrice.put("returnFlight", returnFlight);
            }

            log.info("listDepartureFlight:{}", listDepartureFlights);
            log.info("listReturnFlight:{}", listReturnFlights);
            return mapTotalPrice;

        } catch (Exception e) {
            log.error("error:{}", e);
            return null;
        }


    }

    public Object getOrderTicet(AirportRequest request){
        Map<String,Integer> map = new HashMap<>();
        try {
            OrderTicket orderTicket1 = new OrderTicket();
            orderTicket1.setCustomerName(request.getCustomerName());
            orderTicket1.setPhone(request.getPhone());
            orderTicket1.setPhoneOrder(request.getPhoneOrder());
            orderTicket1.setPhoneType(request.getPhone_type());
            orderTicket1.setStartCity(airportRepository.provinceName(request.getDepartureAirportCode()));
            orderTicket1.setEndCity(airportRepository.provinceName(request.getDestinationAirportCode()));
            orderTicket1.setStartDate(request.getDepartureDate());
            orderTicket1.setEndDate(request.getReturnDate());
            orderTicket1.setAdult(request.getAdult());
            orderTicket1.setChild(request.getChildren());
            orderTicket1.setInfant(request.getInfant());
            orderTicket1.setSuitcase(request.getSuitcase());
            orderTickerRepository.save(orderTicket1);
            log.info("ok");
            map.put("result",1);
        }catch (Exception e){
            log.error("error:{}",e);
            map.put("result",0);
        }

        return  map;
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
