package com.enterprise.demo.dto.profile;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UpdateProfileImage {

    private MultipartFile file;
}
