package com.sewon.jpa.corporation;

import com.sewon.corporation.model.Corporation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorporationJpaRepository extends JpaRepository<Corporation, Long> {

}
