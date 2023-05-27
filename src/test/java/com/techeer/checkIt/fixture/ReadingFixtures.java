package com.techeer.checkIt.fixture;

// <<<<<<< BE/chore/#49

import com.techeer.checkIt.domain.reading.dto.request.CreateReadingReq;
import com.techeer.checkIt.domain.reading.entity.Reading;
import com.techeer.checkIt.domain.reading.entity.ReadingStatus;

import static com.techeer.checkIt.fixture.BookFixtures.TEST_BOOKENT;
import static com.techeer.checkIt.fixture.UserFixtures.TEST_USER;

// =======
  
import com.techeer.checkIt.domain.reading.dto.request.UpdateReadingStatusReq;
import com.techeer.checkIt.domain.reading.entity.Reading;

import static com.techeer.checkIt.domain.reading.entity.ReadingStatus.*;
import static com.techeer.checkIt.fixture.BookFixtures.BOOK_ENT;
import static com.techeer.checkIt.fixture.BookFixtures.BOOK_ENT2;
import static com.techeer.checkIt.fixture.UserFixtures.TEST_USER;

public class ReadingFixtures {
    public static final UpdateReadingStatusReq TEST_UPDATE_READ_REQ =
            UpdateReadingStatusReq.builder()
                    .bookId(1L)
                    .lastPage(148)
                    .status(READ)
                    .build();
    public static final UpdateReadingStatusReq TEST_UPDATE_READING_REQ =
            UpdateReadingStatusReq.builder()
                    .bookId(1L)
                    .lastPage(130)
                    .status(READING)
                    .build();
    public static final UpdateReadingStatusReq TEST_UPDATE_UNREAD_REQ =
            UpdateReadingStatusReq.builder()
                    .bookId(1L)
                    .lastPage(0)
                    .status(UNREAD)
                    .build();

    public static final Reading TEST_READING =
            Reading.builder()
                    .user(TEST_USER)
                    .book(BOOK_ENT)
                    .lastPage(100)
                    .build();
    public static final Reading TEST_READING2 =
            Reading.builder()
                    .user(TEST_USER)
                    .book(BOOK_ENT2)
                    .lastPage(120)
                    .status(READING)
// >>>>>>> develop
                    .build();
    public static final Reading TEST_READING =
            Reading.builder()
                    .user(TEST_USER)
                    .book(TEST_BOOKENT)
                    .status(ReadingStatus.READING)
                    .lastPage(81)
                    .build();
    public static final Reading TEST_READING2 =
            Reading.entityBuilder()
                    .id(1L)
                    .user(TEST_USER)
                    .book(TEST_BOOKENT)
                    .status(ReadingStatus.READING)
                    .lastPage(81)
                    .build();

    public static final CreateReadingReq TEST_READINGREQ =
            CreateReadingReq.builder()
                    .bookId(1L)
                    .lastPage(0)
                    .status("READING")
                    .build();
 
}

