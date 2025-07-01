package com.sewon.asset.request;

public record AssetTransferRequest(
    Long assetId,
    Long fromLocationId,
    Long toLocationId
) {

}
