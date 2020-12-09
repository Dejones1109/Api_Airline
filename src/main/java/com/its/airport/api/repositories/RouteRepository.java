/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.airport.api.repositories;

import com.its.airport.api.entities.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author quangdt
 * @param <T>
 * @param <P>
 */
@Repository
public interface RouteRepository extends JpaRepository<Route, String>{
    @Query("SELECT r.status FROM Route r WHERE r.startPointId  = :startPointId AND r.endPointId = :endPointId")
    Integer searchRouteName(@Param("startPointId") Integer startPointId, @Param ("endPointId") Integer endPointId);
//    @Query("update  Route r set r.status=:status WHERE r.id =:id")
//    Integer Update(@Param("id") Integer id, @Param ("status") Integer status);

}
