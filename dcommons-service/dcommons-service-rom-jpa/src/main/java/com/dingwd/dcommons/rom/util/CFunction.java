package com.dingwd.dcommons.rom.util;


import java.io.Serializable;

/**
 * @author vouse
 */
@FunctionalInterface
public interface CFunction<T, R> extends Serializable {

    R apply(T t);

}