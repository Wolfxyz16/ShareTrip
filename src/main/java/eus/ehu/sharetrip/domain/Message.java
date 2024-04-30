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

    protected Message() {
        // Constructor protegido para uso exclusivo del builder
    }

    // Constructor privado para el builder
    private Message(Builder builder) {
        this.id = builder.id;
        this.messageText = builder.messageText;
        this.sender = builder.sender;
        this.receiver = builder.receiver;
        this.senderName = builder.senderName;
        this.recipientName = builder.recipientName;
    }

    // Builder static inner class
    public static class Builder {
        private Long id;
        private String messageText;
        private User sender;
        private User receiver;
        private String senderName;
        private String recipientName;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder messageText(String messageText) {
            this.messageText = messageText;
            return this;
        }

        public Builder sender(User sender) {
            this.sender = sender;
            this.senderName = sender.getUsername();
            return this;
        }

        public Builder receiver(User receiver) {
            this.receiver = receiver;
            this.recipientName = receiver.getUsername();
            return this;
        }

        public Message build() {
            return new Message(this);
        }
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

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", message='" + messageText + '\'' +
                ", sender=" + sender +
                ", receiver=" + receiver +
                '}';
    }
}
