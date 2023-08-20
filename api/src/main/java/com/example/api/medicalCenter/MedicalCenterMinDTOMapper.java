package com.example.api.medicalCenter;

import com.example.api.address.Address;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class MedicalCenterMinDTOMapper implements Function<MedicalCenter, MedicalCenterMinDTO> {

    @Override
    public MedicalCenterMinDTO apply(MedicalCenter medicalCenter) {
        String direction = null;
        if (medicalCenter.getAddresses() != null && !medicalCenter.getAddresses().isEmpty()){
            Address firstAddress = medicalCenter.getAddresses().get(0);
            if (firstAddress != null){
                if (firstAddress.getSection() != null){
                    direction = firstAddress.getStreet() + " " + firstAddress.getNumber() + " - " + firstAddress.getSection();

                }
                direction = firstAddress.getStreet() + " " + firstAddress.getNumber() ;
            }
        }

        return new MedicalCenterMinDTO(
                medicalCenter.getId(),
                medicalCenter.getName(),
                direction
        );
    }
}
