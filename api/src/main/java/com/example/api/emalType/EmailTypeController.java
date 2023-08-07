package com.example.api.emalType;

import com.example.api.common.APIResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping(path="api/emailTypes")
public class EmailTypeController {

    @Autowired
    private final EmailTypeService emailTypeService;

    @Autowired
    public EmailTypeController(EmailTypeService emailTypeService){this.emailTypeService = emailTypeService;}

    @GetMapping
    public List<EmailType> getEmailTypes(){
        return emailTypeService.getEmailTypes();
    }

    @GetMapping(path= "/show/{emailTypeId}")
    public APIResponse getEmailType(@PathVariable("emailTypeId") Long id){
        return this.emailTypeService.getEmailType(id);
    }

    @PostMapping(path = "/new")
    public APIResponse addEmailType(@RequestBody @Valid EmailTypeDTO emailTypeDTO){
        return this.emailTypeService.newEmailType(emailTypeDTO);
    }

    @PutMapping(path = "/edit/{emailTypeId}")
    public APIResponse editEmailType(@PathVariable("emailTypeId") Long id, @Valid @RequestBody EmailTypeDTO emailTypeDTO){
        return this.emailTypeService.editEmailType(id, emailTypeDTO);
    }

    @DeleteMapping(path="/delete/{emailTypeId}")
    public APIResponse deleteEmailType(@PathVariable("emailTypeId") Long id){
        return this.emailTypeService.deleteEmailType(id);
    }

    @GetMapping(path = "/paginated")
    public Page<EmailType> getEmailTypesPaginated(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize
    ){
        return emailTypeService.getEmailTypesPaginated(currentPage, pageSize);
    }
}
