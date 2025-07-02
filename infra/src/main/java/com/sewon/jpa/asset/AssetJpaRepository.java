package com.sewon.jpa.asset;

import com.sewon.asset.model.Asset;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AssetJpaRepository extends JpaRepository<Asset, Long> {

    @Query("select a from Asset a where a.assetLocation.id = :locationId")
    List<Asset> findAllByAssetLocationId(@Param("locationId") Long locationId);

    @Query("select a from Asset a where a.assetLocation.location = :location and a.assetType.name = :type")
    List<Asset> findAllByLocationAndChildType
        (@Param("location") String location, @Param("type") String type, Pageable pageable);

    @Query("select a from Asset a where a.assetLocation.location = :location and a.assetType.assetType.name = :type")
    List<Asset> findAllByLocationAndParentType
        (@Param("location") String location, @Param("type") String type, Pageable pageable);

    @Query(
        "select a from Asset a where a.assetLocation.location = :location and a.assetType.name = :type "
            + "and a.createdDate between :createdDateAfter and :createdDateBefore")
    List<Asset> findAllByLocationAndChildTypeBetween
        (@Param("location") String location, @Param("type") String type,
            @Param("createdDateAfter") LocalDateTime createdDateAfter,
            @Param("createdDateBefore") LocalDateTime createdDateBefore, Pageable pageable);

    @Query(
        "select a from Asset a where a.assetLocation.location = :location and a.assetType.assetType.name = :type "
            + "and a.createdDate between :createdDateAfter and :createdDateBefore")
    List<Asset> findAllByLocationAndParentTypeBetween
        (@Param("location") String location, @Param("type") String type,
            @Param("createdDateAfter") LocalDateTime createdDateAfter,
            @Param("createdDateBefore") LocalDateTime createdDateBefore, Pageable pageable);

    List<Asset> findAllByCreatedDateBetween(LocalDateTime createdDateAfter,
        LocalDateTime createdDateBefore, Pageable pageable);

    @Query("select count(a) from Asset a where a.assetLocation.affiliation.id = :affiliationId")
    Long getCountByAffiliationId(@Param("affiliationId") Long affiliationId);

    void deleteAllByIdIn(List<Long> ids);
}
