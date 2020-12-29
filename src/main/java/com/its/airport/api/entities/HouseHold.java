package com.its.airport.api.entities;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "hcc_ho_khau")
@AllArgsConstructor
@NoArgsConstructor
public class HouseHold {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "thu_tuc")
    String procedure;
    @Column(name = "yeu_cau")
    String require;
    @Column(name = "cap_xu_ly")
    String treatment;
    @Column(name = "noi_dung")
    String content;
}
