package com.khulnasoft.commons.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Generic validator interface for type-safe validation.
 * Extracted from the AI testing framework for reuse across services.
 */
public interface Validator<T> {

    /**
     * Validates the given value and throws an exception if validation fails.
     *
     * @param value the value to validate
     * @throws ValidationException if validation fails
     */
    void validate(T value) throws ValidationException;
}

/**
 * Exception thrown when validation fails.
 */
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}

/**
 * Validates that numbers are positive.
 * Extracted from the OddEvenCamp validation logic.
 */
public class PositiveNumberValidator implements Validator<Number> {

    @Override
    public void validate(Number value) throws ValidationException {
        if (value == null) {
            throw new ValidationException("Number cannot be null");
        }

        if (value.doubleValue() <= 0) {
            throw new ValidationException("Number must be positive: " + value);
        }
    }
}

/**
 * Validates string inputs for common requirements.
 */
public class StringValidator implements Validator<String> {

    private final int minLength;
    private final int maxLength;
    private final boolean allowNull;
    private final boolean allowEmpty;

    public StringValidator(int minLength, int maxLength) {
        this(minLength, maxLength, false, false);
    }

    public StringValidator(int minLength, int maxLength, boolean allowNull, boolean allowEmpty) {
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.allowNull = allowNull;
        this.allowEmpty = allowEmpty;
    }

    @Override
    public void validate(String value) throws ValidationException {
        if (value == null) {
            if (!allowNull) {
                throw new ValidationException("String cannot be null");
            }
            return;
        }

        if (!allowEmpty && value.trim().isEmpty()) {
            throw new ValidationException("String cannot be empty");
        }

        if (value.length() < minLength) {
            throw new ValidationException("String must be at least " + minLength + " characters long");
        }

        if (value.length() > maxLength) {
            throw new ValidationException("String cannot exceed " + maxLength + " characters");
        }
    }
}

/**
 * Email validator with regex pattern matching.
 */
public class EmailValidator implements Validator<String> {

    private static final String EMAIL_PATTERN =
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
        "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    @Override
    public void validate(String value) throws ValidationException {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException("Email cannot be null or empty");
        }

        if (!value.matches(EMAIL_PATTERN)) {
            throw new ValidationException("Invalid email format: " + value);
        }
    }
}

/**
 * Composite validator that runs multiple validators in sequence.
 */
public class CompositeValidator<T> implements Validator<T> {

    private final List<Validator<T>> validators;

    public CompositeValidator(List<Validator<T>> validators) {
        this.validators = new ArrayList<>(validators);
    }

    public CompositeValidator(Validator<T>... validators) {
        this.validators = Arrays.asList(validators);
    }

    @Override
    public void validate(T value) throws ValidationException {
        for (Validator<T> validator : validators) {
            try {
                validator.validate(value);
            } catch (ValidationException e) {
                throw new ValidationException("Validation failed: " + e.getMessage(), e);
            }
        }
    }

    public CompositeValidator<T> addValidator(Validator<T> validator) {
        validators.add(validator);
        return this;
    }
}

/**
 * Factory for creating common validators.
 */
public class ValidatorFactory {

    public static PositiveNumberValidator positiveNumber() {
        return new PositiveNumberValidator();
    }

    public static StringValidator stringValidator(int minLength, int maxLength) {
        return new StringValidator(minLength, maxLength);
    }

    public static StringValidator stringValidator(int minLength, int maxLength, boolean allowNull, boolean allowEmpty) {
        return new StringValidator(minLength, maxLength, allowNull, allowEmpty);
    }

    public static EmailValidator email() {
        return new EmailValidator();
    }

    public static <T> CompositeValidator<T> composite(Validator<T>... validators) {
        return new CompositeValidator<>(validators);
    }

    public static <T> CompositeValidator<T> composite(List<Validator<T>> validators) {
        return new CompositeValidator<>(validators);
    }
}
