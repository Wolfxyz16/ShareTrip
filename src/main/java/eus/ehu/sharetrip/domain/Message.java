package eus.ehu.sharetrip.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "MESSAGES")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "MESSAGE")
    private String messageText;


    @ManyToOne
    private User receiver;

    @ManyToOne
    private User sender;

    private String senderName;

    private String recipientName;


    public Message() {

    }

    public Message(String messageText, User sender, User receiver) {
        this.messageText = messageText;
        this.sender = sender;
        this.receiver = receiver;
        this.senderName = sender.getUserName();
        this.recipientName = receiver.getUserName();
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String toString() {
        return "Message{" +
                "id=" + id +
                ", message='" + messageText + '\'' +
                ", from=" + sender +
                ", to=" + receiver +
                '}';

    }
}
