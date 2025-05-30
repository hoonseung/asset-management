package com.sewon.affiliation.repository;

import com.sewon.affiliation.model.Affiliation;
import java.util.List;
import java.util.Optional;

public interface AffiliationRepository {

    void save(Affiliation affiliation);

    Optional<Affiliation> findById(Long id);

    Optional<Affiliation> findByDepartmentAndCorporation(String department, String corporation);

    List<Affiliation> findAll();

    void deleteById(Long id);
}
