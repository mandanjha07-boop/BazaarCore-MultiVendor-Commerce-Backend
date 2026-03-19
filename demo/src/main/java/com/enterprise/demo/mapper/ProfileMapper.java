package com.enterprise.demo.mapper;

import com.enterprise.demo.dto.profile.ProfileResponse;
import com.enterprise.demo.dto.profile.UpdateProfileRequest;
import com.enterprise.demo.entity.Customer;
import com.enterprise.demo.entity.Profile;

public class ProfileMapper {
    public static Profile toProfile(UpdateProfileRequest request){
        return Profile.builder()
                .gender(request.getGender())
                .bio(request.getBio())
                .name(request.getName())
                .dateOfBirth(request.getDateOfBirth())
                .build();
    }

    public static ProfileResponse toProfileResponse(Profile profile, String url){
        return ProfileResponse.builder()
                .bio(profile.getBio())
                .id(profile.getId())
                .dateOfBirth(profile.getDateOfBirth())
                .gender(profile.getGender())
                .name(profile.getName())
                .profileImageUrl(url)
                .build();
    }

    public static Profile updateProfileFromRequest(UpdateProfileRequest request, Profile profile){
        if(request.getName()!=null){
            profile.setName(request.getName());
        }
        if(request.getDateOfBirth()!=null){
            profile.setDateOfBirth(request.getDateOfBirth());
        }
        if(request.getGender()!=null){
            profile.setGender(request.getGender());
        }
        if(request.getBio()!=null){
            profile.setBio(request.getBio());
        }
        return profile;
    }
}
