package com.example.api.gender;

import com.example.api.common.APIResponse;
import com.example.api.common.MessagesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class GenderService {
    HashMap<String , Object> data;
    private final GenderRepository genderRepository;
    @Autowired
    public GenderService(GenderRepository genderRepository){this.genderRepository = genderRepository;}

    public List<Gender> getGenders() {
        return this.genderRepository.findByDeletedAtIsNull();
    }

    public APIResponse newGender(GenderDTO genderDTO) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String, Object> data = new HashMap<>();

        Optional<Gender> res = genderRepository.findGenderByName(genderDTO.getName());

        if (res.isPresent()){
            data.put("error", true);
            data.put("message", MessagesResponse.recordNameExists);
            apiResponse.setData(data);
            return apiResponse;
        }
        Gender gender = new Gender();
        gender.setName(genderDTO.getName());
        data.put("message", MessagesResponse.addSuccess);

        genderRepository.save(gender);
        data.put("data", gender);
        apiResponse.setData(data);
        return apiResponse;
    }

    public APIResponse editGender(Long id, GenderDTO genderDTO) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String,Object> data = new HashMap<>();

        Optional<Gender> optionalGender = genderRepository.findByIdAndDeletedAtIsNull(id);

        if (optionalGender.isPresent()) {
            Gender existingGender = optionalGender.get();
            existingGender.setName(genderDTO.getName());

            genderRepository.save(existingGender);
            data.put("message", MessagesResponse.editSuccess);
            data.put("data", existingGender);
            apiResponse.setData(data);
        } else {
            data.put("error",true);
            data.put("message", MessagesResponse.recordNotFound);
            apiResponse.setData(data);
        }
        return apiResponse;
    }

    public APIResponse deleteGender(Long id) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String, Object> data = new HashMap<>();

        boolean exists = this.genderRepository.existsById(id);

        if (!exists){
            data.put("error", true);
            data.put("message", MessagesResponse.recordNotFound);
            apiResponse.setData(data);
            return apiResponse;
        }

        Optional<Gender> optionalGender = genderRepository.findById(id);
        Gender existingGender = optionalGender.get();
        existingGender.setDeletedAt(LocalDateTime.now());

        genderRepository.save(existingGender);
        data.put("message", MessagesResponse.deleteSuccess);
        apiResponse.setData(data);
        return apiResponse;
    }
}
