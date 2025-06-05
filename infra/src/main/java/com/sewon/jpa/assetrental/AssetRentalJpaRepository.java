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
        RentalStatus rentalStatus);

    List<AssetRental> findAllByRentalStatus(RentalStatus rentalStatus);
}
