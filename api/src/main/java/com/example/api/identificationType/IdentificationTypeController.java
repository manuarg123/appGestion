package com.example.api.identificationType;

import com.example.api.common.APIResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping(path="api/identificationTypes")
public class IdentificationTypeController {

    @Autowired
    private final IdentificationTypeService identificationTypeService;

    @Autowired
    public IdentificationTypeController(IdentificationTypeService identificationTypeService){this.identificationTypeService = identificationTypeService;}

    @GetMapping
    public List<IdentificationType> getIdentificationTypes(){
        return identificationTypeService.getIdentificationTypes();
    }

    @GetMapping(path= "/show/{identificationTypeId}")
    public APIResponse getIdentificationType(@PathVariable("identificationTypeId") Long id){
        return this.identificationTypeService.getIdentificationType(id);
    }

    @PostMapping(path = "/new")
    public APIResponse addIdentificationType(@RequestBody @Valid IdentificationTypeDTO identificationTypeDTO){
        return this.identificationTypeService.newIdentificationType(identificationTypeDTO);
    }

    @PutMapping(path = "/edit/{identificationTypeId}")
    public APIResponse editIdentificationType(@PathVariable("identificationTypeId") Long id, @Valid @RequestBody IdentificationTypeDTO identificationTypeDTO){
        return this.identificationTypeService.editIdentificationType(id, identificationTypeDTO);
    }

    @DeleteMapping(path="/delete/{identificationTypeId}")
    public APIResponse deleteIdentificationType(@PathVariable("identificationTypeId") Long id){
        return this.identificationTypeService.deleteIdentificationType(id);
    }

    @GetMapping(path = "/paginated")
    public Page<IdentificationType> getIdentificationTypesPaginated(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize
    ){
        return identificationTypeService.getIdentificationTypesPaginated(currentPage, pageSize);
    }
}
