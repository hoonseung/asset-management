package com.sewon.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public record ApiPagingResponse<T>(

    int code,
    int totalCount,
    T data
) {


    public static <T> ApiPagingResponse<T> ok(T data, int totalCount) {
        return new ApiPagingResponse<>(1, totalCount, data);
    }


}
