package com.example.api.patient;

import com.example.api.address.AddressDTO;
import com.example.api.address.AddressDTOMapper;
import com.example.api.address.AddressFormDTO;
import com.example.api.address.AddressFormDTOMapper;
import com.example.api.clinicHistory.ClinicHistoryDTO;
import com.example.api.clinicHistory.ClinicHistoryDTOMapper;
import com.example.api.email.EmailDTO;
import com.example.api.email.EmailDTOMapper;
import com.example.api.email.EmailFormDTO;
import com.example.api.email.EmailFormDTOMapper;
import com.example.api.emergencyContact.EmergencyContact;
import com.example.api.emergencyContact.EmergencyContactDTO;
import com.example.api.emergencyContact.EmergencyContactDTOMapper;
import com.example.api.identification.IdentificationDTO;
import com.example.api.identification.IdentificationDTOMapper;
import com.example.api.identification.IdentificationFormDTO;
import com.example.api.identification.IdentificationFormDTOMapper;
import com.example.api.phone.PhoneDTO;
import com.example.api.phone.PhoneDTOMapper;
import com.example.api.phone.PhoneFormDTO;
import com.example.api.plan.Plan;
import com.example.api.plan.PlanSocialWorkDTO;
import com.example.api.socialWork.SocialWork;
import com.example.api.socialWork.SocialWorkPlanDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientFormDTOMapper implements Function<Patient, PatientFormDTO> {
    private final EmergencyContactDTOMapper emergencyContactDTOMapper;
    private final AddressFormDTOMapper addressFormDTOMapper;
    private final PhoneDTOMapper phoneDTOMapper;
    private final EmailFormDTOMapper emailFormDTOMapper;
    private final IdentificationFormDTOMapper identificationFormDTOMapper;
    private final ClinicHistoryDTOMapper clinicHistoryDTOMapper;

    @Override
    public PatientFormDTO apply(Patient patient) {

        List<PlanSocialWorkDTO> planSocialWorkDTOS = new ArrayList<>();
        List<Long> plansIds = new ArrayList<>();
        if (patient.getPlans() != null && !patient.getPlans().isEmpty()) {
            for (Plan plan : patient.getPlans()) {
                PlanSocialWorkDTO planSocialWorkDTO = new PlanSocialWorkDTO();
                SocialWorkPlanDTO socialWorkPlanDTO = new SocialWorkPlanDTO();

                planSocialWorkDTO.setId(plan.getId());
                planSocialWorkDTO.setName(plan.getName());
                socialWorkPlanDTO.setId(plan.getSocialWork().getId());
                socialWorkPlanDTO.setName(plan.getSocialWork().getName());
                planSocialWorkDTO.setSocialWork(socialWorkPlanDTO);
                planSocialWorkDTO.setCode("Plan" + plan.getName() + plan.getSocialWork().getName() + plan.getId()+ plan.getSocialWork().getId());
                planSocialWorkDTOS.add(planSocialWorkDTO);

                Long planId = plan.getId();
                plansIds.add(planId);
            }
        }

        List<Long> socialWorksIds = new ArrayList<>();
        if (patient.getSocialWorks() != null && !patient.getSocialWorks().isEmpty()) {
            for (SocialWork socialWork : patient.getSocialWorks()) {
                PlanSocialWorkDTO planSocialWorkDTO = new PlanSocialWorkDTO();
                SocialWorkPlanDTO socialWorkPlanDTO = new SocialWorkPlanDTO();

                planSocialWorkDTO.setId(null);
                planSocialWorkDTO.setName("");
                socialWorkPlanDTO.setName(socialWork.getName());
                socialWorkPlanDTO.setId(socialWork.getId());
                planSocialWorkDTO.setSocialWork(socialWorkPlanDTO);
                planSocialWorkDTO.setCode("NotPlan" + socialWork.getName() + socialWork.getId());
                planSocialWorkDTOS.add(planSocialWorkDTO);

                Long socialWorkId = socialWork.getId();
                socialWorksIds.add(socialWorkId);
            }
        }

        List<EmergencyContactDTO> emergencyContactDTOS = patient.getEmergencyContacts()
                .stream()
                .map(emergencyContactDTOMapper::apply)
                .collect(Collectors.toList());

        List<ClinicHistoryDTO> clinicHistoryDTOS = patient.getClinicHistories()
                .stream()
                .map(clinicHistoryDTOMapper::apply)
                .collect(Collectors.toList());

        List<AddressFormDTO> addressDTOS = patient.getAddresses()
                .stream()
                .map(addressFormDTOMapper::apply)
                .collect(Collectors.toList());

        List<PhoneFormDTO> phoneDTOS = patient.getPhones()
                .stream()
                .map(phoneDTOMapper::apply)
                .collect(Collectors.toList());

        List<EmailFormDTO> emailDTOS = patient.getEmails()
                .stream()
                .map(emailFormDTOMapper)
                .collect(Collectors.toList());

        List<IdentificationFormDTO> identificationDTOS = patient.getIdentifications()
                .stream()
                .map(identificationFormDTOMapper::apply)
                .collect(Collectors.toList());

        return new PatientFormDTO(
                patient.getFirstName(),
                patient.getSecondName(),
                patient.getLastName(),
                patient.getSecondLastName(),
                patient.getBirthday(),
                patient.getGender().getId(),
                patient.getGender().getName(),
                patient.getId(),
                patient.isSmoker(),
                patient.getOccupation(),
                patient.getMedicalHistory(),
                socialWorksIds,
                plansIds,
                emergencyContactDTOS,
                clinicHistoryDTOS,
                planSocialWorkDTOS,
                addressDTOS,
                phoneDTOS,
                emailDTOS,
                identificationDTOS
        );
    }
}
