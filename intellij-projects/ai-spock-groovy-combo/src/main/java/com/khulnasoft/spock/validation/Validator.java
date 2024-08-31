package com.khulnasoft.spock.validation;

public interface Validator<T> {

    void validate(T value);

}
