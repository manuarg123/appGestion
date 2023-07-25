package com.example.api.medicalCenter;

import com.example.api.address.Address;
import com.example.api.address.AddressDTO;
import com.example.api.address.AddressService;
import com.example.api.common.*;
import com.example.api.email.Email;
import com.example.api.email.EmailDTO;
import com.example.api.email.EmailService;
import com.example.api.emalType.EmailType;
import com.example.api.identification.Identification;
import com.example.api.identification.IdentificationDTO;
import com.example.api.identification.IdentificationService;
import com.example.api.identificationType.IdentificationType;
import com.example.api.location.Location;
import com.example.api.phone.Phone;
import com.example.api.phone.PhoneDTO;
import com.example.api.phone.PhoneService;
import com.example.api.phoneType.PhoneType;
import com.example.api.province.Province;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import static org.mockito.ArgumentMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MedicalCenterServiceTest {
    @Mock
    private MedicalCenterRepository medicalCenterRepository;

    @Mock
    private AddressService addressService;

    @Mock
    private IdentificationService identificationService;

    @Mock
    private PhoneService phoneService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private MedicalCenterService medicalCenterService;

    private MedicalCenter medicalCenterReinaFabiola;
    private Email emailReinaFabiola1;
    private Email emailReinaFabiola2;
    private List<Email> emailReinaFabiolaList;
    private Phone phoneReinaFabiola1;
    private Phone phoneReinaFabiola2;
    private List<Phone> phoneReinaFabiolaList;
    private Address addressReinaFabiola1;
    private List<Address> addresReinaFabiolaList;
    private Identification identificationReinaFabiola1;
    private Identification identificationReinaFabiola2;
    private List<Identification> identificationReinaFabiolaList;

    private MedicalCenter medicalCenterHospitalPrivado;
    private Email emailHospitalPrivado1;
    private Email emailHospitalPrivado2;
    private List<Email> emailHospitalPrivadoList;
    private Phone phoneHospitalPrivado1;
    private Phone phoneHospitalPrivado2;
    private List<Phone> phoneHospitalPrivadoList;
    private Address addressHospitalPrivado1;
    private List<Address> addressHospitalPrivadoList;
    private Identification identificationHospitalPrivado1;
    private Identification identificationHospitalPrivado2;
    private List<Identification> identificationHospitalPrivadoList;
    private MedicalCenterDTO medicalCenterDTOReinaFabiola;
    private EmailDTO emailDTOReinaFabiola1;
    private EmailDTO emailDTOReinaFabiola2;
    private List<EmailDTO> emailDTOReinaFabiolaList;
    private PhoneDTO phoneDTOReinaFabiola1;
    private PhoneDTO phoneDTOReinaFabiola2;
    private List<PhoneDTO> phoneDTOReinaFabiolaList;
    private IdentificationDTO identificationDTOReinaFabiola1;
    private IdentificationDTO identificationDTOReinaFabiola2;
    private List<IdentificationDTO> identificationDTOReinaFabiolaList;
    private AddressDTO addressDTOReinaFabiola1;
    private List<AddressDTO> addressDTOReinaFabiolaList;

    private MedicalCenterDTO medicalCenterDTOHospitalPrivado;
    private EmailDTO emailDTOHospitalPrivado1;
    private EmailDTO emailDTOHospitalPrivado2;
    private List<EmailDTO> emailDTOHospitalPrivadoList;
    private PhoneDTO phoneDTOHospitalPrivado1;
    private PhoneDTO phoneDTOHospitalPrivado2;
    private List<PhoneDTO> phoneDTOHospitalPrivadoList;
    private IdentificationDTO identificationDTOHospitalPrivado1;
    private IdentificationDTO identificationDTOHospitalPrivado2;
    private List<IdentificationDTO> identificationDTOHospitalPrivadoList;
    private AddressDTO addressDTOHospitalPrivado1;
    private List<AddressDTO> addressDTOHospitalPrivadoList;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.medicalCenterReinaFabiola = new MedicalCenter();
        this.medicalCenterReinaFabiola.setName("Reina Fabiola");

        this.medicalCenterHospitalPrivado = new MedicalCenter();
        this.medicalCenterHospitalPrivado.setName("Hospital Privado");

        this.emailReinaFabiola1 = new Email(1L,"reinafabiola1@gmail.com", this.medicalCenterReinaFabiola, new EmailType(1L, "Corporativo"));
        this.emailReinaFabiola2 = new Email(2L,"reinafabiola2@gmail.com", this.medicalCenterReinaFabiola, new EmailType(2L, "Personal"));
        this.emailReinaFabiolaList = Arrays.asList(emailReinaFabiola1, emailReinaFabiola2);

        this.phoneReinaFabiola1 = new Phone(1L,"1234334222", this.medicalCenterReinaFabiola, new PhoneType(1L, "Corporativo"));
        this.phoneReinaFabiola2 = new Phone(2L,"122332334", this.medicalCenterReinaFabiola, new PhoneType(2L, "Personal"));
        this.phoneReinaFabiolaList = Arrays.asList(phoneReinaFabiola1, phoneReinaFabiola2);

        this.identificationReinaFabiola1 = new Identification(1L, "33232334423", new IdentificationType(1L, "CUIT") , this.medicalCenterReinaFabiola);
        this.identificationReinaFabiola2 = new Identification(2L, "333444232", new IdentificationType(2L, "DNI"), this.medicalCenterReinaFabiola);
        this.identificationReinaFabiolaList = Arrays.asList(identificationReinaFabiola1, identificationReinaFabiola2);

        this.addressReinaFabiola1 = new Address(1L, "Duarte Quiros", "Alto Alberdi", "2332", "", "", "" ,"5000", "Duarte Quiros 2332 - Alto Alberdi", this.medicalCenterReinaFabiola, new Location(1L, "Córdoba", new Province(1L, "Córdoba")), new Province(1L, "Córdoba"));
        this.addresReinaFabiolaList = Arrays.asList(addressReinaFabiola1);

        this.emailHospitalPrivado1 = new Email(3L, "hospitalprivado1@gmail.com", this.medicalCenterHospitalPrivado, new EmailType(1L, "Corporativo"));
        this.emailHospitalPrivado2 = new Email(4L, "hospitalprivado2@gmail.com", this.medicalCenterHospitalPrivado, new EmailType(2L, "Personal"));
        this.emailHospitalPrivadoList = Arrays.asList(emailHospitalPrivado1, emailHospitalPrivado2);

        this.phoneHospitalPrivado1 = new Phone(3L, "33454432445", this.medicalCenterHospitalPrivado, new PhoneType(1L, "Corporativo"));
        this.phoneHospitalPrivado2 = new Phone(4L, "455654433", this.medicalCenterHospitalPrivado, new PhoneType(2L, "Personal"));
        this.phoneHospitalPrivadoList = Arrays.asList(phoneHospitalPrivado1, phoneHospitalPrivado2);

        this.identificationHospitalPrivado1 = new Identification(3L, "454654654", new IdentificationType(1L, "CUIT") , this.medicalCenterHospitalPrivado);
        this.identificationHospitalPrivado2 = new Identification(4L, "234345454", new IdentificationType(2L, "DNI") , this.medicalCenterHospitalPrivado);
        this.identificationHospitalPrivadoList = Arrays.asList(identificationHospitalPrivado1, identificationHospitalPrivado2);

        this.addressHospitalPrivado1 = new Address(1L, "Velez Sarsfield", "Nueva Córdoba", "1923", "", "", "" ,"5000", "Velez Sarsfield 1923 - Nueva Córdoba", this.medicalCenterHospitalPrivado, new Location(1L, "Córdoba", new Province(1L, "Córdoba")), new Province(1L, "Córdoba"));
        this.addressHospitalPrivadoList = Arrays.asList(addressHospitalPrivado1);


        this.medicalCenterReinaFabiola.setFullName(medicalCenterReinaFabiola.getName());
        this.medicalCenterReinaFabiola.setEmails(emailReinaFabiolaList);
        this.medicalCenterReinaFabiola.setAddresses(addresReinaFabiolaList);
        this.medicalCenterReinaFabiola.setPhones(phoneReinaFabiolaList);
        this.medicalCenterReinaFabiola.setIdentifications(identificationReinaFabiolaList);

        this.medicalCenterHospitalPrivado.setFullName(this.medicalCenterHospitalPrivado.getName());
        this.medicalCenterHospitalPrivado.setEmails(emailHospitalPrivadoList);
        this.medicalCenterHospitalPrivado.setAddresses(addressHospitalPrivadoList);
        this.medicalCenterHospitalPrivado.setPhones(phoneHospitalPrivadoList);
        this.medicalCenterHospitalPrivado.setIdentifications(identificationHospitalPrivadoList);

        this.emailDTOReinaFabiola1 = new EmailDTO();
        this.emailDTOReinaFabiola1.setPersonId(emailReinaFabiola1.getPerson().getId());
        this.emailDTOReinaFabiola1.setId(emailReinaFabiola1.getId());
        this.emailDTOReinaFabiola1.setValue(emailReinaFabiola1.getValue());
        this.emailDTOReinaFabiola1.setTypeId(emailReinaFabiola1.getType().getId());

        this.emailDTOReinaFabiola2 = new EmailDTO();
        this.emailDTOReinaFabiola2.setPersonId(emailReinaFabiola2.getPerson().getId());
        this.emailDTOReinaFabiola2.setId(emailReinaFabiola2.getId());
        this.emailDTOReinaFabiola2.setValue(emailReinaFabiola2.getValue());
        this.emailDTOReinaFabiola2.setTypeId(emailReinaFabiola2.getType().getId());

        this.phoneDTOReinaFabiola1 = new PhoneDTO();
        this.phoneDTOReinaFabiola1.setPersonId(phoneReinaFabiola1.getPerson().getId());
        this.phoneDTOReinaFabiola1.setId(phoneReinaFabiola1.getId());
        this.phoneDTOReinaFabiola1.setValue(phoneReinaFabiola1.getNumber());
        this.phoneDTOReinaFabiola1.setTypeId(phoneReinaFabiola1.getType().getId());

        this.phoneDTOReinaFabiola2 = new PhoneDTO();
        this.phoneDTOReinaFabiola2.setPersonId(phoneReinaFabiola2.getPerson().getId());
        this.phoneDTOReinaFabiola2.setId(phoneReinaFabiola2.getId());
        this.phoneDTOReinaFabiola2.setValue(phoneReinaFabiola2.getNumber());
        this.phoneDTOReinaFabiola2.setTypeId(phoneReinaFabiola2.getType().getId());

        this.identificationDTOReinaFabiola1 = new IdentificationDTO();
        this.identificationDTOReinaFabiola1.setPersonId(identificationReinaFabiola1.getPerson().getId());
        this.identificationDTOReinaFabiola1.setId(identificationReinaFabiola1.getId());
        this.identificationDTOReinaFabiola1.setValue(identificationReinaFabiola1.getNumber());
        this.identificationDTOReinaFabiola1.setTypeId(identificationReinaFabiola1.getType().getId());

        this.identificationDTOReinaFabiola2 = new IdentificationDTO();
        this.identificationDTOReinaFabiola2.setPersonId(identificationReinaFabiola2.getPerson().getId());
        this.identificationDTOReinaFabiola2.setId(identificationReinaFabiola2.getId());
        this.identificationDTOReinaFabiola2.setValue(identificationReinaFabiola2.getNumber());
        this.identificationDTOReinaFabiola2.setTypeId(identificationReinaFabiola2.getType().getId());

        this.addressDTOReinaFabiola1 = new AddressDTO();
        this.addressDTOReinaFabiola1.setPersonId(addressReinaFabiola1.getPerson().getId());
        this.addressDTOReinaFabiola1.setId(addressReinaFabiola1.getId());
        this.addressDTOReinaFabiola1.setComplete_address(addressReinaFabiola1.getComplete_address());
        this.addressDTOReinaFabiola1.setFloor(addressReinaFabiola1.getFloor());
        this.addressDTOReinaFabiola1.setApartment(addressReinaFabiola1.getApartment());
        this.addressDTOReinaFabiola1.setNumber(addressReinaFabiola1.getNumber());
        this.addressDTOReinaFabiola1.setZip(addressReinaFabiola1.getZip());
        this.addressDTOReinaFabiola1.setSection(addressReinaFabiola1.getSection());
        this.addressDTOReinaFabiola1.setStreet(addressReinaFabiola1.getStreet());
        this.addressDTOReinaFabiola1.setLocationId(addressReinaFabiola1.getLocation().getId());
        this.addressDTOReinaFabiola1.setProvinceId(addressReinaFabiola1.getProvince().getId());

        this.emailDTOReinaFabiolaList = Arrays.asList(emailDTOReinaFabiola1, emailDTOReinaFabiola2);
        this.phoneDTOReinaFabiolaList = Arrays.asList(phoneDTOReinaFabiola1, phoneDTOReinaFabiola2);
        this.identificationDTOReinaFabiolaList = Arrays.asList(identificationDTOReinaFabiola1, identificationDTOReinaFabiola2);
        this.addressDTOReinaFabiolaList = Arrays.asList(addressDTOReinaFabiola1);

        this.emailDTOHospitalPrivado1 = new EmailDTO();
        this.emailDTOHospitalPrivado1.setPersonId(emailHospitalPrivado1.getPerson().getId());
        this.emailDTOHospitalPrivado1.setId(emailHospitalPrivado1.getId());
        this.emailDTOHospitalPrivado1.setValue(emailHospitalPrivado1.getValue());
        this.emailDTOHospitalPrivado1.setTypeId(emailHospitalPrivado1.getType().getId());

        this.emailDTOHospitalPrivado2 = new EmailDTO();
        this.emailDTOHospitalPrivado2.setPersonId(emailHospitalPrivado2.getPerson().getId());
        this.emailDTOHospitalPrivado2.setId(emailHospitalPrivado2.getId());
        this.emailDTOHospitalPrivado2.setValue(emailHospitalPrivado2.getValue());
        this.emailDTOHospitalPrivado2.setTypeId(emailHospitalPrivado2.getType().getId());

        this.phoneDTOHospitalPrivado1 = new PhoneDTO();
        this.phoneDTOHospitalPrivado1.setPersonId(phoneHospitalPrivado1.getPerson().getId());
        this.phoneDTOHospitalPrivado1.setId(phoneHospitalPrivado1.getId());
        this.phoneDTOHospitalPrivado1.setValue(phoneHospitalPrivado1.getNumber());
        this.phoneDTOHospitalPrivado1.setTypeId(phoneHospitalPrivado1.getType().getId());

        this.phoneDTOHospitalPrivado2 = new PhoneDTO();
        this.phoneDTOHospitalPrivado2.setPersonId(phoneHospitalPrivado2.getPerson().getId());
        this.phoneDTOHospitalPrivado2.setId(phoneHospitalPrivado2.getId());
        this.phoneDTOHospitalPrivado2.setValue(phoneHospitalPrivado2.getNumber());
        this.phoneDTOHospitalPrivado2.setTypeId(phoneHospitalPrivado2.getType().getId());

        this.identificationDTOHospitalPrivado1 = new IdentificationDTO();
        this.identificationDTOHospitalPrivado1.setPersonId(identificationHospitalPrivado1.getPerson().getId());
        this.identificationDTOHospitalPrivado1.setId(identificationHospitalPrivado1.getId());
        this.identificationDTOHospitalPrivado1.setValue(identificationHospitalPrivado1.getNumber());
        this.identificationDTOHospitalPrivado1.setTypeId(identificationHospitalPrivado1.getType().getId());

        this.identificationDTOHospitalPrivado2 = new IdentificationDTO();
        this.identificationDTOHospitalPrivado2.setPersonId(identificationHospitalPrivado2.getPerson().getId());
        this.identificationDTOHospitalPrivado2.setId(identificationHospitalPrivado2.getId());
        this.identificationDTOHospitalPrivado2.setValue(identificationHospitalPrivado2.getNumber());
        this.identificationDTOHospitalPrivado2.setTypeId(identificationHospitalPrivado2.getType().getId());

        this.addressDTOHospitalPrivado1 = new AddressDTO();
        this.addressDTOHospitalPrivado1.setPersonId(addressHospitalPrivado1.getPerson().getId());
        this.addressDTOHospitalPrivado1.setId(addressHospitalPrivado1.getId());
        this.addressDTOHospitalPrivado1.setComplete_address(addressHospitalPrivado1.getComplete_address());
        this.addressDTOHospitalPrivado1.setFloor(addressHospitalPrivado1.getFloor());
        this.addressDTOHospitalPrivado1.setApartment(addressHospitalPrivado1.getApartment());
        this.addressDTOHospitalPrivado1.setNumber(addressHospitalPrivado1.getNumber());
        this.addressDTOHospitalPrivado1.setZip(addressHospitalPrivado1.getZip());
        this.addressDTOHospitalPrivado1.setSection(addressHospitalPrivado1.getSection());
        this.addressDTOHospitalPrivado1.setStreet(addressHospitalPrivado1.getStreet());
        this.addressDTOHospitalPrivado1.setLocationId(addressHospitalPrivado1.getLocation().getId());
        this.addressDTOHospitalPrivado1.setProvinceId(addressHospitalPrivado1.getProvince().getId());

        this.emailDTOHospitalPrivadoList = Arrays.asList(emailDTOHospitalPrivado1, emailDTOHospitalPrivado2);
        this.phoneDTOHospitalPrivadoList = Arrays.asList(phoneDTOHospitalPrivado1, phoneDTOHospitalPrivado2);
        this.identificationDTOHospitalPrivadoList = Arrays.asList(identificationDTOHospitalPrivado1, identificationDTOHospitalPrivado2);
        this.addressDTOHospitalPrivadoList = Arrays.asList(addressDTOHospitalPrivado1);

        this.medicalCenterDTOReinaFabiola = new MedicalCenterDTO();
        this.medicalCenterDTOReinaFabiola.setName("Reina Fabiola");
        this.medicalCenterDTOReinaFabiola.setEmails(emailDTOReinaFabiolaList);
        this.medicalCenterDTOReinaFabiola.setPhones(phoneDTOReinaFabiolaList);
        this.medicalCenterDTOReinaFabiola.setIdentifications(identificationDTOReinaFabiolaList);
        this.medicalCenterDTOReinaFabiola.setAddresses(addressDTOReinaFabiolaList);

        this.medicalCenterDTOHospitalPrivado = new MedicalCenterDTO();
        this.medicalCenterDTOHospitalPrivado.setName("Hospital Privado");
        this.medicalCenterDTOHospitalPrivado.setEmails(emailDTOHospitalPrivadoList);
        this.medicalCenterDTOHospitalPrivado.setPhones(phoneDTOHospitalPrivadoList);
        this.medicalCenterDTOHospitalPrivado.setIdentifications(identificationDTOHospitalPrivadoList);
        this.medicalCenterDTOHospitalPrivado.setAddresses(addressDTOHospitalPrivadoList);
    }

    @Test
    @DisplayName("NewMedicalCenter: Valid MedicalCenterDTO should return APIResponse")
    void testNewMedicalCenter_whenMedicalCenterDTOIsValid_ShouldReturnAPIResponse() {
        //Given
        MedicalCenter medicalCenterReinaFabiola = new MedicalCenter();
        medicalCenterReinaFabiola.setId(null);
        medicalCenterReinaFabiola.setName("Reina Fabiola");
        medicalCenterReinaFabiola.setFullName(medicalCenterReinaFabiola.getName());
        medicalCenterReinaFabiola.setEmails(emailReinaFabiolaList);
        medicalCenterReinaFabiola.setPhones(phoneReinaFabiolaList);
        medicalCenterReinaFabiola.setIdentifications(identificationReinaFabiolaList);
        medicalCenterReinaFabiola.setAddresses(addresReinaFabiolaList);

        when(medicalCenterRepository.save(eq(medicalCenterReinaFabiola))).thenReturn(medicalCenterReinaFabiola);
        when(medicalCenterRepository.findById(medicalCenterReinaFabiola.getId())).thenReturn(Optional.of(medicalCenterReinaFabiola));

        //When
        APIResponse result = medicalCenterService.newMedicalCenter(medicalCenterDTOReinaFabiola);

        //Then
        assertEquals(HttpStatus.CREATED.value(), result.getStatus());
        assertEquals(MessagesResponse.addSuccess, result.getMessage());
        assertNotNull(result.getData());
        assertEquals(medicalCenterReinaFabiola, result.getData()); // Now the comparison should work

        verify(medicalCenterRepository, times(1)).save(medicalCenterReinaFabiola);
        verify(medicalCenterRepository, times(1)).findById(medicalCenterReinaFabiola.getId());
    }

    @Test
    @DisplayName("NewMedicalCenter: If name is null should throw NotValidException")
    void testNewMedicalCenter_whenNameIsNull_ShouldThrownNotValidException() {
        MedicalCenterDTO medicalCenterDTO = new MedicalCenterDTO();
        medicalCenterDTO.setName(null);

        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", null);

        assertThrows(NotValidException.class, () -> medicalCenterService.newMedicalCenter(medicalCenterDTO), exception.getMessage());

        verify(medicalCenterRepository, never()).save(any(MedicalCenter.class));
    }

}