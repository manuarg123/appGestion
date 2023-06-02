package com.example.api.province;

import com.example.api.common.MessagesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ProvinceService {
    HashMap<String, Object> data;
    private final ProvinceRepository provinceRepository;

    @Autowired
    public ProvinceService(ProvinceRepository provinceRepository){this.provinceRepository = provinceRepository;}
    public List<Province> getProvinces() {return this.provinceRepository.findByDeletedAtIsNull();}


    public ResponseEntity<Object> newProvince(ProvinceDTO provinceDTO) {
        Optional<Province> res = provinceRepository.findProvinceByName(provinceDTO.getName());
        data = new HashMap<>();

        if (res.isPresent()){
            data.put("error",true);
            data.put("message", MessagesResponse.recordNameExists);
            return new ResponseEntity<>(data, HttpStatus.CONFLICT);
        }

        Province province = new Province();
        province.setName(provinceDTO.getName());
        data.put("message", MessagesResponse.addSuccess);

        provinceRepository.save(province);
        data.put("data", province);

        return new ResponseEntity<>(data,HttpStatus.CREATED);
    }

    public ResponseEntity<Object> editProvince(Long id, ProvinceDTO provinceDTO){
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
            data.put("error", true);
            data.put("message", MessagesResponse.recordNotFound);
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }

    }

    public ResponseEntity<Object> deleteProvince(Long id) {
        boolean exists = this.provinceRepository.existsById(id);
        data = new HashMap<>();

        if (!exists){
            data.put("error", true);
            data.put("message", MessagesResponse.recordNotFound);

            return new ResponseEntity<>(data, HttpStatus.CONFLICT);
        }
        Optional<Province> optionalProvince = provinceRepository.findById(id);
        Province existingProvince = optionalProvince.get();
        existingProvince.setDeletedAt(LocalDateTime.now());

        provinceRepository.save(existingProvince);
        data.put("message", MessagesResponse.deleteSuccess);
        return new ResponseEntity<>(data,HttpStatus.OK);
    }
}
