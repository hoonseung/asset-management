package com.sewon.inbound.application;

import com.sewon.inbound.model.AssetInbound;
import com.sewon.inbound.repository.AssetInboundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class AssetInboundService {

    private final AssetInboundRepository assetInboundRepository;


    @Transactional
    public AssetInbound registerAssetInbound(AssetInbound assetInbound) {
        return assetInboundRepository.save(assetInbound);
    }

    @Transactional
    public void deleteAssetInboundById(Long id) {
        assetInboundRepository.deleteById(id);
    }
}
