package com.techeer.checkIt.domain.book.controller;

import com.techeer.checkIt.domain.book.dto.Response.BookRes;
import com.techeer.checkIt.domain.book.dto.Response.BookSearchRes;
import com.techeer.checkIt.domain.book.service.BookService;
import com.techeer.checkIt.domain.user.entity.User;
import com.techeer.checkIt.domain.user.entity.UserDetail;
import com.techeer.checkIt.domain.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<BookRes> getBookById(@AuthenticationPrincipal UserDetail userDetail, @PathVariable Long bookId){
        User user = userService.findUserByUsername(userDetail.getUsername());
        BookRes bookResponse = bookService.findBookById(user.getId(), bookId);
        return ResponseEntity.ok(bookResponse);
    }

    @ApiOperation(value = "책 좋아요 API")
    @GetMapping("/like/{bookId}")
    public ResponseEntity<BookRes> updateLikeById(@AuthenticationPrincipal UserDetail userDetail, @PathVariable Long bookId){
        User user = userService.findUserByUsername(userDetail.getUsername());
        bookService.updateLike(user.getId(), bookId);
        BookRes bookResponse = bookService.findBookById(user.getId(), bookId);
        return ResponseEntity.ok(bookResponse);
    }
}
