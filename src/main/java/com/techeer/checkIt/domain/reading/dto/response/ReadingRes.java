package com.techeer.checkIt.domain.reading.dto.response;

import com.techeer.checkIt.domain.book.dto.Response.BookReadingRes;
import com.techeer.checkIt.domain.book.dto.Response.BookRes;
import com.techeer.checkIt.domain.reading.entity.ReadingStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReadingRes {
    private List<BookReadingRes> bookInfos;
    private ReadingStatus status;
}
