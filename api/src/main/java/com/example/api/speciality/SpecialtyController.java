package com.example.api.speciality;

import com.example.api.common.APIResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/specialities")
public class SpecialtyController {
    private final SpecialtyService specialtyService;
    public SpecialtyController(SpecialtyService specialtyService){this.specialtyService = specialtyService;}

    @GetMapping
    public List<Speciality> getSpecialities(){return specialtyService.getSpecialities();}

    @GetMapping(path="/show/{specialityId}")
    public APIResponse getSpeciality(@PathVariable("specialityId") Long id) { return this.specialtyService.getSpeciality(id);}

    @PostMapping(path="/new")
    public APIResponse addSpeciality(@RequestBody @Valid SpecialtyDTO specialtyDTO){
        return this.specialtyService.newSpeciality(specialtyDTO);
    }

    @PutMapping(path="/edit/{specialityId}")
    public APIResponse editSpeciality(@PathVariable("specialityId") Long id, @RequestBody  @Valid SpecialtyDTO specialtyDTO){
        return this.specialtyService.editSpeciality(id, specialtyDTO);
    }

    @DeleteMapping(path="/delete/{specialityId}")
    public APIResponse deleteSpeciality(@PathVariable("specialityId") Long id){
            return this.specialtyService.deleteSpeciality(id);
    }

    @GetMapping(path = "/paginated")
    public Page<Speciality> getSpecialtiesPaginated(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize
    ){
        return specialtyService.getSpecialitiesPaginated(currentPage, pageSize);
    }
}
