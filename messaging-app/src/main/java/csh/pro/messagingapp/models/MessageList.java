package csh.pro.messagingapp.models;

import java.util.List;

import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;

import csh.pro.messagingapp.keys.MessageListKey;

@Table(value = "messages_by_user_folder")
public class MessageList {

    @PrimaryKey
    private MessageListKey key;

    @CassandraType(type = Name.LIST, typeArguments = Name.TEXT)
    private List<String> to;

    @CassandraType(type = Name.TEXT)
    private String subject;

    @CassandraType(type = Name.BOOLEAN)
    private boolean isRead;

    @Transient
    private String agoTime;

    public MessageListKey getKey() {
        return key;
    }

    public void setKey(MessageListKey key) {
        this.key = key;
    }

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }

    public String getAgoTime() {
        return agoTime;
    }

    public void setAgoTime(String agoTime) {
        this.agoTime = agoTime;
    }
}
