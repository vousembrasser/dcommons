package com.dingwd.rom.service.query;

record lte<T extends Comparable<? super T>>(String attributeName, T value) {
    }