package com.wtd.ddd.util.studycode;

import lombok.Getter;

/**
 * Created By mand2 on 2020-10-25.
 */
@Getter
public enum PlaceUtils {
    SEOUL_GANGNAM("SP10", "서울/강남"),
    SEOUL_KONKUK_UNIV("SP11", "서울/건대"),
    SEOUL_SINCHON("SP12", "서울/신촌홍대"),
    SEOUL_YEOUIDO("SP13", "서울/여의도"),
    GYEONGGI_PANGYO("SP20", "경기/판교"),
    GYEONGGI_SUWON("SP21", "경기/수원");

    private String code;
    private String description;

    PlaceUtils(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
