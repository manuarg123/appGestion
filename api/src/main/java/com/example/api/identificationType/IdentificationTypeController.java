package com.example.api.identificationType;

import com.example.api.common.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/identificationTypes")
public class IdentificationTypeController {
    @Autowired
    private final IdentificationTypeService identificationTypeService;

    public IdentificationTypeController(IdentificationTypeService identificationTypeService){this.identificationTypeService = identificationTypeService;}

    @GetMapping
    public List<IdentificationType> getIdentificationTypes(){return identificationTypeService.getIdentificationTypes();}

    @PostMapping(path="/new")
    public APIResponse addIdentificationtype(@RequestBody IdentificationTypeDTO identificationTypeDTO){
        return this.identificationTypeService.newIdentificationType(identificationTypeDTO);
    }

    @PutMapping(path="/edit/{identificationTypeId}")
    public APIResponse editIdentificationType(@PathVariable("identificationTypeId") Long id, @RequestBody IdentificationTypeDTO identificationTypeDTO){
        return this.identificationTypeService.editIdentificationType(id, identificationTypeDTO);
    }

    @DeleteMapping(path="/delete/{identificationTypeId}")
    public APIResponse deleteIdentificationType(@PathVariable("identificationTypeId") Long id){
        return this.identificationTypeService.deleteIdentificationType(id);
    }
}
