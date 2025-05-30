package com.sewon.corporation.repository;

import com.sewon.corporation.model.Corporation;
import java.util.List;
import java.util.Optional;

public interface CorporationRepository {

    void save(Corporation corporation);

    Optional<Corporation> findById(Long id);

    List<Corporation> findAll();

    void deleteById(Long id);
}
