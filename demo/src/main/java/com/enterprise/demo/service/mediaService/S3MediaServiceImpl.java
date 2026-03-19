package com.enterprise.demo.service.mediaService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class S3MediaServiceImpl implements MediaService {
    private final S3Client s3Client;
    String bucketName = "enterprise-corebazzar-media-123";
    private final String cdnBaseUrl="https://"+bucketName+".s3.amazonaws.com";
    @Override
    public String getPublicUrl(String key) {
        if(key==null) return null;

        return cdnBaseUrl+ "/" +key;
    }

    @Override
    public String uploadProfileImage(MultipartFile file, Long customerId) {
        String key="users"+customerId+"profile.jpg";
        try{
            PutObjectRequest putRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putRequest,
                    RequestBody.fromInputStream(
                            file.getInputStream(),
                            file.getSize()
                    ));
            return key;
        } catch(Exception e){
            throw new RuntimeException("Failed to upload profile image", e);
        }
    }

    @Override
    public String uploadProductImage(Long id, MultipartFile file) {
        String key = "product-images/" + id + "/" + UUID.randomUUID();
        try{
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(request,RequestBody.fromBytes(file.getBytes()));
            return key;
        }catch(Exception e){
            throw new RuntimeException("Failed to upload product image", e);
        }
    }

    public void testConnection() {

        s3Client.listBuckets()
                .buckets()
                .forEach(b -> System.out.println("BUCKET: " + b.name()));
    }


}
