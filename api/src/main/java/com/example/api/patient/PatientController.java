package com.example.api.patient;

import com.example.api.common.APIResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/patients")
@AllArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @GetMapping
    public List<PatientListDTO> getPatients() {
        return this.patientService.getPatients();
    }

    @GetMapping(path = "/paginated")
    public Page<PatientListDTO> getPatientsPaginated(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize
    ){
        return patientService.getPatientsPaginated(currentPage, pageSize);
    }

    @GetMapping(path = "/show/{patientId}")
    public APIResponse getPatient(@PathVariable("patientId") Long id) {
        return this.patientService.getPatient(id);
    }

    @PostMapping(path = "/new")
    public APIResponse newPatient(@RequestBody @Valid PatientDTO patientDTO) {
        return this.patientService.newPatient(patientDTO);
    }

    @PutMapping(path = "/edit/{patientId}")
    public APIResponse editPatient(@PathVariable("patientId") Long id, @RequestBody @Valid PatientDTO patientDTO) {
        return this.patientService.editPatient(id, patientDTO);
    }

    @DeleteMapping(path = "/delete/{patientId}")
    public APIResponse deletePatient(@PathVariable("patientId") Long id) {
        return this.patientService.deletePatient(id);
    }
}
