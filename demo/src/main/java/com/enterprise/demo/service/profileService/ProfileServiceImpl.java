package com.enterprise.demo.service.profileService;

import com.enterprise.demo.dto.profile.ProfileResponse;
import com.enterprise.demo.dto.profile.UpdateProfileRequest;
import com.enterprise.demo.entity.Customer;
import com.enterprise.demo.entity.Profile;
import com.enterprise.demo.mapper.ProfileMapper;
import com.enterprise.demo.repository.ProfileRepository;
import com.enterprise.demo.service.customerService.CustomerService;
import com.enterprise.demo.service.mediaService.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
private final CustomerService customerService;
private final MediaService mediaService;
private final ProfileRepository profileRepository;
    @Override
    @Transactional
    public ProfileResponse getMyProfile() {
        Customer customer = customerService.getLoggedInUser();
        Profile profile = profileRepository.findByCustomerId(customer.getId())
                .orElseGet(()->createEmptyProfile(customer));

        String imgUrl= mediaService.getPublicUrl(profile.getProfileImageKey());
        return ProfileMapper .toProfileResponse(profile,imgUrl);
    }

    private Profile createEmptyProfile(Customer customer) {
        Profile profile=Profile.builder()
                .customer(customer)
                .createdAt(LocalDateTime.now())
                .build();
        return profileRepository.save(profile);
    }

    @Transactional
    public void updateProfileImage(MultipartFile file){
        if(file==null || file.isEmpty()){
            throw new RuntimeException("File is empty");
        }
        long max_size = 2*1024*1024;
        if(file.getSize()>max_size){
            throw new RuntimeException("File too large");
        }
        String type=file.getContentType();
        if(type == null || !(type.equals("image/jpeg")
                || type.equals("image/png")
                || type.equals("image/webp"))){
            throw new RuntimeException("Invalid file type");
        }
        Customer customer = customerService.getLoggedInUser();
        Profile profile = profileRepository.findByCustomerId(customer.getId())
                .orElseGet(()->createEmptyProfile(customer));
        String key = mediaService.uploadProfileImage(file, customer.getId());
        profile.setProfileImageKey(key);
        profileRepository.save(profile);
    }

    @Transactional
    public ProfileResponse updateProfile(UpdateProfileRequest request){
        Customer customer = customerService.getLoggedInUser();
        Profile existingprofile = profileRepository.findByCustomerId(customer.getId())
                .orElseGet(()->createEmptyProfile(customer));
        ProfileMapper.updateProfileFromRequest(request,existingprofile);
        String imgUrl= mediaService.getPublicUrl(existingprofile.getProfileImageKey());
        return ProfileMapper.toProfileResponse(existingprofile,imgUrl);
    }
}
