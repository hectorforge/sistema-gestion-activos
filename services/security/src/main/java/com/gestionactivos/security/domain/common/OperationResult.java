package com.gestionactivos.security.domain.common;

import java.util.Set;

public sealed interface OperationResult<T> permits OperationResult.Success,
        OperationResult.FailureValidation, OperationResult.FailureSingle {

    boolean isSuccess();
    T data();
    String errorCode();
    String errorMessage();
    Set<String> validationErrors();

    /**
     * Creates a successful OperationResult with the given data.
     * @param data
     * @return
     * @param <T>
     */
    static<T> OperationResult<T> success(T data) {
        return new Success<>(true,data,null,null,null);
    }

    /**
     * Creates a failed OperationResult due to validation errors.
     * @param errorCode
     * @param errorMessage
     * @param validationErrors
     * @return
     * @param <T>
     */
    static<T> OperationResult<T> failureValidations(String errorCode, String errorMessage, Set<String> validationErrors) {
        return new FailureValidation<>(false,null,errorCode,errorMessage,validationErrors);
    }

    /**
     * Creates a failed OperationResult due to a single error.
     * @param errorCode
     * @param errorMessage
     * @return
     * @param <T>
     */
    static<T> OperationResult<T> failureSingle(String errorCode, String errorMessage) {
        return new FailureSingle<>(false,null,errorCode,errorMessage,null);
    }

    record Success<T>(
            boolean isSuccess,
            T data,
            String errorCode,
            String errorMessage,
            Set<String> validationErrors
    ) implements OperationResult<T> {}

    record FailureValidation<T>(
            boolean isSuccess,
            T data,
            String errorCode,
            String errorMessage,
            Set<String> validationErrors
    ) implements OperationResult<T> {}

    record FailureSingle<T>(
            boolean isSuccess,
            T data,
            String errorCode,
            String errorMessage,
            Set<String> validationErrors
    ) implements OperationResult<T> {}
}