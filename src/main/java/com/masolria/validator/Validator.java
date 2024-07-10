package com.masolria.validator;

public interface Validator<T> {
    boolean isValid(T object);
}
