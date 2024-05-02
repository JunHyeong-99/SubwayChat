package com.cloud.SubwayChat.service;

import com.cloud.SubwayChat.domain.SubwayLine;
import com.cloud.SubwayChat.domain.User;
import com.cloud.SubwayChat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User join(String nickName, SubwayLine line){
        User user = new User(nickName, line);

        return userRepository.save(user);
    }
}
