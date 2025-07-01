package com.sewon.assetlocation.application;

import static com.sewon.assetlocation.exception.LocationErrorCode.LOCATION_DUPLICATION;
import static com.sewon.assetlocation.exception.LocationErrorCode.LOCATION_NOT_FOUND;

import com.sewon.affiliation.application.AffiliationService;
import com.sewon.affiliation.model.Affiliation;
import com.sewon.assetlocation.dto.AssetLocationResult;
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
    public void updateAssetLocation(Long affiliationId, Long locationId, String location) {
        AssetLocation assetLocation = findAssetLocationById(locationId);
        for (AssetLocation item : assetLocationRepository.findAllByAffiliationId(affiliationId)) {
            if (item.getLocation().equals(location)) {
                throw new DomainException(LOCATION_DUPLICATION);
            }
        }
        assetLocation.setLocation(location);
    }

    @Transactional
    public void deleteAssetLocationById(Long id) {
        assetLocationRepository.deleteById(id);
    }

    public List<AssetLocationResult> findAllByAffiliationId(Long id) {
        return assetLocationRepository.findAllByAffiliationId(id)
            .stream()
            .map(AssetLocationResult::from)
            .toList();
    }

    public AssetLocation findAssetLocationById(Long id) {
        return assetLocationRepository.findById(id)
            .orElseThrow(() -> new DomainException(LOCATION_NOT_FOUND));
    }

    public List<AssetLocation> findAllAssetLocation() {
        return assetLocationRepository.findAll();
    }


}
