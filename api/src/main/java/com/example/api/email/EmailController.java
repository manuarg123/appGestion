package com.example.api.email;

import com.example.api.common.APIResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping(path="/api/emails")
public class EmailController {
    @Autowired
    private final EmailService emailService;

    public EmailController(EmailService emailService){this.emailService = emailService;}

    @GetMapping(path= "/show/{emailId}")
    public APIResponse getEmail(@PathVariable("emailId") Long id){
        return this.emailService.getEmail(id);
    }
    @GetMapping
    public List<Email> getEmails(){return this.emailService.getEmails();}

    @PostMapping(path="/new")
    public APIResponse addEmail(@RequestBody @Valid EmailDTO emailDTO){
        return this.emailService.newEmail(emailDTO);
    }

    @PutMapping(path="/edit/{emailId}")
    public APIResponse editEmail(@PathVariable("emailId") Long id, @RequestBody @Valid EmailDTO emailDTO){
        return this.emailService.editEmail(id, emailDTO);
    }

    @DeleteMapping(path="/delete/{emailId}")
    public APIResponse deleteEmail(@PathVariable("emailId")Long id){
        return this.emailService.deleteEmail(id);
    }
}
