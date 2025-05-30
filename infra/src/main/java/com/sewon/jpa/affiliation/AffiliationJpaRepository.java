package com.sewon.jpa.affiliation;

import com.sewon.affiliation.model.Affiliation;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AffiliationJpaRepository extends JpaRepository<Affiliation, Long> {

    Optional<Affiliation> findByDepartmentAndCorporationName(String department, String corporation);
}
