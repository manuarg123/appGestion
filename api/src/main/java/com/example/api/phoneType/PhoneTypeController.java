package com.example.api.phoneType;

import com.example.api.common.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/phoneTypes")
public class PhoneTypeController {
    @Autowired
    private final PhoneTypeService phoneTypeService;

    public PhoneTypeController(PhoneTypeService phoneTypeService) {
        this.phoneTypeService = phoneTypeService;
    }

    @GetMapping
    public List<PhoneType> getPhoneTypes(){return phoneTypeService.getPhoneTypes();}

    @PostMapping(path="/new")
    public APIResponse addPhoneType(@RequestBody PhoneTypeDTO phoneTypeDTO){
        return this.phoneTypeService.newPhoneType(phoneTypeDTO);
    }

    @PutMapping(path="/edit/{phoneTypeId}")
    public APIResponse editPhoneType(@PathVariable("phoneTypeId") Long id, @RequestBody PhoneTypeDTO phoneTypeDTO){
        return this.phoneTypeService.editPhoneType(id,phoneTypeDTO);
    }

    @DeleteMapping(path="/delete/{phoneTypeId}")
    public APIResponse deletePhoneType(@PathVariable("phoneTypeId") Long id){
        return this.phoneTypeService.deletePhoneType(id);
    }
}
