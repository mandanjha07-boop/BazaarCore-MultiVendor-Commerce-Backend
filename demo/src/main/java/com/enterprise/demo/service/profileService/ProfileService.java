package com.enterprise.demo.service.profileService;

import com.enterprise.demo.dto.profile.ProfileResponse;
import com.enterprise.demo.dto.profile.UpdateProfileRequest;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {
    ProfileResponse getMyProfile();
    void updateProfileImage(MultipartFile file);
    ProfileResponse updateProfile(UpdateProfileRequest request);
}
