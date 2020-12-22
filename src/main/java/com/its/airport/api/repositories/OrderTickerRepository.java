package com.its.airport.api.repositories;

import com.its.airport.api.entities.OrderTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface OrderTickerRepository extends JpaRepository<OrderTicket,String> {




}
