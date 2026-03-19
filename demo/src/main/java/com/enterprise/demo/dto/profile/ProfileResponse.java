package com.enterprise.demo.dto.profile;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ProfileResponse {

    private Long id;
    private String name;
    private String profileImageUrl;   // NOTE → URL not key
    private LocalDate dateOfBirth;
    private String gender;
    private String bio;
}
