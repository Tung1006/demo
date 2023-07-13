package com.example.demo.province;

import com.example.demo.commune.entity.Commune;
import com.example.demo.province.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProvinceRepository extends JpaRepository<Province, UUID> {
    List<Province> findByNameContains(String name);

    List<Province> findByCodeContains(String code);
}
