package com.techeer.checkIt.domain.review.controller;

import com.techeer.checkIt.domain.book.entity.Book;
import com.techeer.checkIt.domain.book.service.BookService;
import com.techeer.checkIt.domain.review.dto.request.CreateReviewReq;
import com.techeer.checkIt.domain.review.dto.response.ReviewRes;
import com.techeer.checkIt.domain.review.service.ReviewService;
import com.techeer.checkIt.domain.user.entity.User;
import com.techeer.checkIt.domain.user.entity.UserDetail;
import com.techeer.checkIt.domain.user.service.UserService;
import com.techeer.checkIt.global.result.ResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.techeer.checkIt.global.result.ResultCode.GET_REVIEW_SUCCESS;
import static com.techeer.checkIt.global.result.ResultCode.REVIEW_CREATE_SUCCESS;
import static com.techeer.checkIt.global.result.ResultCode.REVIEW_DELETE_SUCCESS;
import static com.techeer.checkIt.global.result.ResultCode.REVIEW_UPDATE_SUCCESS;

@Api(tags = "리뷰 API")
@RestController
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@RequestMapping("/api/v1/reviews")
public class ReviewController {
  private  final ReviewService reviewService;
  private final UserService userService;
  private final BookService bookService;

  @ApiOperation(value = "리뷰 생성 API")
  @PostMapping
  public ResponseEntity<ResultResponse> createReview(
      @AuthenticationPrincipal UserDetail userDetail,
      @RequestBody @Valid CreateReviewReq createReviewReq
  ){
    User user = userService.findUserByUsername(userDetail.getUsername());
    Book book = bookService.findById(createReviewReq.getBookId());
    reviewService.createReview(user, book, createReviewReq);
    ReviewRes reviewRes = reviewService.findReviewByUserNameIdBookId(user, createReviewReq.getBookId());
    return ResponseEntity.ok(ResultResponse.of(REVIEW_CREATE_SUCCESS, reviewRes));
  }

  @ApiOperation(value = "리뷰 조회 API")
  @GetMapping("{bookId}")
  public ResponseEntity<ResultResponse> getReviewByUserNameBookId(
      @AuthenticationPrincipal UserDetail userDetail,
      @PathVariable Long bookId
  ){
    User user = userService.findUserByUsername(userDetail.getUsername());
    ReviewRes reviewRes = reviewService.findReviewByUserNameIdBookId(user, bookId);
    return ResponseEntity.ok(ResultResponse.of(GET_REVIEW_SUCCESS, reviewRes));
  }

  @ApiOperation(value = "리뷰 삭제 API")
  @DeleteMapping ("{bookId}")
  public ResponseEntity<ResultResponse> deleteReviewByUserNameBookId(
      @AuthenticationPrincipal UserDetail userDetail,
      @PathVariable Long bookId
  ){
    User user = userService.findUserByUsername(userDetail.getUsername());
    reviewService.deleteReview(user, bookId);
    return ResponseEntity.ok(ResultResponse.of(REVIEW_DELETE_SUCCESS));
  }

  @ApiOperation(value = "리뷰 내용 변경 API")
  @PutMapping
  public ResponseEntity<ResultResponse> updateReview(
      @AuthenticationPrincipal UserDetail userDetail,
      @RequestBody @Valid CreateReviewReq createReviewReq
  ) {
    User user = userService.findUserByUsername(userDetail.getUsername());
    reviewService.updateReview(user, createReviewReq);
    ReviewRes reviewRes = reviewService.findReviewByUserNameIdBookId(user, createReviewReq.getBookId());
    return ResponseEntity.ok(ResultResponse.of(REVIEW_UPDATE_SUCCESS, reviewRes));
  }

}
