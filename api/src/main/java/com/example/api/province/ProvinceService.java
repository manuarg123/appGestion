package com.example.api.province;

import com.example.api.common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@Validated
public class ProvinceService {
    HashMap<String, Object> data;
    private final ProvinceRepository provinceRepository;

    @Autowired
    public ProvinceService(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    public List<Province> getProvinces() {
        return this.provinceRepository.findByDeletedAtIsNull();
    }


    public APIResponse newProvince(ProvinceDTO provinceDTO) {
        if (Stream.of(provinceDTO)
                .map(ProvinceDTO::getName)
                .anyMatch(name -> Objects.isNull(name) || name.isBlank() || name.length() > 144)) {
            throw new NotValidException(MessagesResponse.notValidParameters);
        }

        Optional<Province> res = provinceRepository.findProvinceByName(provinceDTO.getName());

        if (res.isPresent()) {
            Province existingProvince = res.get();
            if (existingProvince.getDeletedAt() == null) {
                throw new DuplicateRecordException(MessagesResponse.nameAlreadyExists);
            }
        }

        APIResponse apiResponse = new APIResponse();
        Province province = new Province();
        province.setName(provinceDTO.getName());
        provinceRepository.save(province);

        apiResponse.setData(province);
        apiResponse.setMessage(MessagesResponse.addSuccess);
        apiResponse.setStatus(HttpStatus.CREATED.value());

        return apiResponse;
    }

    public APIResponse editProvince(Long id, ProvinceDTO provinceDTO) {
        if (Stream.of(provinceDTO)
                .map(ProvinceDTO::getName)
                .anyMatch(name -> Objects.isNull(name) || name.isBlank() || name.length() > 144)) {
            throw new NotValidException(MessagesResponse.notValidParameters);
        }

        APIResponse apiResponse = new APIResponse();
        Optional<Province> optionalProvince = provinceRepository.findByIdAndDeletedAtIsNull(id);

        if (optionalProvince.isPresent()) {
            Optional<Province> res = provinceRepository.findProvinceByName(provinceDTO.getName());

            if (res.isPresent()) {
                Province existingProvince = res.get();
                if (existingProvince.getDeletedAt() == null) {
                    throw new DuplicateRecordException(MessagesResponse.nameAlreadyExists);
                }
            }

            Province existingProvince = optionalProvince.get();
            existingProvince.setName(provinceDTO.getName());
            provinceRepository.save(existingProvince);

            apiResponse.setStatus(HttpStatus.OK.value());
            apiResponse.setMessage(MessagesResponse.editSuccess);
            apiResponse.setData(existingProvince);
        } else {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }
        return apiResponse;
    }

    public APIResponse deleteProvince(Long id) {
        APIResponse apiResponse = new APIResponse();
        Optional<Province> optionalProvince = provinceRepository.findByIdAndDeletedAtIsNull(id);

        if (!optionalProvince.isPresent()) {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }

        Province existingProvince = optionalProvince.get();
        existingProvince.setDeletedAt(LocalDateTime.now());
        provinceRepository.save(existingProvince);

        apiResponse.setMessage(MessagesResponse.deleteSuccess);
        apiResponse.setData(existingProvince);
        apiResponse.setStatus(HttpStatus.OK.value());
        return apiResponse;
    }

    public APIResponse getProvince(Long id) {
        Optional<Province> optionalProvince = provinceRepository.findByIdAndDeletedAtIsNull(id);

        if (!optionalProvince.isPresent()) {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }

        APIResponse apiResponse = new APIResponse();
        Province existingProvince = optionalProvince.get();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(existingProvince);
        return apiResponse;
    }
}
