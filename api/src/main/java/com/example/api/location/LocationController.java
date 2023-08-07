package com.example.api.location;

import com.example.api.common.APIResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/locations")
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public List<Location> getLocations() {
        return this.locationService.getLocations();
    }

    @GetMapping(path = "/show/{locationId}")
    public APIResponse getLocation(@PathVariable("locationId") Long id) {
        return this.locationService.getLocation(id);
    }

    @PostMapping(path = "/new")
    public APIResponse addLocation(@RequestBody @Valid LocationDTO locationDTO) {
        return this.locationService.newLocation(locationDTO);
    }

    @PutMapping(path = "/edit/{locationId}")
    public APIResponse editLocation(@PathVariable("locationId") Long id, @RequestBody @Valid LocationDTO locationDTO) {
        return this.locationService.editLocation(id, locationDTO);
    }

    @DeleteMapping(path = "/delete/{locationId}")
    public APIResponse deleteLocation(@PathVariable("locationId") Long id) {
        return this.locationService.deleteLocation(id);
    }

    @GetMapping(path = "/paginated")
    public Page<Location> getLocationsPaginated(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int pageSize
    ){
        return locationService.getLocationsPaginated(currentPage, pageSize);
    }
}
