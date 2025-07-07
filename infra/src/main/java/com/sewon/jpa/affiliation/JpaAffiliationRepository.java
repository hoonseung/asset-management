package com.sewon.jpa.affiliation;

import com.sewon.affiliation.model.Affiliation;
import com.sewon.affiliation.repository.AffiliationRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class JpaAffiliationRepository implements AffiliationRepository {

    private final AffiliationJpaRepository affiliationJpaRepository;

    @Override
    public Affiliation save(Affiliation affiliation) {
        return affiliationJpaRepository.save(affiliation);
    }

    @Override
    public Optional<Affiliation> findById(Long id) {
        return affiliationJpaRepository.findById(id);
    }

    @Override
    public Optional<Affiliation> findByIdAndCorporation(Long id,
        Long corporationId) {
        return affiliationJpaRepository.findByIdAndCorporationId(id, corporationId);
    }

    @Override
    public List<Affiliation> findAllByCorporationId(Long corporationId) {
        return affiliationJpaRepository.findAllByCorporationId(corporationId);
    }

    @Override
    public List<Affiliation> findAll() {
        return affiliationJpaRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        affiliationJpaRepository.deleteById(id);
    }
}
