package com.dingwd.rom.service.util;


import java.io.Serializable;

/**
 * @author vouse
 */
@FunctionalInterface
public interface CFunction<T, R> extends Serializable {

    R apply(T t);

}