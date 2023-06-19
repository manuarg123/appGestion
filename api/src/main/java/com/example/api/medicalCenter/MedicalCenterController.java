package com.example.api.medicalCenter;

import com.example.api.common.APIResponse;
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

    @PostMapping(path="/new")
    public APIResponse addMedicalCenter(@RequestBody MedicalCenterDTO medicalCenterDTO){
        return this.medicalCenterService.newMedicalCenter(medicalCenterDTO);
    }
}
