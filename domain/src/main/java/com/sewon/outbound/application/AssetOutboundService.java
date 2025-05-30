package com.sewon.outbound.application;

import com.sewon.outbound.model.AssetOutbound;
import com.sewon.outbound.repository.AssetOutboundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AssetOutboundService {

    private final AssetOutboundRepository assetOutboundRepository;


    @Transactional
    public AssetOutbound registerAssetOutbound(AssetOutbound assetOutbound) {
        return assetOutboundRepository.save(assetOutbound);
    }

    @Transactional
    public void deleteAssetOutboundById(Long id) {
        assetOutboundRepository.deleteById(id);
    }
}
