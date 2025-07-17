package com.sewon.jpa.affiliation;

import com.sewon.affiliation.model.Affiliation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AffiliationJpaRepository extends JpaRepository<Affiliation, Long> {

    Optional<Affiliation> findByIdAndCorporationId(Long id, Long corporationId);

    List<Affiliation> findAllByCorporationId(Long corporationId);

    @Query("select case when count(a) > 0 then true else false end from Account a where a.affiliation.id = :id")
    boolean existsAccountByAffiliationId(Long id);
}
