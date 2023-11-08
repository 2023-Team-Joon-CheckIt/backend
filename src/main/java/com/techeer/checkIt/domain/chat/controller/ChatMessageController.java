package com.techeer.checkIt.domain.chat.controller;

import com.techeer.checkIt.domain.chat.dto.request.CreateMessageReq;
import com.techeer.checkIt.domain.chat.service.UserChatRoomService;
import com.techeer.checkIt.domain.user.entity.UserDetail;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import java.util.Objects;

import static com.techeer.checkIt.global.constant.RabbitMQ.*;

@Controller
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessageController {
    //    private final SimpMessageSendingOperations sendingOperations;
    private final UserChatRoomService userChatRoomService;
    private final RabbitTemplate rabbitTemplate;

    //    @MessageMapping("/chat/rooms/{chatRoomId}/message")
    @MessageMapping("chat.message.{chatRoomId}")
    public void chat(StompHeaderAccessor headerAccessor,
                     @DestinationVariable Long chatRoomId,
                     @Payload CreateMessageReq messageRequest) {
        UserDetail userDetail = (UserDetail) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("userDetail");

//        sendingOperations.convertAndSend("/sub/chat/rooms/" + chatRoomId, messageRequest.getContent());
        rabbitTemplate.convertAndSend(CHAT_EXCHANGE_NAME + "room." + chatRoomId, messageRequest.getContent());


        userChatRoomService.saveMessage(userDetail.getUser(), chatRoomId, messageRequest);
        log.info("Message [{}] send by user: {} to chatting room: {}", messageRequest.getContent(), userDetail.getUsername(), chatRoomId);
    }

    //메세지가 큐에 도착할 때 실행
    @RabbitListener(queues = CHAT_QUEUE_NAME)
    public void receive(CreateMessageReq messageRequest) {
        log.info("message.getText = {}", messageRequest.getContent());
    }

//    @MessageMapping("/enter/rooms/{chatRoomId}")
//    public void enter(StompHeaderAccessor headerAccessor,
//                      @DestinationVariable Long chatRoomId) {
//        UserDetail userDetail = (UserDetail) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("userDetail");
//
//        if (userDetail.getUser().getRole() == Role.USER) { // 사용자는 관리자와의 채팅방 1개만 존재한다.
//            if (userChatRoomService.duplicatedUserChatRoom(userDetail.getUser())) { // 사용자가 이미 채팅방 입장했으면
//                throw new ChatRoomDuplicatedException();
//            }
//            ChatRoom chatRoom = userChatRoomService.createChatRoom();
//            userChatRoomService.createUserChatRoom(userDetail.getUser(), chatRoom.getId());
//
//        }
//        // TODO: 관리자가 먼저 채팅하는 경우는 없다는 가정하에 (더 나은 로직 생기면 수정하기)
//        else throw new ChatRoomDuplicatedException();
//
//        sendingOperations.convertAndSend("/sub/chat/rooms/" + chatRoomId,
//                        userDetail.getUser().getNickname() + " 님이 "
//                                + "채팅방 " + chatRoomId + "에 입장하셨습니다.");
//    }
}
