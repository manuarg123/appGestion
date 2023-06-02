package com.example.api.province;

import com.example.api.dto.RequestMeta;
import com.example.api.utils.TokenHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/provinces")
public class ProvinceController {

    @Autowired
    private final ProvinceService provinceService;

    @Autowired
    private RequestMeta requestMeta;

    @Autowired
    public ProvinceController(ProvinceService provinceService){this.provinceService = provinceService;}

    @GetMapping
    public List<Province> getProvinces(){
        return provinceService.getProvinces();
    }

    @PostMapping(path = "/new")
    public ResponseEntity<Object>addProvince(@RequestBody Province province){
        return this.provinceService.newProvince(province);
    }

    @PutMapping(path = "/edit/{provinceId}")
    public ResponseEntity<Object>editProvince(@PathVariable("provinceId") Long id, @RequestBody Province province){
        return this.provinceService.editProvince(id, province);
    }

    @DeleteMapping(path="/delete/{provinceId}")
    public ResponseEntity<Object>deleteProvince(@PathVariable("provinceId") Long id){
        return this.provinceService.deleteProvince(id);
    }
}
