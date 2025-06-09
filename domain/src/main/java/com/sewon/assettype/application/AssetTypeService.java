package com.sewon.assettype.application;

import static com.sewon.assettype.exception.AssetTypeErrorCode.ASSET_TYPE_NOT_FOUND;

import com.sewon.assettype.model.AssetType;
import com.sewon.assettype.repository.AssetTypeRepository;
import com.sewon.common.exception.DomainException;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AssetTypeService {

    private final AssetTypeRepository assetTypeRepository;

    @Transactional
    public void registerAssetType(AssetType assetType, Long id) {
        if (Objects.isNull(id)) {
            assetTypeRepository.save(assetType);
            return;
        }
        assetType.setAssetType(findAssetTypeById(id));
        assetTypeRepository.save(assetType);
    }

    public void deleteAssetTypeById(Long id) {
        assetTypeRepository.deleteById(id);
    }

    public AssetType findAssetTypeById(Long id) {
        return assetTypeRepository.findById(id)
            .orElseThrow(() -> new DomainException(ASSET_TYPE_NOT_FOUND));
    }

    public AssetType findAssetByParentAndChildType(String parentType, String childType) {
        return assetTypeRepository.findByParentAndChildType(parentType, childType)
            .orElseThrow(() -> new DomainException(ASSET_TYPE_NOT_FOUND));
    }

    public List<AssetType> findAllParentAssetType() {
        return assetTypeRepository.findAllParentType();
    }

    public List<AssetType> findAll() {
        return assetTypeRepository.findAll();
    }
}
