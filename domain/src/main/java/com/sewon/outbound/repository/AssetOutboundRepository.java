package com.sewon.outbound.repository;

import com.sewon.outbound.model.AssetOutbound;

public interface AssetOutboundRepository {

    AssetOutbound save(AssetOutbound assetOutbound);

    void deleteById(Long id);
}
