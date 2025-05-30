package com.sewon.jpa.outbound;

import com.sewon.outbound.model.AssetOutbound;
import com.sewon.outbound.repository.AssetOutboundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class JpaAssetOutboundRepository implements AssetOutboundRepository {

    private final AssetOutboundJpaRepository assetOutboundJpaRepository;

    @Override
    public AssetOutbound save(AssetOutbound assetOutbound) {
        return assetOutboundJpaRepository.save(assetOutbound);
    }

    @Override
    public void deleteById(Long id) {
        assetOutboundJpaRepository.deleteById(id);
    }
}
