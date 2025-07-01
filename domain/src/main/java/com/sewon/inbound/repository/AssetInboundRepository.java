package com.sewon.inbound.repository;

import com.sewon.inbound.model.AssetInbound;
import java.util.List;

public interface AssetInboundRepository {

    AssetInbound save(AssetInbound assetInbound);

    void saveAll(List<AssetInbound> assetInbounds);

    void deleteById(Long id);
}
