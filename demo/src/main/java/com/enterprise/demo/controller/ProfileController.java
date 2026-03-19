package com.enterprise.demo.controller;

import com.enterprise.demo.dto.profile.ProfileResponse;
import com.enterprise.demo.dto.profile.UpdateProfileRequest;
import com.enterprise.demo.entity.Customer;
import com.enterprise.demo.entity.Profile;
import com.enterprise.demo.repository.ProfileRepository;
import com.enterprise.demo.service.customerService.CustomerService;
import com.enterprise.demo.service.profileService.ProfileService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;
    private final ProfileRepository profileRepository;
    @GetMapping
    public ResponseEntity<ProfileResponse> getMyProfile(){
        ProfileResponse profileResponse =profileService.getMyProfile();

        return ResponseEntity.ok(profileResponse);
    }

    @PutMapping("/avatar")
    public ResponseEntity<Void> uploadProfileImage(@RequestParam MultipartFile file){
        profileService.updateProfileImage(file);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping
    public ResponseEntity<ProfileResponse> updateUserProfile(@Valid @RequestBody UpdateProfileRequest request){
        return ResponseEntity.ok(profileService.updateProfile(request));
    }
}
