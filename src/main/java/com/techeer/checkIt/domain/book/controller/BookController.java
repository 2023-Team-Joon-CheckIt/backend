package com.techeer.checkIt.domain.book.controller;

import com.techeer.checkIt.domain.book.dto.Response.BookRes;
import com.techeer.checkIt.domain.book.dto.Response.BookSearchLikeRes;
import com.techeer.checkIt.domain.book.dto.Response.BookSearchRes;
import com.techeer.checkIt.domain.book.service.BookService;
import com.techeer.checkIt.domain.user.entity.User;
import com.techeer.checkIt.domain.user.entity.UserDetail;
import com.techeer.checkIt.domain.user.service.UserService;
import com.techeer.checkIt.global.result.ResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.techeer.checkIt.global.result.ResultCode.GET_LIKE_BOOK_SUCCESS;
import static com.techeer.checkIt.global.result.ResultCode.GET_NEW_BOOK_SUCCESS;
import static com.techeer.checkIt.global.result.ResultCode.UPDATE_BOOK_LIKE_SUCCESS;
import static com.techeer.checkIt.global.result.ResultCode.GET_ONE_BOOK_SUCCESS;

@Api(tags = "책 API")
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@RestController
public class BookController {
    private final BookService bookService;
    private final UserService userService;

    @ApiOperation(value = "책 검색 API")
    @GetMapping("/search")
    public ResponseEntity<List<BookSearchRes>> searchTitle(@RequestParam(defaultValue = "") String title) {
        List<BookSearchRes> bookList = bookService.findBookByTitle(title);
        return ResponseEntity.ok(bookList);
    }

    @ApiOperation(value = "책 한 권 조회 API")
    @GetMapping("{bookId}")
    public ResponseEntity<ResultResponse> getBookById(
        @AuthenticationPrincipal UserDetail userDetail,
        @PathVariable Long bookId
    ){
        User user = userService.findUserByUsername(userDetail.getUsername());
        BookRes bookResponse = bookService.findBookById(user.getId(), bookId);
        return ResponseEntity.ok(ResultResponse.of(GET_ONE_BOOK_SUCCESS, bookResponse));
    }

    @ApiOperation(value = "신규 도서 조회 API")
    @GetMapping("/new")
    public ResponseEntity<ResultResponse> getNewBooksList() {
        Page<BookSearchRes> books = bookService.sortedBooksByTime();
        return ResponseEntity.ok(ResultResponse.of(GET_NEW_BOOK_SUCCESS, books));
    }

    @ApiOperation(value = "책 좋아요 API")
    @PostMapping("/like/{bookId}")
    public ResponseEntity<ResultResponse> updateLikeById(
        @AuthenticationPrincipal UserDetail userDetail,
        @PathVariable Long bookId
    ){
        User user = userService.findUserByUsername(userDetail.getUsername());
        bookService.updateLike(user.getId(), bookId);
        BookRes bookResponse = bookService.findBookById(user.getId(), bookId);
        return ResponseEntity.ok(ResultResponse.of(UPDATE_BOOK_LIKE_SUCCESS ,bookResponse));
    }

    @ApiOperation(value = "인기 있는 책 조회 API")
    @GetMapping("/like")
    public ResponseEntity<ResultResponse> getLikeBooksList() {
        Page<BookSearchLikeRes> books = bookService.sortedBooksByLike();
        return ResponseEntity.ok(ResultResponse.of(GET_LIKE_BOOK_SUCCESS, books));
    }
}
