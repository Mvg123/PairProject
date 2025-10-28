package com.example.user.dto;

import com.example.user.model.Gym;
import com.example.user.model.Message;

import java.util.List;

public class GymAndMessage {
    private List<Gym> gyms;
    private List<Message> messages;

    public GymAndMessage(List<Gym> gyms, List<Message> messages) {
        this.gyms = gyms;
        this.messages = messages;
    }

    public List<Gym> getGyms() {
        return gyms;
    }

    public List<Message> getMessages() {
        return messages;
    }
}
