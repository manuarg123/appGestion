package com.example.api.location;

import com.example.api.common.APIResponse;
import com.example.api.common.MessagesResponse;
import com.example.api.common.NotFoundException;
import com.example.api.common.NotValidException;
import com.example.api.province.Province;
import com.example.api.province.ProvinceDTO;
import com.example.api.province.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class LocationService {
    HashMap<String, Object> data;
    private final LocationRepository locationRepository;
    private final ProvinceRepository provinceRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository, ProvinceRepository provinceRepository) {
        this.locationRepository = locationRepository;
        this.provinceRepository = provinceRepository;
    }

    public List<Location> getLocations() {
        return this.locationRepository.findByDeletedAtIsNull();
    }


    public APIResponse newLocation(LocationDTO locationDTO) {
        validateLocation(locationDTO);
        APIResponse apiResponse = new APIResponse();

        Optional<Province> optionalProvince = findProvince(locationDTO.getProvinceId());
        Province existingProvince = optionalProvince.get();

        Location location = new Location();
        location.setName(locationDTO.getName());
        location.setProvince(existingProvince);

        locationRepository.save(location);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(location);
        apiResponse.setMessage(MessagesResponse.addSuccess);
        return apiResponse;
    }

    public APIResponse editLocation(Long id, LocationDTO locationDTO) {
        validateLocation(locationDTO);
        APIResponse apiResponse = new APIResponse();

        Optional<Location> optionalLocation = findLocation(id);
        Location existingLocation = optionalLocation.get();

        Optional<Province> optionalProvince = findProvince(locationDTO.getProvinceId());
        Province existingProvince = optionalProvince.get();

        existingLocation.setName(locationDTO.getName());
        existingLocation.setProvince(existingProvince);

        locationRepository.save(existingLocation);
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(existingLocation);
        apiResponse.setMessage(MessagesResponse.editSuccess);
        return apiResponse;
    }

    public APIResponse deleteLocation(Long id) {
        APIResponse apiResponse = new APIResponse();
        Optional<Location> optionalLocation = findLocation(id);

        Location existingLocation = optionalLocation.get();
        existingLocation.setDeletedAt(LocalDateTime.now());
        locationRepository.save(existingLocation);

        apiResponse.setMessage(MessagesResponse.deleteSuccess);
        apiResponse.setData(existingLocation);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    public APIResponse getLocation(Long id) {
        Optional<Location> optionalLocation = locationRepository.findByIdAndDeletedAtIsNull(id);

        if (!optionalLocation.isPresent()) {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }

        APIResponse apiResponse = new APIResponse();
        Location existingLocation = optionalLocation.get();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(existingLocation);
        return apiResponse;
    }

    Optional<Province> findProvince(Long id) {
        Optional<Province> optionalProvince = provinceRepository.findByIdAndDeletedAtIsNull(id);
        if (!optionalProvince.isPresent()) {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }
        return optionalProvince;
    }

    Optional<Location> findLocation(Long id) {
        Optional<Location> optionalLocation = locationRepository.findByIdAndDeletedAtIsNull(id);
        if (!optionalLocation.isPresent()) {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }
        return optionalLocation;
    }

    public void validateLocation(LocationDTO locationDTO) {
        if (Stream.of(locationDTO)
                .map(LocationDTO::getName)
                .anyMatch(name -> name == null || name.isBlank() || name.length() > 144)) {
            throw new NotValidException(MessagesResponse.notValidParameters);
        }

        if (Stream.of(locationDTO)
                .map(LocationDTO::getProvinceId)
                .anyMatch(provinceId -> provinceId == null || String.valueOf(provinceId).isBlank())) {
            throw new NotValidException(MessagesResponse.notValidParameters);
        }
    }

    public Page<Location> getLocationsPaginated(int currentPage, int pageSize){
        int startIndex = (currentPage - 1) * pageSize;
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        return locationRepository.findPageByDeletedAtIsNull(pageable);
    }
}
