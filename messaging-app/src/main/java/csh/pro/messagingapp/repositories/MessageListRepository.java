package csh.pro.messagingapp.repositories;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import csh.pro.messagingapp.keys.MessageListKey;
import csh.pro.messagingapp.models.MessageList;

@Repository
public interface MessageListRepository extends CassandraRepository<MessageList, MessageListKey> {

    List<MessageList> getMessageListByKeyUserIDAndKeyLabel(String userID, String label);
}
