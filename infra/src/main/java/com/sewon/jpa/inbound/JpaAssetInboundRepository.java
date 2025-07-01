package com.sewon.jpa.inbound;

import com.sewon.inbound.model.AssetInbound;
import com.sewon.inbound.repository.AssetInboundRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class JpaAssetInboundRepository implements AssetInboundRepository {

    private final AssetInboundJpaRepository assetInboundJpaRepository;

    @Override
    public AssetInbound save(AssetInbound assetInbound) {
        return assetInboundJpaRepository.save(assetInbound);
    }

    @Override
    public void saveAll(List<AssetInbound> assetInbounds) {
        assetInboundJpaRepository.saveAll(assetInbounds);
    }

    @Override
    public void deleteById(Long id) {
        assetInboundJpaRepository.deleteById(id);
    }
}
