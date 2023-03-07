package com.godlife.godlifecommonservice.controller;

import com.godlife.godlifecommonservice.request.RequestImage;
import com.godlife.godlifecommonservice.request.RequestTerm;
import com.godlife.godlifecommonservice.response.ApiResponse;
import com.godlife.godlifecommonservice.service.ImageService;
import com.godlife.godlifecommonservice.service.TermService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;

import static com.godlife.godlifecommonservice.constants.MessageConstants.*;

@RequestMapping("/commons")
@RequiredArgsConstructor
@RestController
public class CommonController {

    /**
     * 이미지 API 서비스
     */
    private final ImageService imageService;

    /**
     * 약관 API 서비스
     */
    private final TermService termService;

    /**
     * 전체 프로필 이미지 조회
     *
     * @return 전체 프로필 이미지
     */
    @GetMapping("/images")
    public ResponseEntity<ApiResponse<?>> getProfileImages() {
        return ResponseEntity.ok(ApiResponse.successResponse(imageService.getProfileImages(), MESSAGE_SUCCESS_GET_IMAGES));
    }

    /**
     * 프로필 이미지 추가
     *
     * @param requestImage 이미지 정보
     * @return 추가 결과
     */
    @PostMapping("/images")
    public ResponseEntity<ApiResponse<?>> insertProfileImage(@RequestBody RequestImage requestImage) {
        imageService.insertProfileImage(requestImage.getUrl());
        return ResponseEntity.ok(ApiResponse.successResponse(null, MESSAGE_SUCCESS_POST_IMAGES));
    }

    /**
     * 프로필 이미지 접근
     *
     * @param imageName 이미지명
     * @return 프로필 이미지
     */
    @GetMapping("/images/{imageName}")
    public ResponseEntity<Resource> getImage(
            @PathVariable(value = "imageName") String imageName) {
        try {
            String path = "/home/images/commons";
            FileSystemResource resource = new FileSystemResource(path + imageName);
            if (!resource.exists()) {
                throw new NoSuchElementException();
            }
            HttpHeaders header = new HttpHeaders();
            Path filePath = Paths.get(path + imageName);
            header.add("Content-Type", Files.probeContentType(filePath));
            return new ResponseEntity<>(resource, header, HttpStatus.OK);
        } catch (Exception e) {
            throw new NoSuchElementException();
        }
    }

    /**
     * 약관 조회
     *
     * @param type 약관 타입
     * @return 약관
     */
    @GetMapping("/terms/{type}")
    public ResponseEntity<ApiResponse<?>> getTerms(@PathVariable String type) {
        return ResponseEntity.ok(ApiResponse.successResponse(termService.getTerms(type), MESSAGE_SUCCESS_GET_TERMS));
    }

    /**
     * 약관 추가
     *
     * @param requestTerm 약관 정보
     * @return 추가 결과
     */
    @PostMapping("/terms")
    public ResponseEntity<ApiResponse<?>> insertTerms(@RequestBody RequestTerm requestTerm) {
        termService.insertTerm(requestTerm.getType(), requestTerm.getVersion(), requestTerm.getContents(), requestTerm.isRequired());
        return ResponseEntity.ok(ApiResponse.successResponse(null, MESSAGE_SUCCESS_POST_TERMS));
    }

    /**
     * 카테고리 조회
     *
     * @return 카테고리 리스트
     */
    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<?>> getCategories() {
        return ResponseEntity.ok(ApiResponse.successResponse(termService.getCategories(), MESSAGE_SUCCESS_GET_CODES));
    }
}
