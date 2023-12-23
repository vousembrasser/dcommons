package com.dingwd.rom.service.query;

import java.util.List;

record in<T>(String attributeName, T... value) {
    @SafeVarargs
    public in {
    }
}