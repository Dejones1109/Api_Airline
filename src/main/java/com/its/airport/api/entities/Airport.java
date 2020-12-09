/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.its.airport.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

/**
 *
 * @author quangdt
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "tbl_airport")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Airport {

    @Id
    @Column(name = "airport_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "airport_name")

    String airportName;
    @Column(name = "airport_code")
    String airportCode;
    @Column(name = "code_province")
    String codeProvince;
    @Column(name = "code_country")

    String codeCountry;
    @Column(name = "province_name")
    String provinceName;
    @Column(name = "country_name")
    String countryName;
}
