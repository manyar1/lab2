package ru.daniel.EncryptedMessage.service;

import org.springframework.stereotype.Service;
import ru.daniel.EncryptedMessage.model.Message;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class EncryptoService {
    private static SecretKey secretKey;

    public String encrypto(Message message)
            throws
            NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        // Генерация ключа для шифрования
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128); // Указываем размер ключа
        secretKey = keyGenerator.generateKey();

        // Создание экземпляра Cipher для алгоритма AES
        Cipher cipher = Cipher.getInstance("AES");

        // Инициализация Cipher в режиме шифрования с нашим ключом
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedMessage = cipher.doFinal(message.getText().getBytes());

        // Преобразование зашифрованного сообщения в строку для отправки
        String encodedMessage = Base64.getEncoder().encodeToString(encryptedMessage);
        //System.out.println("Зашифрованное сообщение: " + encodedMessage);
        return encodedMessage;
    }

    public void deCrypto(String text)
            throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            IllegalBlockSizeException,
            BadPaddingException, InvalidKeyException {

        // Создание экземпляра Cipher для алгоритма AES
        Cipher cipher = Cipher.getInstance("AES");

        // Инициализация Cipher в режиме дешифрования
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(text));
        String decryptedMessage = new String(decryptedBytes);
        System.out.println("Ключ: " + secretKey);
        System.out.println("Расшифрованное сообщение: " + decryptedMessage);
    }
}
