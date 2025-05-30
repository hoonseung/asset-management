package com.sewon.jpa.corporation;

import com.sewon.corporation.model.Corporation;
import com.sewon.corporation.repository.CorporationRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class JpaCorporationRepository implements CorporationRepository {

    private final CorporationJpaRepository corporationJpaRepository;

    @Override
    public void save(Corporation corporation) {
        corporationJpaRepository.save(corporation);
    }

    @Override
    public Optional<Corporation> findById(Long id) {
        return corporationJpaRepository.findById(id);
    }

    @Override
    public List<Corporation> findAll() {
        return corporationJpaRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        corporationJpaRepository.deleteById(id);
    }
}
