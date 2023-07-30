package com.example.api.province;

import com.example.api.common.APIResponse;
import com.example.api.medicalCenter.MedicalCenter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping(path="api/provinces")
public class ProvinceController {

    @Autowired
    private final ProvinceService provinceService;

    @Autowired
    public ProvinceController(ProvinceService provinceService){this.provinceService = provinceService;}

    @GetMapping
    public List<Province> getProvinces(){
        return provinceService.getProvinces();
    }

    @GetMapping(path= "/show/{provinceId}")
    public APIResponse getProvince(@PathVariable("provinceId") Long id){
        return this.provinceService.getProvince(id);
    }

    @PostMapping(path = "/new")
    public APIResponse addProvince(@RequestBody @Valid ProvinceDTO provinceDTO){
        return this.provinceService.newProvince(provinceDTO);
    }

    @PutMapping(path = "/edit/{provinceId}")
    public APIResponse editProvince(@PathVariable("provinceId") Long id, @Valid @RequestBody ProvinceDTO provinceDTO){
        return this.provinceService.editProvince(id, provinceDTO);
    }

    @DeleteMapping(path="/delete/{provinceId}")
    public APIResponse deleteProvince(@PathVariable("provinceId") Long id){
        return this.provinceService.deleteProvince(id);
    }

    @GetMapping(path = "/paginated")
    public Page<Province> getProvincesPaginated(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize
    ){
        return provinceService.getProvincesPaginated(currentPage, pageSize);
    }
}
