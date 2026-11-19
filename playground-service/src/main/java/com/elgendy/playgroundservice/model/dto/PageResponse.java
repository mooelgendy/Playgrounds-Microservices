package com.elgendy.playgroundservice.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Envelope returned by the paginated playground search endpoint.
 */
@Data
public class PageResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final List<T> content;
    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;
    private final boolean first;
    private final boolean last;

    public static <T> PageResponse<T> of(List<T> content, int page, int size, long totalElements) {
        int safeSize = size <= 0 ? 1 : size;
        int totalPages = (int) Math.ceil((double) totalElements / safeSize);
        boolean first = page <= 0;
        boolean last = totalPages == 0 || page >= totalPages - 1;
        return new PageResponse<>(content, page, safeSize, totalElements, totalPages, first, last);
    }
}
