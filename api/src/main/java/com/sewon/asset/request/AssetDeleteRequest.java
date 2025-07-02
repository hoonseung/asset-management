package com.sewon.asset.request;

import java.util.List;

public record AssetDeleteRequest(
    List<Long> ids
) {

}
