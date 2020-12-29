package com.its.airport.api.repositories;

import com.its.airport.api.entities.HouseHold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseHoldRepository extends JpaRepository<HouseHold,Long> {
    @Query("SELECT r.content FROM HouseHold r WHERE lower(r.procedure) = :procedure and lower(r.require)=:require and lower(r.treatment)=:treatment")
    String content(@Param("procedure") String procedure,@Param("require") String require,@Param("treatment") String treatment);
}
