package com.example.demo.commune;

import com.example.demo.commune.entity.Commune;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

public interface CommuneRepository extends JpaRepository<Commune, UUID> {
    List<Commune> findByNameContains(String name);

    List<Commune> findByCodeContains(String code);

//    Page<Commune> findAllP(Pageable pageable);
//    @Query(nativeQuery = true, value = "SELECT ROW_COUNT() FROM commune")
//    void countById(UUID id);

}
