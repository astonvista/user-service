package com.example.user_service.service;

import com.example.user_service.dto.UserEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final String topic = "user-events";

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendUserEvent(String operation, String email) throws JsonProcessingException {
        UserEvent event = new UserEvent(operation, email);
        kafkaTemplate.send(topic, objectMapper.writeValueAsString(event));
        log.info("Сообщение отправлено в брокер");
    }
}
