package ru.daniel.EncryptedMessage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.daniel.EncryptedMessage.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
}
