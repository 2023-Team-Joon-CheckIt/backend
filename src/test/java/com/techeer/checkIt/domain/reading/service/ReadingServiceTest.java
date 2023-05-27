package com.techeer.checkIt.domain.reading.service;

import com.techeer.checkIt.domain.book.dto.Response.BookRes;
import com.techeer.checkIt.domain.reading.dto.request.UpdateReadingStatusReq;
import com.techeer.checkIt.domain.reading.entity.Reading;
import com.techeer.checkIt.domain.reading.mapper.ReadingMapper;
import com.techeer.checkIt.domain.reading.repository.ReadingRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.techeer.checkIt.domain.reading.entity.ReadingStatus.READ;
import static com.techeer.checkIt.domain.reading.entity.ReadingStatus.READING;
import static com.techeer.checkIt.fixture.BookFixtures.*;
import static com.techeer.checkIt.fixture.ReadingFixtures.*;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReadingServiceTest{
    @InjectMocks
    private ReadingService readingService;
    @Mock
    private ReadingRepository readingRepository;

    @Mock
    private ReadingMapper readingMapper;

    @Test
    @DisplayName("Service) 상태 별 책 조회")
    void findReadingByStatus() {
        // given
        List<Reading> readings = new ArrayList<>();
        readings.add(TEST_READING);
        readings.add(TEST_READING2);

        List<BookRes> bookListByStatus  = new ArrayList<>();
        bookListByStatus.add(TEST_BOOK);
        bookListByStatus.add(TEST_BOOK2);

        given(readingRepository.findByUserIdAndStatus(1L, READING)).willReturn(readings);
        when(readingMapper.toDtoList(readings)).thenReturn(bookListByStatus);

        // when
        bookListByStatus  = readingService.findReadingByStatus(1L, READING);

        // then
        assertThat(bookListByStatus.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Service) 독서 상태 변경")
    void updateReadingStatus() {
        // given
        UpdateReadingStatusReq updateStatusToRead = TEST_UPDATE_READ_REQ;       // READ로 변경
        UpdateReadingStatusReq updateStatusToReading = TEST_UPDATE_READING_REQ; // READIING으로 변경
        UpdateReadingStatusReq updateStatusToUnread = TEST_UPDATE_UNREAD_REQ;   // UNREAD로 변경

        given(readingRepository.findByUserIdAndBookIdAndStatus(any(), any(), any())).willReturn(Optional.ofNullable(TEST_READING));

        // when
        readingService.updateReadingStatus(1L, 1L, READING, updateStatusToRead);
        readingService.updateReadingStatus(1L, 1L, READ, updateStatusToReading);
        readingService.updateReadingStatus(1L, 1L, READING, updateStatusToUnread);


        // then
        assertThat(updateStatusToRead.getLastPage()).isEqualTo(TEST_READING.getBook().getPages());
        assertThat(updateStatusToReading.getLastPage()).isEqualTo(130);
        assertThat(updateStatusToUnread.getLastPage()).isEqualTo(0);

    }
}