package com.sewon.rental.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sewon.rental.model.AssetRental;
import java.time.LocalDate;
import java.util.List;

public record RentalListResponse(
    @JsonProperty("list")
    List<RentalOneResponse> rentalOneResponses

) {

    public static RentalListResponse from(List<AssetRental> rentals) {
        return new RentalListResponse(
            rentals.stream()
                .map(RentalOneResponse::from)
                .toList()
        );
    }


    record RentalOneResponse(
        Long id,
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
        LocalDate toDate
    ) {

        public static RentalOneResponse from(AssetRental rental) {
            return new RentalOneResponse(
                rental.getId(),
                rental.getBarcodeValue(),
                rental.getCorporation(),
                rental.getDepartment(),
                rental.getAssetLocation(),
                rental.getRentLocation(),
                rental.getParentCategory(),
                rental.getChildCategory(),
                rental.getRenter(),
                rental.getRegister(),
                rental.getRentalStatus().getValue(),
                rental.getFromDate(),
                rental.getToDate()
            );
        }
    }

}
