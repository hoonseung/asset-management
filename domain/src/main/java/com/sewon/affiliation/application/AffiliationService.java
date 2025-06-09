package com.sewon.affiliation.application;

import static com.sewon.affiliation.exception.AffiliationErrorCode.AFFILIATION_NOT_FOUND;

import com.sewon.affiliation.model.Affiliation;
import com.sewon.affiliation.repository.AffiliationRepository;
import com.sewon.common.exception.DomainException;
import com.sewon.corporation.application.CorporationService;
import com.sewon.corporation.model.Corporation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AffiliationService {

    private final AffiliationRepository affiliationRepository;
    private final CorporationService corporationService;

    @Transactional
    public void registerAffiliation(Long id, String department) {
        Corporation corporation = corporationService.findByCorporationById(id);
        affiliationRepository.save(Affiliation.of(corporation, department));
    }

    @Transactional
    public void deleteAffiliationById(Long id) {
        affiliationRepository.deleteById(id);
    }

    public Affiliation findAffiliationById(Long id) {
        return affiliationRepository.findById(id)
            .orElseThrow(() -> new DomainException(AFFILIATION_NOT_FOUND));
    }

    public Affiliation findAffiliationByDepartmentAndCorporation(String department,
        String corporation) {
        return affiliationRepository.findByDepartmentAndCorporation(department, corporation)
            .orElseThrow(() -> new DomainException(AFFILIATION_NOT_FOUND));
    }

    public List<Affiliation> findAllAffiliation() {
        return affiliationRepository.findAll();
    }


}
