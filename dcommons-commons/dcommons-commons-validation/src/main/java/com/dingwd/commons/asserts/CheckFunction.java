package com.dingwd.commons.asserts;

@FunctionalInterface
public interface CheckFunction {
    Boolean execute() throws Throwable;
}