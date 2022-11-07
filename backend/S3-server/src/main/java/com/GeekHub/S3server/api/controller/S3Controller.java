package com.GeekHub.S3server.api.controller;

import com.GeekHub.S3server.api.request.UploadFileReq;
import com.GeekHub.S3server.api.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/s3")
@CrossOrigin(origins = "*", allowCredentials = "false")
public class S3Controller {
    private final S3Uploader s3Uploader;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(UploadFileReq uploadFileReq) throws IOException {

        log.info("Start uploading....");
        String url = s3Uploader.uploadFile(uploadFileReq);

        return new ResponseEntity<>("성공", HttpStatus.OK);
    }
}
