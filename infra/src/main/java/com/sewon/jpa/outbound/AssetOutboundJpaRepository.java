package com.sewon.jpa.outbound;

import com.sewon.outbound.model.AssetOutbound;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetOutboundJpaRepository extends JpaRepository<AssetOutbound, Long> {

}
