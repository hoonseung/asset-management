package com.sewon.inbound.repository;

import com.sewon.inbound.model.AssetInbound;

public interface AssetInboundRepository {

    AssetInbound save(AssetInbound assetInbound);

    void deleteById(Long id);
}
