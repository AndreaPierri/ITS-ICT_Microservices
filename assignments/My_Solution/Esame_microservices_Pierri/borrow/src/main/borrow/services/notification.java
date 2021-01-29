package org.pierri.borrow.services;

import org.pierri.borrow.model.Borrow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


public class NotificationClient {

    @Value("${kafka.sms.topic}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, Borrow> kafkaTemplate;

    public void sendSMS(Borrow borrow){
        kafkaTemplate.send(topic, borrow);
    }

}