package com.sewon.corporation.application;

import static com.sewon.corporation.exception.CorporationErrorCode.CORPORATION_NOT_FOUND;

import com.sewon.common.exception.DomainException;
import com.sewon.corporation.dto.CorporationResult;
import com.sewon.corporation.model.Corporation;
import com.sewon.corporation.repository.CorporationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CorporationService {

    private final CorporationRepository corporationRepository;


    @Transactional
    public void registerCorporation(Corporation corporation) {
        corporationRepository.save(corporation);
    }

    @Transactional
    public void deleteCorporationById(Long id) {
        corporationRepository.deleteById(id);
    }

    public Corporation findByCorporationById(Long id) {
        return corporationRepository.findById(id)
            .orElseThrow(() -> new DomainException(CORPORATION_NOT_FOUND));
    }

    public List<CorporationResult> findAllCorporation() {
        return corporationRepository.findAll()
            .stream()
            .map(CorporationResult::from)
            .toList();
    }
}
