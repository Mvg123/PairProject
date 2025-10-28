package com.example.user.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "CUSMSG")
public class CustomerMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    private int cusnum;

    @Column(unique = true, nullable = false)
    private String messageId = UUID.randomUUID().toString();

    public CustomerMessage() {}
    public CustomerMessage(String name, String phone, int cusnum) {
        this.name = name;
        this.phone = phone;
        this.cusnum = cusnum;
    }

    public String getMessageId() { return messageId; }
    public void setMessageId(String messageId) { this.messageId = messageId; }
}
