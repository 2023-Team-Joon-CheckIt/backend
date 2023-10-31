package com.techeer.checkIt.global.result;


import lombok.AllArgsConstructor;
import lombok.Getter;

/** {행위}_{목적어}_{성공여부} message 는 동사 명사형으로 마무리 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    // 도메인 별로 나눠서 관리(ex: User 도메인)
    // User
    USER_REGISTRATION_SUCCESS("U001", "사용자 등록 성공"),
    USER_USERNAME_DUPLICATED("U002", "회원 아이디 중복"),
    USER_USERNAME_NOT_DUPLICATED("U003", "회원 아이디 중복되지 않음"),
    USER_LOGIN_SUCCESS("U004", "회원 로그인 성공"),
    USER_LOGOUT_SUCCESS("U005", "회원 로그아웃 성공"),
    USER_REISSUE_SUCCESS("U006", "토큰 재발급 성공"),
    GET_LOGIN_USER_SUCCESS("U007", "로그인 되어있는 회원 조회 성공"),

    // Book
    GET_NEW_BOOK_SUCCESS("B001","신규 도서 조회 성공"),
    UPDATE_BOOK_LIKE_SUCCESS("B002","좋아요 갱신 성공"),
    GET_ONE_BOOK_SUCCESS("B003","책 조회 성공"),
    GET_LIKE_BOOK_SUCCESS("B004","인기있는 도서 조회 성공"),


    // Reading
    READING_CREATE_SUCCESS("R001", "독서정보 생성 성공"),
    READING_UPDATE_SUCCESS("R002", "독서정보 갱신 성공"),
    READING_STATUS_UPDATE_SUCCESS("R003", "독서 상태 갱신 성공"),
    READING_PERCENTAGE_SUCCESS("R004", "읽은 퍼센트 계산 성공"),

    // ReadingVolume
    GET_READING_VOLUMES_SUCCESS("RV001", "독서추세 조회 성공"),

    // Review
    REVIEW_CREATE_SUCCESS("RE001", "리뷰 등록 성공"),
    GET_REVIEW_SUCCESS("RE002", "리뷰 조회 성공"),
    REVIEW_DELETE_SUCCESS("RE003", "리뷰 삭제 성공"),
    REVIEW_UPDATE_SUCCESS("RE004", "리뷰 갱신 성공"),
    ;

    private final String code;
    private final String message;
}
