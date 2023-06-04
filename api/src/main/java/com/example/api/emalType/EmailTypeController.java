package com.example.api.emalType;

import com.example.api.common.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/emailTypes")
public class EmailTypeController {
    @Autowired
    private final EmailTypeService emailTypeService;

    public EmailTypeController(EmailTypeService emailTypeService){this.emailTypeService = emailTypeService;}

    @GetMapping
    public List<EmailType> getEmailTypes(){return emailTypeService.getEmailTypes();}

    @PostMapping(path="/new")
    public APIResponse addEmailType(@RequestBody EmailTypeDTO emailTypeDTO){
        return this.emailTypeService.newEmailType(emailTypeDTO);
    }

    @PutMapping(path="/edit/{emailTypeId}")
    public APIResponse editEmailType(@PathVariable("emailTypeId") Long id, @RequestBody EmailTypeDTO emailTypeDTO){
        return this.emailTypeService.editEmailType(id,emailTypeDTO);
    }

    @DeleteMapping(path="/delete/{emailTypeId}")
    public APIResponse deletePhoneType(@PathVariable("emailTypeId") Long id){
        return this.emailTypeService.deleteEmailType(id);
    }
}
