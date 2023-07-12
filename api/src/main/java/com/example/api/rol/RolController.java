package com.example.api.rol;

import com.example.api.common.APIResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/roles")
public class RolController {
    @Autowired
    private final RolService rolService;
    public RolController(RolService rolService){this.rolService = rolService;}

    @GetMapping
    public List<Rol> getRoles(){return this.rolService.getRoles();}

    @GetMapping(path="/show/{rolId}")
    public APIResponse getRol(@PathVariable("rolId") Long id){return this.rolService.getRol(id);}

    @PostMapping(path="/new")
    public APIResponse addRol(@RequestBody @Valid RolDTO rolDTO){
        return this.rolService.newRol(rolDTO);
    }

    @PutMapping(path="/edit/{rolId}")
    public APIResponse editRol(@PathVariable("rolId") Long id, @RequestBody @Valid RolDTO rolDTO){
        return this.rolService.editRol(id, rolDTO);
    }

    @DeleteMapping(path="/delete/{rolId}")
    public APIResponse deleteRol(@PathVariable("rolId") Long id){
        return this.rolService.deleteRol(id);
    }
}
