package com.example.api.medicalCenter;

import com.example.api.common.APIResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/medicalCenters")
public class MedicalCenterController {
    private final MedicalCenterService medicalCenterService;
    public MedicalCenterController(MedicalCenterService medicalCenterService){
        this.medicalCenterService = medicalCenterService;
    }

    @GetMapping
    public List<MedicalCenter> getMedicalCenters(){ return this.medicalCenterService.geMedicalCenters();}

    @GetMapping(path="/show/{medicalCenterId}")
    public APIResponse getMedicalCenter(@PathVariable("medicalCenterId") Long id){
        return this.medicalCenterService.getMedicalCenter(id);
    }

    @PostMapping(path="/new")
    public APIResponse addMedicalCenter(@RequestBody @Valid MedicalCenterDTO medicalCenterDTO){
        return this.medicalCenterService.newMedicalCenter(medicalCenterDTO);
    }
}
