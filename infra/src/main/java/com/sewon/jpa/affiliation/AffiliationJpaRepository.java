package com.sewon.jpa.affiliation;

import com.sewon.affiliation.model.Affiliation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AffiliationJpaRepository extends JpaRepository<Affiliation, Long> {

    @Query("select a from Affiliation a where a.department = :department and a.corporation.name = :corporation")
    Optional<Affiliation> findByDepartmentAndCorporationName(String department, String corporation);

    List<Affiliation> findAllByCorporationId(Long corporationId);
}
