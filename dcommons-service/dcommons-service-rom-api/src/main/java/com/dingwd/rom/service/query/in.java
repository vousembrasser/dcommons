package com.dingwd.rom.service.query;

import java.util.Collection;

record in<T>(String attributeName, T... value) {
}

record inList<T>(String attributeName, Collection<T> value) {
}