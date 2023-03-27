package com.techeer.checkIt.domain.user.service;

import com.techeer.checkIt.domain.user.entity.User;
import com.techeer.checkIt.domain.user.exception.UserNotFoundException;
import com.techeer.checkIt.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void join() {
        User user = User.builder().build();
        userRepository.save(user);
    }

    public User findUserById(Long uid) {
        User user = userRepository.findById(uid).orElseThrow(UserNotFoundException::new);
        return user;
    }

}
