package com.sewon.jpa.asset;

import com.sewon.asset.dto.result.AllAssetResult;
import com.sewon.asset.model.Asset;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AssetJpaRepository extends JpaRepository<Asset, Long> {


    @Query("select count(a) from Asset a where a.assetLocation.affiliation.id = :affiliationId")
    Long getCountByAffiliationId(@Param("affiliationId") Long affiliationId);

    void deleteAllByIdIn(List<Long> ids);


    @Query(
        "select new com.sewon.asset.dto.result.AllAssetResult"
            + "(a.id, b.value, c.name, af.department, al.location, a.assetDivision, at,"
            + "at.id, at.assetType.name, at.name, a.assetStatus, a.manufacturer, a.model, a.acquisitionDate, a.acquisitionPrice,"
            + " ac.name, ea.cpu, ea.ram, ea.storage, ea.gpu, a.createdDate)"
            + "from Asset a left join ElectronicAsset ea on a.id = ea.id "
            + "join AssetLocation al on a.assetLocation.id = al.id "
            + "join Affiliation af on al.affiliation.id = af.id "
            + "join Corporation c on af.corporation.id = c.id "
            + "join AssetType at on a.assetType.id = at.id "
            + "join Account ac on a.account.id = ac.id "
            + "join Barcode b on a.barcode.id = b.id")
    List<AllAssetResult> findAllAsset(Pageable pageable);


    @Query(
        "select new com.sewon.asset.dto.result.AllAssetResult"
            + "(a.id, b.value, c.name, af.department, al.location, a.assetDivision, at,"
            + "at.id, at.assetType.name, at.name, a.assetStatus, a.manufacturer, a.model, a.acquisitionDate, a.acquisitionPrice,"
            + " ac.name, ea.cpu, ea.ram, ea.storage, ea.gpu, ac.createdDate)"
            + "from Asset a left join ElectronicAsset ea on a.id = ea.id "
            + "join AssetLocation al on a.assetLocation.id = al.id "
            + "join Affiliation af on al.affiliation.id = af.id "
            + "join Corporation c on af.corporation.id = c.id "
            + "join AssetType at on a.assetType.id = at.id "
            + "join Account ac on a.account.id = ac.id "
            + "join Barcode b on a.barcode.id = b.id where b.value = :value")
    Optional<AllAssetResult> findAssetByBarcode(@Param("value") String value);
}
