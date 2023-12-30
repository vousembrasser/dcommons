package com.dingwd.commons;

@FunctionalInterface
public interface CheckFunction {
    Boolean execute() throws Throwable;
}