package com.demo.component.province;

import com.demo.component.province.entity.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProvinceServiceImpl {
    @Autowired
    private ProvinceRepository repository;

    public List<Province> findAll() {

        return repository.findAll();
    }

    public Province save(Province province) {
        return repository.save(province);
    }

    public Province findById(UUID id) {
        return repository.findById(id).get();
    }

    public void delete(UUID id) {
        repository.delete(findById(id));
    }

    public List<Province> findByName(String name) {
        return repository.findByNameContains(name);
    }

    public List<Province> findByCode(String code) {
        return repository.findByCodeContains(code);
    }
}
