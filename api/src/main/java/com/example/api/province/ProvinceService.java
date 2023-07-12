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
    public ProvinceService(ProvinceRepository provinceRepository){this.provinceRepository = provinceRepository;}
    public List<Province> getProvinces() {return this.provinceRepository.findByDeletedAtIsNull();}


    public ResponseEntity<Object> newProvince(ProvinceDTO provinceDTO) {

        if (Stream.of(provinceDTO)
                .map(ProvinceDTO::getName)
                .anyMatch(name -> Objects.isNull(name) || name.isBlank() || name.length() > 55)){
            throw new NotValidException(MessagesResponse.notValidParameters);
        }

        Optional<Province> res = provinceRepository.findProvinceByName(provinceDTO.getName());
        data = new HashMap<>();

        if (res.isPresent()) {
            Province existingProvince = res.get();
            if (existingProvince.getDeletedAt() == null){
                throw new DuplicateRecordException(MessagesResponse.nameAlreadyExists);
            }
        }

        Province province = new Province();
        province.setName(provinceDTO.getName());
        data.put("message", MessagesResponse.addSuccess);

        provinceRepository.save(province);
        data.put("data", province);

        return new ResponseEntity<>(data,HttpStatus.CREATED);
    }

    public ResponseEntity<Object> editProvince(Long id, ProvinceDTO provinceDTO){

        if (Stream.of(provinceDTO)
                .map(ProvinceDTO::getName)
                .anyMatch(name -> Objects.isNull(name) || name.isBlank() || name.length() > 55)){
            throw new NotValidException(MessagesResponse.notValidParameters);
        }

        Optional<Province> optionalProvince = provinceRepository.findByIdAndDeletedAtIsNull(id);
        data = new HashMap<>();

        if (optionalProvince.isPresent()){
            Province existingProvince = optionalProvince.get();
            existingProvince.setName(provinceDTO.getName());

            provinceRepository.save(existingProvince);
            data.put("message", MessagesResponse.editSuccess);
            data.put("data", existingProvince);

            return new ResponseEntity<>(data, HttpStatus.OK);
        } else {
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }

    }

    public ResponseEntity<Object> deleteProvince(Long id) {
        boolean exists = this.provinceRepository.existsById(id);
        data = new HashMap<>();

        if (!exists){
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }
        Optional<Province> optionalProvince = provinceRepository.findById(id);
        Province existingProvince = optionalProvince.get();
        existingProvince.setDeletedAt(LocalDateTime.now());

        provinceRepository.save(existingProvince);
        data.put("message", MessagesResponse.deleteSuccess);
        return new ResponseEntity<>(data,HttpStatus.OK);
    }

    public APIResponse getProvince(Long id) {
        boolean exists = this.provinceRepository.existsById(id);
        if (!exists){
            throw new NotFoundException(MessagesResponse.recordNotFound);
        }

        APIResponse apiResponse = new APIResponse();

        Optional<Province> optionalProvince = provinceRepository.findById(id);
        Province existingProvince = optionalProvince.get();

        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(existingProvince);
        return apiResponse;
    }
}
