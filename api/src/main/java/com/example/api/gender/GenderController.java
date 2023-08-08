package com.example.api.gender;

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
@RequestMapping(path="api/genders")
public class GenderController {

    @Autowired
    private final GenderService genderService;

    @Autowired
    public GenderController(GenderService genderService){this.genderService = genderService;}

    @GetMapping
    public List<Gender> getGenders(){
        return genderService.getGenders();
    }

    @GetMapping(path= "/show/{genderId}")
    public APIResponse getGender(@PathVariable("genderId") Long id){
        return this.genderService.getGender(id);
    }

    @PostMapping(path = "/new")
    public APIResponse addGender(@RequestBody @Valid GenderDTO genderDTO){
        return this.genderService.newGender(genderDTO);
    }

    @PutMapping(path = "/edit/{genderId}")
    public APIResponse editGender(@PathVariable("genderId") Long id, @Valid @RequestBody GenderDTO genderDTO){
        return this.genderService.editGender(id, genderDTO);
    }

    @DeleteMapping(path="/delete/{genderId}")
    public APIResponse deleteGender(@PathVariable("genderId") Long id){
        return this.genderService.deleteGender(id);
    }

    @GetMapping(path = "/paginated")
    public Page<Gender> getGendersPaginated(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize
    ){
        return genderService.getGendersPaginated(currentPage, pageSize);
    }
}
