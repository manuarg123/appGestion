package com.example.api.speciality;

import com.example.api.common.APIResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/specialities")
public class SpecialtyController {
    private final SpecialtyService specialtyService;
    public SpecialtyController(SpecialtyService specialtyService){this.specialtyService = specialtyService;}

    @GetMapping
    public List<Speciality> getSpecialities(){return specialtyService.getSpecialities();}

    @PostMapping(path="/new")
    public APIResponse addSpeciality(@RequestBody SpecialtyDTO specialtyDTO){
        return this.specialtyService.newSpeciality(specialtyDTO);
    }

    @PutMapping(path="/edit/{specialityId}")
    public APIResponse editSpeciality(@PathVariable("specialityId") Long id, @RequestBody SpecialtyDTO specialtyDTO){
        return this.specialtyService.editSpeciality(id, specialtyDTO);
    }

    @DeleteMapping(path="/delete/{specialityId}")
    public APIResponse deleteSpeciality(@PathVariable("specialityId") Long id){
            return this.specialtyService.deleteSpeciality(id);
    }
}
