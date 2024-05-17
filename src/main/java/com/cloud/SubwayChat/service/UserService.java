package com.cloud.SubwayChat.service;

import com.cloud.SubwayChat.domain.SubwayLine;
import com.cloud.SubwayChat.domain.User;
import com.cloud.SubwayChat.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User join(String nickName, HttpSession session){
        User user = User.builder()
                .nickName(nickName)
                .build();

        userRepository.save(user);

        // 세션에 사용자 정보 저장
        session.setAttribute("USER_ID", user.getId());
        session.setAttribute("NICKNAME", user.getNickName());

        return user;
    }
}
