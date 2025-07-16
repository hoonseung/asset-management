package com.sewon.asset.request;

import java.util.List;

public record AssetDisposeRequest(
    List<String> barcodes
) {

}
