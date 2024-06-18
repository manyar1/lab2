package ru.daniel.EncryptedMessage.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.daniel.EncryptedMessage.model.Message;
import ru.daniel.EncryptedMessage.service.EncryptoService;
import ru.daniel.EncryptedMessage.service.MessageService;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

@Controller
@RequestMapping("/message")
public class EncryptedMessageController {

    private final MessageService messageService;
    private final EncryptoService encryptoService;

    private static ArrayList<String> list;

    private static String rezultMessage;

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("message", messageService.findAll());
        return "message/index";
    }

    @Autowired
    public EncryptedMessageController(MessageService messageService,
                                      EncryptoService encryptoService) {
        this.messageService = messageService;
        this.encryptoService = encryptoService;
    }


    @GetMapping("/add")
    public String newMessage(@ModelAttribute("message") Message message){
        return "message/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("message") @Valid Message message,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "message/new";
        }

        messageService.save(message);
        return "redirect:/message/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("message", messageService.findOne(id));
        return "message/show";
    }

    @GetMapping("/{id}/cipher")
    public String cipher(@PathVariable("id") int id,
                         Model model) throws
            NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {

        Message message = messageService.findOne(id);
        rezultMessage = encryptoService.encrypto(message);
        model.addAttribute("cipher", rezultMessage);
        return "/message/cipher";
    }

    @GetMapping("/decipher")
    public void cipher() throws
            NoSuchPaddingException,
            IllegalBlockSizeException,
            NoSuchAlgorithmException,
            BadPaddingException, InvalidKeyException {
        encryptoService.deCrypto(rezultMessage);
    }
}
