package com.dingwd.rom.service.query;

record between<T extends Comparable<? super T>>(String attributeName, T x, T y) {
    }