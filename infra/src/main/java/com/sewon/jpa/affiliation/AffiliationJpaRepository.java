package com.sewon.jpa.affiliation;

import com.sewon.affiliation.model.Affiliation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AffiliationJpaRepository extends JpaRepository<Affiliation, Long> {

    Optional<Affiliation> findByIdAndCorporationId(Long id, Long corporationId);

    List<Affiliation> findAllByCorporationId(Long corporationId);
}
