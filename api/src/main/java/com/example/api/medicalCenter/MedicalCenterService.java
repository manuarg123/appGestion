package com.example.api.medicalCenter;

import com.example.api.address.AddressDTO;
import com.example.api.address.AddressService;
import com.example.api.common.APIResponse;
import com.example.api.common.MessagesResponse;
import com.example.api.email.Email;
import com.example.api.email.EmailDTO;
import com.example.api.email.EmailRepository;
import com.example.api.email.EmailService;
import com.example.api.emalType.EmailType;
import com.example.api.emalType.EmailTypeRepository;
import com.example.api.identification.Identification;
import com.example.api.identification.IdentificationDTO;
import com.example.api.identification.IdentificationRepository;
import com.example.api.identification.IdentificationService;
import com.example.api.identificationType.IdentificationType;
import com.example.api.identificationType.IdentificationTypeRepository;
import com.example.api.person.Person;
import com.example.api.person.PersonRepository;
import com.example.api.phone.Phone;
import com.example.api.phone.PhoneDTO;
import com.example.api.phone.PhoneRepository;
import com.example.api.phone.PhoneService;
import com.example.api.phoneType.PhoneType;
import com.example.api.phoneType.PhoneTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class MedicalCenterService {
    private final MedicalCenterRepository medicalCenterRepository;
    private final IdentificationService identificationService;
    private final AddressService addressService;
    private final PhoneService phoneService;
    private final EmailService emailService;

    @Autowired
    public MedicalCenterService(MedicalCenterRepository medicalCenterRepository, AddressService addressService, IdentificationService identificationService, PhoneService phoneService, EmailService emailService){
        this.medicalCenterRepository = medicalCenterRepository;
        this.addressService = addressService;
        this.identificationService = identificationService;
        this.phoneService = phoneService;
        this.emailService = emailService;
    }

    public List<MedicalCenter> geMedicalCenters() {
        return medicalCenterRepository.findByDeletedAtIsNull();
    }

    //Normalizar el Response y los métodos para Phone , email e identification como esta el de Address
    public APIResponse newMedicalCenter(MedicalCenterDTO medicalCenterDTO) {
        APIResponse apiResponse = new APIResponse();
        HashMap<String, Object> data = new HashMap<>();

        MedicalCenter medicalCenter = new MedicalCenter();
        medicalCenter.setName(medicalCenterDTO.getName());
        medicalCenter.setFullName(medicalCenter.getName());

        medicalCenterRepository.save(medicalCenter);
        data.put("message", MessagesResponse.addSuccess);
        data.put("data", medicalCenter);
        apiResponse.setData(data);

        Long medicalCenterId = medicalCenter.getId();

        for (PhoneDTO phoneDTO : medicalCenterDTO.getPhones()){
            HashMap <String, Object> phoneHash = new HashMap<>();
            HashMap phoneData = phoneService.createPhone(phoneDTO, phoneHash, medicalCenterId);
            apiResponse.setData(phoneData);
        }

        for (EmailDTO emailDTO : medicalCenterDTO.getEmails()){
            HashMap <String, Object> emailHash = new HashMap<>();
            HashMap emailData = emailService.createEmail(emailDTO, emailHash, medicalCenterId);
            apiResponse.setData(emailData);
        }

        for (IdentificationDTO identificationDTO : medicalCenterDTO.getIdentifications()){
            HashMap <String, Object> identificationHash = new HashMap<>();
            HashMap identificationData = identificationService.createIdentification(identificationDTO, identificationHash, medicalCenterId);
            apiResponse.setData(identificationData);
        }

        for (AddressDTO addressDTO : medicalCenterDTO.getAddresses()){
            HashMap <String, Object> addressHash = new HashMap<>();
            HashMap addressData = addressService.createAddress(addressDTO,addressHash,medicalCenterId);
            apiResponse.setData(addressData);
        }
        Optional<MedicalCenter> optionalMedicalCenter = medicalCenterRepository.findById(medicalCenterId);
        if (optionalMedicalCenter.isPresent()){
            data.put("message", MessagesResponse.addSuccess);
            data.put("data", optionalMedicalCenter.get());
        }
        apiResponse.setData(data);
        return apiResponse;
    }
}
