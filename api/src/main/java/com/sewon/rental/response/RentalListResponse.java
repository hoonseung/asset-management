package com.sewon.rental.response;

import static com.sewon.rental.constant.RentalStatus.EXPIRE;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sewon.rental.dto.AssetRentalResult;
import java.time.LocalDate;
import java.util.List;

public record RentalListResponse(
    @JsonProperty("list")
    List<RentalOneResponse> rentalOneResponses
) {

    public static RentalListResponse from(List<AssetRentalResult> rentals) {
        return new RentalListResponse(
            rentals.stream()
                .map(rental -> RentalOneResponse.from(rental, false))
                .toList()
        );
    }

    public static RentalListResponse fromIncludeExpire(List<AssetRentalResult> rentals) {
        return new RentalListResponse(
            rentals.stream()
                .map(rental -> RentalOneResponse.from(rental,
                    rental.status().equals(EXPIRE.getValue())))
                .toList()
        );
    }


    record RentalOneResponse(
        Long id,
        Long assetId,
        String barcode,
        String corporation,
        String department,
        String location,
        String rentLocation,
        String parentType,
        String childType,
        String renter,
        String register,
        Integer status,
        LocalDate fromDate,
        LocalDate toDate,
        boolean isExpire
    ) {

        public static RentalOneResponse from(AssetRentalResult rental, boolean isExpire) {
            return new RentalOneResponse(
                rental.id(),
                rental.assetId(),
                rental.barcode(),
                rental.corporation(),
                rental.department(),
                rental.location(),
                rental.rentLocation(),
                rental.parentType(),
                rental.childType(),
                rental.renter(),
                rental.register(),
                rental.status(),
                rental.fromDate(),
                rental.toDate(),
                isExpire
            );
        }
    }

}
