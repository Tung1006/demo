package com.demo.component.district.entity;


import com.demo.component.province.entity.Province;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "district")
public class District implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    //mã huyện
    @Column(name = "code")
    private String code;

    //tên huyện
    @Column(name = "name")
    private String name;

    //năm thành lập
    @Column(name = "founded_year")
    private Integer foundedYear;

    //diện tích
    @Column(name = "acreage")
    private Integer acreage;

    //dân số
    @Column(name = "number_of_people")
    private Integer numberOfPeople;

    @ManyToOne
    @JoinColumn(name = "id_province")
    private Province province;

}
