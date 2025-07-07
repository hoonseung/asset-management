package com.sewon.assettype.application;

import static com.sewon.assettype.exception.AssetTypeErrorCode.ASSET_TYPE_DELETE_ERROR;
import static com.sewon.assettype.exception.AssetTypeErrorCode.ASSET_TYPE_DUPLICATION;
import static com.sewon.assettype.exception.AssetTypeErrorCode.ASSET_TYPE_NOT_FOUND;

import com.sewon.assettype.dto.AssetTypeParentResult;
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

    @Transactional
    public void updateAssetType(Long typeId, String type) {
        if (assetTypeRepository.findByName(type).isPresent()) {
            throw new DomainException(ASSET_TYPE_DUPLICATION);
        }
        AssetType assetType = findAssetTypeById(typeId);
        assetType.setName(type);
    }

    @Transactional
    public void deleteAssetTypeById(Long id) {
        if (assetTypeRepository.findAllById(id).isEmpty()) {
            assetTypeRepository.deleteById(id);
            return;
        }
        throw new DomainException(ASSET_TYPE_DELETE_ERROR);
    }

    public AssetType findAssetTypeById(Long id) {
        return assetTypeRepository.findById(id)
            .orElseThrow(() -> new DomainException(ASSET_TYPE_NOT_FOUND));
    }

    public AssetType findAssetTypeByName(String name) {
        return assetTypeRepository.findByName(name)
            .orElseThrow(() -> new DomainException(ASSET_TYPE_NOT_FOUND));
    }

    public AssetType findAssetByParentAndChildType(Long parentTypeId, Long childTypeId) {
        return assetTypeRepository.findByParentAndChildType(parentTypeId, childTypeId)
            .orElseThrow(() -> new DomainException(ASSET_TYPE_NOT_FOUND));
    }

    public List<AssetTypeParentResult> findAllParentAssetType() {
        return assetTypeRepository.findAllParentType()
            .stream().map(AssetTypeParentResult::from)
            .toList();
    }

    public List<AssetType> findAll() {
        return assetTypeRepository.findAll();
    }


}
