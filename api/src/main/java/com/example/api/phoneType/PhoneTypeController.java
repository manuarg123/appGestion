package com.example.api.phoneType;

import com.example.api.common.APIResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping(path="api/phoneTypes")
public class PhoneTypeController {

    @Autowired
    private final PhoneTypeService phoneTypeService;

    @Autowired
    public PhoneTypeController(PhoneTypeService phoneTypeService){this.phoneTypeService = phoneTypeService;}

    @GetMapping
    public List<PhoneType> getPhoneTypes(){
        return phoneTypeService.getPhoneTypes();
    }

    @GetMapping(path= "/show/{phoneTypeId}")
    public APIResponse getPhoneType(@PathVariable("phoneTypeId") Long id){
        return this.phoneTypeService.getPhoneType(id);
    }

    @PostMapping(path = "/new")
    public APIResponse addPhoneType(@RequestBody @Valid PhoneTypeDTO phoneTypeDTO){
        return this.phoneTypeService.newPhoneType(phoneTypeDTO);
    }

    @PutMapping(path = "/edit/{phoneTypeId}")
    public APIResponse editPhoneType(@PathVariable("phoneTypeId") Long id, @Valid @RequestBody PhoneTypeDTO phoneTypeDTO){
        return this.phoneTypeService.editPhoneType(id, phoneTypeDTO);
    }

    @DeleteMapping(path="/delete/{phoneTypeId}")
    public APIResponse deletePhoneType(@PathVariable("phoneTypeId") Long id){
        return this.phoneTypeService.deletePhoneType(id);
    }
}
