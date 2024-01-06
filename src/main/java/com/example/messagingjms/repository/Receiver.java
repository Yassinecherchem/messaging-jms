package com.example.messagingjms.repository;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.example.messagingjms.model.Email;

@Component
public class Receiver {
    @JmsListener(destination = "mailbox", containerFactory = "myFactory")
    public void receiveMessage(Email email) {
        System.out.println("---- repository.Received from sender: <" + email + ">");
    }

}
