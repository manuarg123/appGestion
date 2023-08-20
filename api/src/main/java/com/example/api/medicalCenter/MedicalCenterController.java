package com.example.api.medicalCenter;

import com.example.api.common.APIResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/medicalCenters")
@AllArgsConstructor
public class MedicalCenterController {
    private final MedicalCenterService medicalCenterService;

    @GetMapping
    public List<MedicalCenter> getMedicalCenters() {
        return this.medicalCenterService.geMedicalCenters();
    }

    @GetMapping(path = "/show/{medicalCenterId}")
    public APIResponse getMedicalCenter(@PathVariable("medicalCenterId") Long id) {
        return this.medicalCenterService.getMedicalCenter(id);
    }

    @PostMapping(path = "/new")
    public APIResponse addMedicalCenter(@RequestBody @Valid MedicalCenterDTO medicalCenterDTO) {
        return this.medicalCenterService.newMedicalCenter(medicalCenterDTO);
    }

    @PutMapping(path = "/edit/{medicalCenterId}")
    public APIResponse editMedicalCenter(@PathVariable("medicalCenterId") Long id, @RequestBody @Valid MedicalCenterDTO medicalCenterDTO) {
        return this.medicalCenterService.editMedicalCenter(id, medicalCenterDTO);
    }

    @DeleteMapping(path = "/delete/{medicalCenterId}")
    public APIResponse deleteMedicalCenter(@PathVariable("medicalCenterId") Long id) {
        return this.medicalCenterService.deleteMedicalCenter(id);
    }

    @GetMapping(path = "/paginated")
    public Page<MedicalCenter> getMedicalCentersPaginated(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize
    ){
        return medicalCenterService.getMedicalCentersPaginated(currentPage, pageSize);
    }

    @GetMapping(path = "/minDto/{medicalCenterId}")
    public MedicalCenterMinDTO getMedicalCentersDto(@PathVariable("medicalCenterId") Long id) {
        return this.medicalCenterService.getMedicalCentersDto(id);
    }
}
