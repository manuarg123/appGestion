package com.example.api.address;

import com.example.api.common.APIResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/addresses")
public class AddressController {
    private final AddressService addressService;
    @Autowired
    public AddressController(AddressService addressService){this.addressService = addressService;}

    @GetMapping
    public List<Address> getAddresses(){return this.addressService.getAddresses();}

    @GetMapping(path="/show/{addressId}")
    public APIResponse getAddress(@PathVariable("addressId") Long id){
        return this.addressService.getAddress(id);
    }

    @PostMapping(path="/new")
    public APIResponse addAddress(@RequestBody @Valid AddressDTO addressDTO){
        return this.addressService.newAddress(addressDTO);
    }

    @PutMapping(path="/edit/{addressId}")
    public APIResponse editAddress(@PathVariable("addressId") Long id, @RequestBody @Valid AddressDTO addressDTO){
        return this.addressService.editAddress(id, addressDTO);
    }

    @DeleteMapping(path="/delete/{addressId}")
    public APIResponse deleteAddress(@PathVariable("addressId") Long id){
        return this.addressService.deleteAddress(id);
    }
}
