package com.sewon.jpa.assetrental;

import com.sewon.rental.constant.RentalStatus;
import com.sewon.rental.model.AssetRental;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AssetRentalJpaRepository extends JpaRepository<AssetRental, Long> {

    @Query("select ar from AssetRental ar where ar.account.username = :username")
    List<AssetRental> findAllByAccountName(@Param("username") String username);

    @Query("select ar from AssetRental ar where ar.account.username = :username and ar.rentalStatus = :rentalStatus")
    List<AssetRental> findAllByAccountNameAndRentalStatus(@Param("username") String username,
        @Param("rentalStatus")
        RentalStatus rentalStatus);

    @Query(
        "select ar from AssetRental ar join fetch ar.asset a join fetch a.assetLocation al where ar.rentalStatus = :rentalStatus "
            + "and al.affiliation.id = :id")
    List<AssetRental> findAllByRentalStatusAndAssetDepartment(RentalStatus rentalStatus,
        Long id);

    @Query(
        "select ar from AssetRental ar join fetch ar.asset a join fetch a.assetLocation al where ar.rentalStatus = :rentalStatus "
            + "and ar.account.affiliation.id = :id")
    List<AssetRental> findAllByRentalStatusAndMyDepartment(RentalStatus rentalStatus,
        Long id);

    List<AssetRental> findAllByIdIn(List<Long> ids);

    @Query(
        "select count(ar.asset) from AssetRental ar where ar.assetLocation.affiliation.id = :affiliationId "
            + "and ar.rentalStatus = :status")
    Long getRentedByAffiliationId(Long affiliationId, RentalStatus status);

    @Query(
        "select count(a) from Asset a " +
            "where a.assetLocation.affiliation.id = :affiliationId " +
            "and a.id not in (" +
            "  select ar.asset.id from AssetRental ar where ar.rentalStatus in :statuses)")
    Long getRentableByAffiliationId(Long affiliationId, List<RentalStatus> statuses);

}
