package com.enterprise.demo.dto.profile;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;

@Getter
@Setter
public class UpdateProfileRequest {

    @Size(max = 50)
    private String name;
    @Past
    private LocalDate dateOfBirth;
    @Pattern(regexp = "MALE|FEMALE|OTHER")
    private String gender;
    @Size(max = 500)
    private String bio;
}
