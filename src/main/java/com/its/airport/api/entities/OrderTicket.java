package com.its.airport.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "tbl_order")
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderTicket {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "cus_name")
    String customerName;
    @Column(name = "phone")
    String phone;
    @Column(name = "phone_order")
    String phoneOrder;
 // phoneType: 1(zalo), 0 (điện thoại)
    @Column(name = "phone_type")
    int phoneType;
    @Column(name = "start_city")
   String startCity;
    @Column(name = "end_city")
    String endCity;
    @Column(name = "start_date")
    String startDate;
    @Column(name = "end_date")
    String endDate;
    @Column(name = "adult")
    int adult;
    @Column(name = "child")
    int child;
    @Column(name = "infant")
    int infant;
    @Column(name = "suitcase")
    int suitcase;
}