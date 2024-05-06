package com.cloud.SubwayChat.repository;

import com.cloud.SubwayChat.domain.ChatRoom;
import com.cloud.SubwayChat.domain.SubwayLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    boolean existsBySubwayLine(SubwayLine subwayLine);
}
