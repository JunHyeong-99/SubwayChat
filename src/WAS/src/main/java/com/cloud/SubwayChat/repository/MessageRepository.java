package com.cloud.SubwayChat.repository;

import com.cloud.SubwayChat.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findTop100ByRoomIdOrderBySentTimeAsc(String roomId);
}