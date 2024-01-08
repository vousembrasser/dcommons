package com.dingwd.commons.validation;

@FunctionalInterface
public interface CheckFunction {
    Boolean execute() throws Throwable;
}