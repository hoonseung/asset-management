package com.sewon.affiliation.repository;

import com.sewon.affiliation.model.Affiliation;
import java.util.List;
import java.util.Optional;

public interface AffiliationRepository {

    Affiliation save(Affiliation affiliation);

    Optional<Affiliation> findById(Long id);

    Optional<Affiliation> findByIdAndCorporation(Long id, Long corporationId);

    List<Affiliation> findAllByCorporationId(Long corporationId);

    List<Affiliation> findAll();

    void deleteById(Long id);
}
