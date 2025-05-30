package com.sewon.jpa.inbound;

import com.sewon.inbound.model.AssetInbound;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetInboundJpaRepository extends JpaRepository<AssetInbound, Long> {

}
