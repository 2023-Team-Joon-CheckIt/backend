package com.techeer.checkIt.domain.chat.repository;

import com.techeer.checkIt.domain.chat.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByUserIdAndChatRoomId(Long userId, Long chatRoomId);

}
