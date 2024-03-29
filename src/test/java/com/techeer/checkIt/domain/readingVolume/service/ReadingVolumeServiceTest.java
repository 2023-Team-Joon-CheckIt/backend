package com.techeer.checkIt.domain.readingVolume.service;


import com.techeer.checkIt.domain.readingVolume.exception.ReadingVolumeNotFoundException;
import com.techeer.checkIt.domain.readingVolume.dto.response.SearchReadingVolumesRes;
import com.techeer.checkIt.domain.readingVolume.entity.ReadingVolume;
import com.techeer.checkIt.domain.readingVolume.mapper.ReadingVolumeMapper;
import com.techeer.checkIt.domain.readingVolume.repository.ReadingVolumeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.techeer.checkIt.fixture.ReadingVolumeFixtures.*;
import static com.techeer.checkIt.fixture.UserFixtures.TEST_USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReadingVolumeServiceTest {
    @InjectMocks
    private ReadingVolumeService readingVolumeService;

    @Mock
    private ReadingVolumeRepository readingVolumeRepository;
    @Mock
    private ReadingVolumeMapper readingVolumeMapper;

    // TEST_SEARCH_READINGVOLUME_RES 수정 필요 (api에서 원하는 날짜로 요청하도록 수정)
    @Test
    @DisplayName("user의 일주일 치 독서 기록을 반환한다.")
    void findReadingVolumesByUserAndDateBetween() {
        List<ReadingVolume> list = new ArrayList<>();
        list.add(TEST_READINGVOLUME_ENT);

        List<SearchReadingVolumesRes> list2 = new ArrayList<>();
        list2.add(TEST_SEARCH_READINGVOLUME_RES);

        given(readingVolumeRepository.findByUserAndDateBetween(any(),any(),any())).willReturn(list);

        when(readingVolumeMapper.toDtoList(anyList())).thenReturn(list2);

        List<SearchReadingVolumesRes> newList = readingVolumeService.findReadingVolumesByUserAndDateBetween(TEST_USER, "2023-10-26");

        assertEquals(newList, list2);

    }

    @Test
    @DisplayName("readingVolume을 저장")
    void registerReadingVolume() {
        given(readingVolumeRepository.save(any())).willReturn(TEST_READINGVOLUME_ENT);

        ReadingVolume readingVolume = readingVolumeService.registerReadingVolume(TEST_USER, 10);
        assertEquals(
                TEST_READINGVOLUME_ENT, readingVolume);
    }

    @Test
    @DisplayName("user의 당일 독서 기록을 불러온다.")
    void findReadingVolumeByUserAndDate() {
        given(readingVolumeRepository.findByUserAndDate(any(),any())).willReturn(Optional.of(TEST_READINGVOLUME_ENT));

        LocalDate date = LocalDate.now();
        ReadingVolume readingVolume = readingVolumeService.findReadingVolumeByUserAndDate(TEST_USER, date);
        assertEquals(TEST_READINGVOLUME_ENT, readingVolume);
    }


    @Test
    @DisplayName("user의 당일 독서 기록이 있는지 확인한다.")
    void existsUserAndDate() {
        when(readingVolumeService.existsUserAndDate(any(), any())).thenReturn(true);

        LocalDate date = LocalDate.now();
        boolean flag1 = readingVolumeService.existsUserAndDate(TEST_USER,date);
        assertEquals(true, flag1);
    }

    @Test
    @DisplayName("해당 날짜의 독서추세를 찾을 수 없는 경우")
    void readingVolumeNotFoundException() {
        LocalDate date = LocalDate.now();
        Mockito.lenient().when(readingVolumeRepository.findByUserAndDate(TEST_USER, date)).thenThrow(new ReadingVolumeNotFoundException());

        assertThrows(ReadingVolumeNotFoundException.class, () -> {
            readingVolumeRepository.findByUserAndDate(TEST_USER, date);
        }, "해당 날짜의 독서이력이 있습니다.");
    }
}