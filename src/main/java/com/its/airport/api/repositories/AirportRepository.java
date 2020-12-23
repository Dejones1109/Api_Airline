/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.airport.api.repositories;

import com.its.airport.api.entities.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @param <T>
 * @param <P>
 * @author quangdt
 */
@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {
    @Query("SELECT r.id FROM Airport r WHERE lower(r.airportName)  = :airportName")
    Integer searchAirportID(@Param("airportName") String airportName);
    @Query("SELECT r.provinceName FROM Airport r WHERE r.airportCode=:airportCode")
    String provinceName(@Param("airportCode") String airportCode);

}
