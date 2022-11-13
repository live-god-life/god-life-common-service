package com.godlife.godlifecommonservice.controller;

import com.godlife.godlifecommonservice.request.RequestImage;
import com.godlife.godlifecommonservice.response.ApiResponse;
import com.godlife.godlifecommonservice.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.godlife.godlifecommonservice.constants.CommonConstants.MESSAGE_SUCCESS_GET_IMAGES;
import static com.godlife.godlifecommonservice.constants.CommonConstants.MESSAGE_SUCCESS_POST_IMAGES;

@RequestMapping("/commons")
@RequiredArgsConstructor
@RestController
public class CommonController {

    /** 공통 서비스 */
    private final CommonService commonService;

    /**
     * 전체 프로필 이미지 조회
     * @return 전체 프로필 이미지
     */
    @GetMapping("/images")
    public ResponseEntity<ApiResponse<?>> getProfileImages() {
        return ResponseEntity.ok(ApiResponse.successResponse(commonService.getProfileImages(), MESSAGE_SUCCESS_GET_IMAGES));
    }

}
