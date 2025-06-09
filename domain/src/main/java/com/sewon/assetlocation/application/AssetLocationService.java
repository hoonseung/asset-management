package com.sewon.assetlocation.application;

import static com.sewon.assetlocation.exception.LocationErrorCode.LOCATION_NOT_FOUND;

import com.sewon.affiliation.application.AffiliationService;
import com.sewon.affiliation.model.Affiliation;
import com.sewon.assetlocation.model.AssetLocation;
import com.sewon.assetlocation.repository.AssetLocationRepository;
import com.sewon.common.exception.DomainException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AssetLocationService {

    private final AssetLocationRepository assetLocationRepository;
    private final AffiliationService affiliationService;


    @Transactional
    public void registerAssetLocation(String location, Long id) {
        Affiliation affiliation = affiliationService.findAffiliationById(id);
        assetLocationRepository.save(AssetLocation.of(location, affiliation));
    }

    @Transactional
    public void deleteAssetLocationById(Long id) {
        assetLocationRepository.deleteById(id);
    }

    public List<AssetLocation> findAllByAffiliationId(Long id) {
        return assetLocationRepository.findAllByAffiliationId(id);
    }

    public AssetLocation findAssetLocationById(Long id) {
        return assetLocationRepository.findById(id)
            .orElseThrow(() -> new DomainException(LOCATION_NOT_FOUND));
    }

    public AssetLocation findAssetLocationByLocation(String location) {
        return assetLocationRepository.findByLocation(location)
            .orElseThrow(() -> new DomainException(LOCATION_NOT_FOUND));
    }

    public List<AssetLocation> findAllAssetLocation() {
        return assetLocationRepository.findAll();
    }


}
