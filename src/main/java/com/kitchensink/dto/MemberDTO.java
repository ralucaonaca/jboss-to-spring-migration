package com.kitchensink.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {

    @NonNull
    @Size(min = 1, max = 25, message = "Invalid field : Name should have a length between 1 and 25 characters.")
    @Pattern(regexp = "[^0-9]*", message = "Invalid field : Must not contain numbers")
    private String name;

    @Email(message = "Invalid field : Must contain a valid email address")
    @NonNull
    @NotEmpty(message = "Invalid field :  Must not be empty")
    private String email;

    @Size(min = 10, max = 12, message = "Invalid field : phoneNumber should have a length between 1 and 12 characters.")
    @Digits(fraction = 0, integer = 12)
    @NonNull
    private String phoneNumber;
}
