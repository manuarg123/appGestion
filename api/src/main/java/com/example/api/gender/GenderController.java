package com.example.api.gender;

import com.example.api.common.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/genders")
public class GenderController {
    @Autowired
    private final GenderService genderService;
    public GenderController(GenderService genderService){this.genderService = genderService;}

    @GetMapping
    public List<Gender> getGenders(){return this.genderService.getGenders();}

    @PostMapping(path="/new")
    public APIResponse addGender(@RequestBody GenderDTO genderDTO){
        return this.genderService.newGender(genderDTO);
    }

    @PutMapping(path="/edit/{genderId}")
    public APIResponse editGender(@PathVariable("genderId") Long id, @RequestBody GenderDTO genderDTO){
        return this.genderService.editGender(id, genderDTO);
    }

    @DeleteMapping(path="/delete/{genderId}")
    public APIResponse deleteGender(@PathVariable("genderId") Long id){
        return this.genderService.deleteGender(id);
    }
}
