package com.demo.component.commune.entity;

import com.demo.component.district.entity.District;
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
@Table(name = "commune")
public class Commune implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    //mã xã
    @Column(name = "code")
    private String code;

    //tên xã
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

    //Huyện
    @ManyToOne
    @JoinColumn(name = "id_district")
    private District district;

    //tỉnh
    @ManyToOne
    @JoinColumn(name = "id_province")
    private Province province;
}
