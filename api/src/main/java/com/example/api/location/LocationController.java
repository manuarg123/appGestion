package com.example.api.location;

import com.example.api.common.APIResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/locations")
public class LocationController {
    private final LocationService locationService;
    public LocationController(LocationService locationService){this.locationService = locationService;}

    @GetMapping
    public List<Location> getLocations(){ return this.locationService.getLocations();}

    @PostMapping(path="/new")
    public APIResponse addLocation(@RequestBody LocationDTO locationDTO){
        return this.locationService.newLocation(locationDTO);
    }

    @PutMapping(path="/edit/{locationId}")
    public APIResponse editLocation(@PathVariable("locationId") Long id, @RequestBody LocationDTO locationDTO){
        return this.locationService.editLocation(id,locationDTO);
    }

    @DeleteMapping(path="/delete/{locationId}")
    public APIResponse deleteLocation(@PathVariable("locationId") Long id){
        return this.locationService.deleteLocation(id);
    }
}
