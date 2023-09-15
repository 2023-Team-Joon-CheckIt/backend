package com.techeer.checkIt.domain.book.service;

import com.techeer.checkIt.domain.book.dao.RedisDao;
import com.techeer.checkIt.domain.book.dto.Response.BookRes;
import com.techeer.checkIt.domain.book.dto.Response.BookSearchRes;
import com.techeer.checkIt.domain.book.entity.Book;
import com.techeer.checkIt.domain.book.entity.BookDocument;
import com.techeer.checkIt.domain.book.mapper.BookMapper;
import com.techeer.checkIt.domain.book.exception.BookNotFoundException;
import com.techeer.checkIt.domain.book.repository.BookJpaRepository;
import com.techeer.checkIt.domain.book.repository.BookSearchRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class BookService {
    private final BookJpaRepository bookJpaRepository;
    private final BookSearchRepository bookSearchRepository;
    private final BookMapper bookMapper;
    private final RedisDao redisDao;

    public List<BookSearchRes> findBookByTitle(String title) {
        List<BookDocument> books = bookSearchRepository.findByTitleContaining(title);
        return bookMapper.toDtoList(books);
    }

    // id별 조회할 때
    public BookRes findBookById(Long userId, Long id) {
        Book book = bookJpaRepository.findByBookId(id).orElseThrow(BookNotFoundException::new);
        String redisKey = "B" + id.toString();
        String redisUserKey = "U" + userId.toString();
        String values = (redisDao.getValues(redisKey) == null) ? redisDao.setValues(redisKey,"0") : redisDao.getValues(redisKey);
        int views = Integer.parseInt(values);
        boolean like_status = !redisDao.getValuesList(redisUserKey).contains(redisKey.substring(1)) ? false : true;
        return bookMapper.toDto(book, views, like_status);
    }
    // 책 판별용
    public Book findById(Long id) {
        return bookJpaRepository.findById(id).orElseThrow(BookNotFoundException::new);
    }

    public void updateLike(Long userId, Long bookId) {
        String redisKey = "B" + bookId.toString(); // 게시글 key
        String redisUserKey = "U" + userId.toString(); // 유저 key
        // redis에 없는 게시글 id가 들어올 경우 : 새롭게 데이터를 만들어주고 좋아요수를 0으로 초기화, 있는 경우 : 현제 좋아요수 반환
        String values = (redisDao.getValues(redisKey) == null) ? redisDao.setValues(redisKey,"0") : redisDao.getValues(redisKey);
        int views = Integer.parseInt(values);

        // 유저를 key로 조회한 게시글 ID List안에 해당 게시글 ID가 포함되어있지 않는다면,
        if (!redisDao.getValuesList(redisUserKey).contains(redisKey.substring(1))) {
            redisDao.setValuesList(redisUserKey, redisKey.substring(1)); // 유저 key로 해당 글 ID를 List 형태로 저장
            views = Integer.parseInt(values) + 1; // 좋아요 증가
            redisDao.setValues(redisKey, String.valueOf(views)); // 글ID key로 좋아요 저장
        } else {
            redisDao.deleteValueList(redisUserKey, redisKey.substring(1)); // 유저 key로 해당 글 ID를 List 형태에서 제거
            views = Integer.parseInt(values) - 1; // 좋아요 감소
            redisDao.setValues(redisKey, String.valueOf(views)); // 글ID key로 좋아요 저장
        }
    }


}
