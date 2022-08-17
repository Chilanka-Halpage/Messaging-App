package csh.pro.messagingapp.repositories;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;

import csh.pro.messagingapp.models.Message;

public interface MessageRepository extends CassandraRepository<Message, UUID>{
    
}
