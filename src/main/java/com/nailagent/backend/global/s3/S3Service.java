package com.nailagent.backend.global.s3;

import com.nailagent.backend.global.exception.CustomException;
import com.nailagent.backend.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    public String upload(MultipartFile file, String reserveDate, String customerName) {
        String extension = getExtension(file.getOriginalFilename());
        // S3 키는 한글 그대로 저장 (S3는 유니코드 키 지원)
        String key = reserveDate + "/" + customerName + "/" + UUID.randomUUID() + extension;
        // URL 반환 시에만 이름 부분 인코딩
        String encodedName = URLEncoder.encode(customerName, StandardCharsets.UTF_8).replace("+", "%20");
        String url = "https://" + bucket + ".s3." + region + ".amazonaws.com/"
                + reserveDate + "/" + encodedName + "/" + key.substring(key.lastIndexOf("/") + 1);

        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(file.getContentType())
                    .contentLength(file.getSize())
                    .build();

            s3Client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            log.info("S3 업로드 완료: {}", key);
        } catch (IOException e) {
            log.error("S3 업로드 실패: {}", key, e);
            throw new CustomException(ErrorCode.S3_UPLOAD_FAILED);
        }

        return url;
    }

    public void delete(String imageUrl) {
        String baseUrl = "https://" + bucket + ".s3." + region + ".amazonaws.com/";
        // URL에 인코딩된 한글을 디코딩해서 실제 S3 키로 변환
        String encodedKey = imageUrl.substring(baseUrl.length());
        String key = java.net.URLDecoder.decode(encodedKey, StandardCharsets.UTF_8);
        try {
            s3Client.deleteObject(b -> b.bucket(bucket).key(key));
            log.info("S3 삭제 완료: {}", key);
        } catch (Exception e) {
            log.error("S3 삭제 실패: {}", key, e);
        }
    }

    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) return "";
        return filename.substring(filename.lastIndexOf("."));
    }
}
