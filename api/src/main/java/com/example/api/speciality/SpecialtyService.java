package com.example.api.speciality;

import com.example.api.common.APIResponse;
import com.example.api.common.MessagesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class SpecialtyService {
    private final SpecialtyRepository specialtyRepository;
    @Autowired
    public SpecialtyService(SpecialtyRepository specialtyRepository){this.specialtyRepository = specialtyRepository;}

    public List<Speciality> getSpecialities() {
        return this.specialtyRepository.findByDeletedAtIsNull();
    }

    public APIResponse newSpeciality(SpecialtyDTO specialtyDTO) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String, Object> data = new HashMap<>();

        Optional<Speciality> res = specialtyRepository.findSpecialityByName(specialtyDTO.getName());

        if (res.isPresent()){
            data.put("error", true);
            data.put("message", MessagesResponse.recordNameExists);
            apiResponse.setData(data);
            return apiResponse;
        }

        Speciality speciality = new Speciality();
        speciality.setName(specialtyDTO.getName());
        data.put("message", MessagesResponse.addSuccess);

        specialtyRepository.save(speciality);
        data.put("data", speciality);
        apiResponse.setData(data);
        return apiResponse;
    }

    public APIResponse editSpeciality(Long id, SpecialtyDTO specialtyDTO) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String,Object> data = new HashMap<>();

        Optional<Speciality> optionalSpeciality = specialtyRepository.findByIdAndDeletedAtIsNull(id);

        if (optionalSpeciality.isPresent()){
            Speciality existingSpeciality = optionalSpeciality.get();
            existingSpeciality.setName(specialtyDTO.getName());

            specialtyRepository.save(existingSpeciality);
            data.put("message", MessagesResponse.editSuccess);
            data.put("data", existingSpeciality);
            apiResponse.setData(data);

        } else {
            data.put("error",true);
            data.put("message", MessagesResponse.recordNotFound);
            apiResponse.setData(data);
        }
        return apiResponse;
    }


    public APIResponse deleteSpeciality(Long id) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String, Object> data = new HashMap<>();

        boolean exists = this.specialtyRepository.existsById(id);

        if (!exists){
            data.put("error", true);
            data.put("message", MessagesResponse.recordNotFound);
            apiResponse.setData(data);
            return apiResponse;
        }

        Optional<Speciality> optionalSpeciality = specialtyRepository.findById(id);
        Speciality existingSpeciality = optionalSpeciality.get();
        existingSpeciality.setDeletedAt(LocalDateTime.now());

        specialtyRepository.save(existingSpeciality);
        data.put("message", MessagesResponse.deleteSuccess);
        apiResponse.setData(data);
        return apiResponse;
    }
}
