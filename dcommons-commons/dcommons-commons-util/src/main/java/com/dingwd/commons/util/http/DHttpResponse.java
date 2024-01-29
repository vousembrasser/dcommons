package com.dingwd.commons.util.http;

import com.dingwd.commons.constant.exceptions.DHttpException;

public record DHttpResponse<T>(T response, DHttpException error) {

    public DHttpResponse(T response) {
        this(response, null);
    }

    public DHttpResponse(DHttpException error) {
        this(null, error);
    }
}
