package com.example.api.province;

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


    public ResponseEntity<Object> newProvince(Province province) {
        Optional<Province> res = provinceRepository.findProvinceByName(province.getName());
        data = new HashMap<>();

        if (res.isPresent() && province.getId() == null){
            data.put("error",true);
            data.put("message", "Ya existe un registro con este nombre");
            return new ResponseEntity<>(data, HttpStatus.CONFLICT);
        }

        data.put("message", "Se guardó con éxito el registro");

        provinceRepository.save(province);
        data.put("data", province);

        return new ResponseEntity<>(data,HttpStatus.CREATED);
    }

    public ResponseEntity<Object> editProvince(Long id, Province province){
        Optional<Province> optionalProvince = provinceRepository.findByIdAndDeletedAtIsNull(id);
        data = new HashMap<>();

        if (optionalProvince.isPresent()){
            Province existingProvince = optionalProvince.get();
            existingProvince.setName(province.getName());

            provinceRepository.save(existingProvince);
            data.put("message", "Registro actualizado exitosamente");
            data.put("data", existingProvince);

            return new ResponseEntity<>(data, HttpStatus.OK);
        } else {
            data.put("error", true);
            data.put("message", "No se encontro un registro con ese id:" + id);
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }

    }

    public ResponseEntity<Object> deleteProvince(Long id) {
        boolean exists = this.provinceRepository.existsById(id);
        data = new HashMap<>();

        if (!exists){
            data.put("error", true);
            data.put("message", "No existe el registro");

            return new ResponseEntity<>(data, HttpStatus.CONFLICT);
        }
        Optional<Province> optionalProvince = provinceRepository.findById(id);
        Province existingProvince = optionalProvince.get();
        existingProvince.setDeletedAt(LocalDateTime.now());

        provinceRepository.save(existingProvince);
        data.put("message", "Registro eliminado");
        return new ResponseEntity<>(data,HttpStatus.OK);
    }
}
