package com.dingwd.commons.reponse;

import com.dingwd.commons.constant.messages.DErrorMessage;
import com.dingwd.commons.validation.param.DValidatorString;

public record ApiResponse(String body, String status, Boolean success) {

    public ApiResponse(String body, String status, Boolean success) {
        this.body = DValidatorString.hasText(body) ? body.trim() : DErrorMessage.SERVICE.INTERNAL_SERVER_ERROR.getMessage().trim();
        this.status = DValidatorString.hasText(status) ? status.trim() : DErrorMessage.SERVICE.INTERNAL_SERVER_ERROR.getCode().trim();
        this.success = success;
    }

    @Override
    public String toString() {
        return "{\"body\": \"" + body + "\", \"status\": \"" + status + "\"}";
    }
}
