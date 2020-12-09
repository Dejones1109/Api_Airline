package com.its.airport.api.repositories.common;

import com.its.airport.api.repositories.AirportRepository;
import com.its.airport.api.repositories.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CommonRepository {
    @Autowired
    AirportRepository airportRepository;
    @Autowired
    RouteRepository routeRepository;
}
