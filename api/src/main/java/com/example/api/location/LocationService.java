package com.example.api.location;

import com.example.api.common.APIResponse;
import com.example.api.common.MessagesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class LocationService {
    HashMap<String , Object> data;
    private final LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository){this.locationRepository = locationRepository;}

    public List<Location> getLocations(){
        return this.locationRepository.findByDeletedAtIsNull();
    }


    public APIResponse newLocation(LocationDTO locationDTO) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String, Object> data = new HashMap<>();

        Location location = new Location();
        location.setName(locationDTO.getName());
        location.setProvinceId(locationDTO.getProvinceId());

        locationRepository.save(location);
        data.put("message", MessagesResponse.addSuccess);
        data.put("data", location);
        apiResponse.setData(data);
        return apiResponse;
    }

    public APIResponse editLocation(Long id, LocationDTO locationDTO) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String,Object> data = new HashMap<>();

        Optional<Location> optionalLocation = locationRepository.findByIdAndDeletedAtIsNull(id);

        if (optionalLocation.isPresent()) {
            Location existingLocation = optionalLocation.get();
            existingLocation.setName(locationDTO.getName());
            existingLocation.setProvinceId(locationDTO.getProvinceId());

            locationRepository.save(existingLocation);
            data.put("message", MessagesResponse.editSuccess);
            data.put("data", existingLocation);
            apiResponse.setData(data);
        } else {
            data.put("error",true);
            data.put("message", MessagesResponse.recordNotFound);
            apiResponse.setData(data);
        }
        return apiResponse;
    }
    public APIResponse deleteLocation(Long id) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String, Object> data = new HashMap<>();

        boolean exists = this.locationRepository.existsById(id);

        if (!exists){
            data.put("error", true);
            data.put("message", MessagesResponse.recordNotFound);
            apiResponse.setData(data);
            return apiResponse;
        }

        Optional<Location> optionalLocation = locationRepository.findById(id);
        Location existingLocation = optionalLocation.get();
        existingLocation.setDeletedAt(LocalDateTime.now());

        locationRepository.save(existingLocation);
        data.put("message", MessagesResponse.deleteSuccess);
        apiResponse.setData(data);
        return apiResponse;
    }
}
