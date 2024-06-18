package ru.daniel.EncryptedMessage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.daniel.EncryptedMessage.repositories.MessageRepository;
import ru.daniel.EncryptedMessage.model.Message;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MessageService {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> findAll() {
        if (true)
            return messageRepository.findAll();
        else
            return messageRepository.findAll();
    }

    public Message findOne(int id) {
        Optional<Message> foundMessage= messageRepository.findById(id);
        return foundMessage.orElse(null);
    }

    @Transactional
    public void save(Message message) {
        messageRepository.save(message);
    }
}
