package com.dingwd.rom.service.query;

record likeRight(String attributeName, String value) {
    public likeRight {
        value = value + "%";
    }
}