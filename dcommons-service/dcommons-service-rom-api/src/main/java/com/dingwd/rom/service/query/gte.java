package com.dingwd.rom.service.query;

record gte<T extends Comparable<? super T>>(String attributeName, T value) {
    }