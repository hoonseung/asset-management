package com.sewon.jpa.barcode;

import com.sewon.asset.model.Asset;
import com.sewon.barcode.model.Barcode;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BarcodeJpaRepository extends JpaRepository<Barcode, Long> {


    Optional<Barcode> findBarcodesByValue(String value);

    @Query("select b.asset from Barcode b where b.value = :value")
    Optional<Asset> findAssetByBarcode(@Param("value") String value);
}
