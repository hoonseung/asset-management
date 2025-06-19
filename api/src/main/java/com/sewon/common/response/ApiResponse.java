package com.sewon.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public record ApiResponse<T>(

    int code,
    T data
) {


    public static <T> ApiResponse<T> ok() {
        return new ApiResponse<>(1, null);
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(1, data);
    }

    public static <T> ApiResponse<T> fail() {
        return new ApiResponse<>(0, null);
    }


}
