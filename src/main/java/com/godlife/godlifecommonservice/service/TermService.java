package com.godlife.godlifecommonservice.service;

import com.godlife.godlifecommonservice.domain.dto.TermDto;
import com.godlife.godlifecommonservice.domain.entity.MarketingTerm;
import com.godlife.godlifecommonservice.domain.entity.PrivacyTerm;
import com.godlife.godlifecommonservice.domain.entity.Term;
import com.godlife.godlifecommonservice.domain.entity.UseTerm;
import com.godlife.godlifecommonservice.repository.TermRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TermService {

    /** 약관 Repository */
    private final TermRepository termRepository;
    /**
     * 약관 추가
     * @param type      약관 타입
     * @param version   약관 버전
     * @param contents  약관 컨텐츠
     * @param required  약관 필수 여부
     */
    public void insertTerm(String type, String version, String contents, boolean required) {
        Term term = getTermFactory(type, version, contents, required);

        termRepository.save(term);
    }

    /**
     * 약관 팩토리
     * @param type      약관 타입
     * @param version   약관 버전
     * @param contents  약관 컨텐츠
     * @param required  약관 필수 여부
     * @return 약관 Entity
     */
    private Term getTermFactory(String type, String version, String contents, boolean required) {
        switch (type) {
            case "use":
                return UseTerm.of(version, contents, required);
            case "privacy":
                return PrivacyTerm.of(version, contents, required);
            case "marketing":
                return MarketingTerm.of(version, contents, required);
            default:
                return null;
        }
    }
}
