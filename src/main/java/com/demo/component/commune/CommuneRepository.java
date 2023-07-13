package com.demo.component.commune;

import com.demo.component.commune.entity.Commune;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommuneRepository extends JpaRepository<Commune, UUID> {
    List<Commune> findByNameContains(String name);

    List<Commune> findByCodeContains(String code);

//    Page<Commune> findAllP(Pageable pageable);
//    @Query(nativeQuery = true, value = "SELECT ROW_COUNT() FROM commune")
//    void countById(UUID id);

}
