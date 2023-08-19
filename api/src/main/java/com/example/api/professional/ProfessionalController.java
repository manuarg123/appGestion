package com.example.api.professional;

import com.example.api.common.APIResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/professionals")
@AllArgsConstructor
public class ProfessionalController {
    private final ProfessionalService professionalService;

    @GetMapping
    public List<ProfessionalListDTO> getProfessionals() {
        return this.professionalService.getProfessionals();
    }

    @GetMapping(path = "/paginated")
    public Page<ProfessionalListDTO> getProfessionalsPaginated(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize
    ){
        return professionalService.getProfessionalsPaginated(currentPage, pageSize);
    }

    @GetMapping(path = "/show/{professionalId}")
    public APIResponse getProfessional(@PathVariable("professionalId") Long id) {
        return this.professionalService.getProfessional(id);
    }

    @PostMapping(path = "/new")
    public APIResponse newProfessional(@RequestBody @Valid ProfessionalDTO professionalDTO) {
        return this.professionalService.newProfessional(professionalDTO);
    }

    @PutMapping(path = "/edit/{professionalId}")
    public APIResponse editProfessional(@PathVariable("professionalId") Long id, @RequestBody @Valid ProfessionalDTO professionalDTO) {
        return this.professionalService.editProfessional(id, professionalDTO);
    }

    @DeleteMapping(path = {"/delete/{professionalId}"})
    public APIResponse deleteProfessional(@PathVariable("professionalId") Long id) {
        return this.professionalService.deleteProfessional(id);
    }
}
