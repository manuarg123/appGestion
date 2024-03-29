package com.example.api.phone;

import com.example.api.common.APIResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping(path="/api/phones")
public class PhoneController {
    @Autowired
    private final PhoneService phoneService;

    public PhoneController(PhoneService phoneService){this.phoneService = phoneService;}

    @GetMapping(path= "/show/{phoneId}")
    public APIResponse getPhone(@PathVariable("phoneId") Long id){
        return this.phoneService.getPhone(id);
    }
    @GetMapping
    public List<Phone> getPhones(){return this.phoneService.getPhones();}

    @PostMapping(path="/new")
    public APIResponse addPhone(@RequestBody @Valid PhoneDTO phoneDTO){
        return this.phoneService.newPhone(phoneDTO);
    }

    @PutMapping(path="/edit/{phoneId}")
    public APIResponse editPhone(@PathVariable("phoneId") Long id, @RequestBody @Valid PhoneDTO phoneDTO){
        return this.phoneService.editPhone(id, phoneDTO);
    }

    @DeleteMapping(path="/delete/{phoneId}")
    public APIResponse deletePhone(@PathVariable("phoneId")Long id){
        return this.phoneService.deletePhone(id);
    }
}
