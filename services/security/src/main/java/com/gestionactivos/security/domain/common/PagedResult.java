package com.gestionactivos.security.domain.common;

import java.util.List;

public record PagedResult<T> (
        List<T> data,

        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages,

        boolean first,
        boolean last,
        boolean hasNext,
        boolean hasPrevious,
        boolean isEmpty
){ }
