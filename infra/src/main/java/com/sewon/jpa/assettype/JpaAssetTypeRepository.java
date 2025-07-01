package com.sewon.jpa.assettype;

import com.sewon.assettype.model.AssetType;
import com.sewon.assettype.repository.AssetTypeRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class JpaAssetTypeRepository implements AssetTypeRepository {

    private final AssetTypeJpaRepository assetTypeJpaRepository;

    @Override
    public void save(AssetType assetType) {
        assetTypeJpaRepository.save(assetType);
    }

    @Override
    public Optional<AssetType> findById(Long id) {
        return assetTypeJpaRepository.findById(id);
    }

    @Override
    public Optional<AssetType> findByParentAndChildType(String parentType, String childType) {
        return assetTypeJpaRepository.findByNameAndAssetTypeName(childType, parentType);
    }

    @Override
    public Optional<AssetType> findByName(String name) {
        return assetTypeJpaRepository.findByName(name);
    }

    @Override
    public List<AssetType> findAllParentType() {
        return assetTypeJpaRepository.findAllParentType();
    }

    @Override
    public List<AssetType> findAll() {
        return assetTypeJpaRepository.findAll();
    }

    @Override
    public List<AssetType> findAllById(Long id) {
        return assetTypeJpaRepository.findAllByAssetTypeId(id);
    }

    @Override
    public void deleteById(Long id) {
        assetTypeJpaRepository.deleteById(id);
    }
}
