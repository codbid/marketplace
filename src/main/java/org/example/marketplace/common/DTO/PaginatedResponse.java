package org.example.marketplace.common.DTO;

import java.util.List;

public record PaginatedResponse<T>(
        List<T> items,
        int limit,
        int offset,
        long total
) {}
