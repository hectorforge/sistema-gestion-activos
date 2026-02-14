package com.gestionactivos.reports.domain.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationResult<T> {
    private boolean isSuccess;
    private T data;
    private String errorCode;
    private String errorMessage;
    private Set<String> validationErrors;
}
