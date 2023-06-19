package com.example.api.email;

import com.example.api.common.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/emails")
public class EmailController {
    @Autowired
    private final EmailService emailService;

    public EmailController(EmailService emailService){this.emailService = emailService;}

    @GetMapping
    public List<Email> getEmails(){return this.emailService.getEmails();}

    @PostMapping(path="/new")
    public APIResponse addEmail(@RequestBody EmailDTO emailDTO){
        return this.emailService.newEmail(emailDTO);
    }

    @PutMapping(path="/edit/{emailId}")
    public APIResponse editEmail(@PathVariable("emailId") Long id, @RequestBody EmailDTO emailDTO){
        return this.emailService.editEmail(id, emailDTO);
    }

    @DeleteMapping(path="/delete/{emailId}")
    public APIResponse deleteEmail(@PathVariable("emailId")Long id){
        return this.emailService.deleteEmail(id);
    }
}