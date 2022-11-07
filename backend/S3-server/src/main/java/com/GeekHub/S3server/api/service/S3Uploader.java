package com.GeekHub.S3server.api.service;

import com.GeekHub.S3server.api.request.UploadFileReq;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    public String uploadFile(UploadFileReq uploadFileReq) throws IOException {
        log.info("Start uploading picture by : " + uploadFileReq.getUserId());
        log.info("Start uploading picture from : " + uploadFileReq.getSpotId());
        String currentUserId = uploadFileReq.getUserId();
        MultipartFile curImage = uploadFileReq.getImage();

        String originalName = LocalDate.now() + "/" + currentUserId + "/" + createFileName(curImage.getOriginalFilename());
        long size = curImage.getSize(); // 파일 크기

        ObjectMetadata objectMetaData = new ObjectMetadata();
        objectMetaData.setContentType(curImage.getContentType());
        objectMetaData.setContentLength(size);

        // S3에 업로드
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, originalName, curImage.getInputStream(), objectMetaData)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );

        String imagePath = amazonS3Client.getUrl(bucket, originalName).toString();

        /*
         * url 저장하는 코드 작성
         * */

        log.info("Picture save complete");
        return imagePath;
    }

    // 파일의 이름을 암호화
    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName)); // 파일 이름
    }

    //
    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
        }
    }

//    private String setFileNameAsDriver(){
//     return getName
//    }

}
