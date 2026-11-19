package com.elgendy.playgroundservice.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * Request/response representation of a playground.
 *
 * <p>Validation lives on the API model instead of the entity so that every
 * write endpoint rejects incomplete or oversized input with a {@code 400}
 * response before the persistence layer is touched.</p>
 */
@Data
public class PlaygroundDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @NotBlank(message = "name is required")
    @Size(max = 120, message = "name must be at most 120 characters")
    private String name;

    @NotBlank(message = "address is required")
    @Size(max = 255, message = "address must be at most 255 characters")
    private String address;

    @Size(max = 2000, message = "description must be at most 2000 characters")
    private String description;

    @Pattern(regexp = "^(?:[0-9+()\\-\\s]{6,20})?$", message = "phone must contain 6 to 20 digits, spaces or +()- characters")
    private String phone;

    @NotBlank(message = "area is required")
    @Size(max = 120, message = "area must be at most 120 characters")
    private String area;

    private Date availableTime;

    @Pattern(regexp = "^(?:\\d{1,6}(?:\\.\\d{1,2})?)?$", message = "pricePerHour must be a non-negative amount with up to 2 decimals")
    private String pricePerHour;
}
