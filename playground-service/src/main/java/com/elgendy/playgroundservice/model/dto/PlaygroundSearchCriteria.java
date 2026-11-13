package com.elgendy.playgroundservice.model.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * Query parameters for {@code GET /api/playground/search}.
 *
 * <p>Both filters are optional and case-insensitive, and use a "contains"
 * match so callers can search for partial area or name values.</p>
 */
@Data
public class PlaygroundSearchCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int MAX_PAGE_SIZE = 100;

    private String area;
    private String name;

    @Min(value = 0, message = "page must be zero or greater")
    private int page = 0;

    @Min(value = 1, message = "size must be at least 1")
    @Max(value = MAX_PAGE_SIZE, message = "size must be at most " + MAX_PAGE_SIZE)
    private int size = 20;
}
