package com.sewon.affiliation.application;

import static com.sewon.affiliation.exception.AffiliationErrorCode.AFFILIATION_DELETE_ERROR;
import static com.sewon.affiliation.exception.AffiliationErrorCode.AFFILIATION_NOT_FOUND;
import static com.sewon.affiliation.exception.AffiliationErrorCode.DEPARTMENT_DUPLICATION;

import com.sewon.affiliation.dto.AffiliationResult;
import com.sewon.affiliation.model.Affiliation;
import com.sewon.affiliation.repository.AffiliationRepository;
import com.sewon.assetlocation.repository.AssetLocationRepository;
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
    private final AssetLocationRepository assetLocationRepository;

    @Transactional
    public AffiliationResult registerAffiliation(Long id, String department) {
        Corporation corporation = corporationService.findByCorporationById(id);
        return AffiliationResult.from(
            affiliationRepository.save(Affiliation.of(corporation, department))
        );
    }

    @Transactional
    public void updateAffiliation(Long corporationId, Long affiliationId, String department) {
        Affiliation affiliation = findAffiliationById(affiliationId);
        for (Affiliation item : affiliationRepository.findAllByCorporationId(corporationId)) {
            if (item.getDepartment().equals(department)) {
                throw new DomainException(DEPARTMENT_DUPLICATION);
            }
        }
        affiliation.setDepartment(department);
    }

    @Transactional
    public void deleteAffiliationById(Long id) {
        if (assetLocationRepository.findAllByAffiliationId(id).isEmpty()) {
            affiliationRepository.deleteById(id);
            return;
        }
        throw new DomainException(AFFILIATION_DELETE_ERROR);
    }

    public Affiliation findAffiliationById(Long id) {
        return affiliationRepository.findById(id)
            .orElseThrow(() -> new DomainException(AFFILIATION_NOT_FOUND));
    }

    public Affiliation findAffiliationByIdAndCorporation(Long id,
        Long corporationId) {
        return affiliationRepository.findByIdAndCorporation(id, corporationId)
            .orElseThrow(() -> new DomainException(AFFILIATION_NOT_FOUND));
    }

    public List<AffiliationResult> findAllAffiliationResult() {
        return affiliationRepository.findAll()
            .stream().map(AffiliationResult::from).toList();
    }

    public List<Affiliation> findAllAffiliation() {
        return affiliationRepository.findAll();
    }


}
