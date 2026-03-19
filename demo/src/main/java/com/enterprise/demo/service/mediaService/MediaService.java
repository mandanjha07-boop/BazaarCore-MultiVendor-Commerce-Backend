package com.enterprise.demo.service.mediaService;

import org.springframework.web.multipart.MultipartFile;

public interface MediaService {
    String getPublicUrl(String key);
    String uploadProfileImage(MultipartFile file,Long customerId);

    String uploadProductImage(Long id, MultipartFile file);
}
