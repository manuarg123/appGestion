package com.example.api.identification;

import com.example.api.common.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/identifications")
public class IdentificationController {
    @Autowired
    private final IdentificationService identificationService;

    public IdentificationController(IdentificationService identificationService){this.identificationService = identificationService;}

    @GetMapping
    public List<Identification> getIdentifications(){return this.identificationService.getIdentifications();}

    @PostMapping(path="/new")
    public APIResponse addIdentification(@RequestBody IdentificationDTO identificationDTO){
        return this.identificationService.newIdentification(identificationDTO);
    }

    @PutMapping(path="/edit/{identificationId}")
    public APIResponse editIdentification(@PathVariable("identificationId") Long id, @RequestBody IdentificationDTO identificationDTO){
        return this.identificationService.editIdentification(id, identificationDTO);
    }

    @DeleteMapping(path="/delete/{identificationId}")
    public APIResponse deleteIdentification(@PathVariable("identificationId")Long id){
        return this.identificationService.deleteIdentification(id);
    }
}