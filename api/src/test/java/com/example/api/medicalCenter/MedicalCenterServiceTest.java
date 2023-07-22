package com.example.api.medicalCenter;

import com.example.api.address.Address;
import com.example.api.address.AddressDTO;
import com.example.api.address.AddressService;
import com.example.api.common.*;
import com.example.api.email.Email;
import com.example.api.email.EmailDTO;
import com.example.api.email.EmailService;
import com.example.api.identification.Identification;
import com.example.api.identification.IdentificationService;
import com.example.api.phone.Phone;
import com.example.api.phone.PhoneDTO;
import com.example.api.phone.PhoneService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

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
    private Address addressReinaFabiola2;
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
    private Address addressHospitalPrivado2;
    private List<Address> addressHospitalPrivadoList;
    private Identification identificationHospitalPrivado1;
    private Identification identificationHospitalPrivado2;
    private List<Identification> identificationHospitalPrivadoList;
    private MedicalCenterDTO medicalCenterDTOReinaFabiola;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        this.medicalCenterReinaFabiola = new MedicalCenter();
        medicalCenterReinaFabiola.setFullName("Reina Fabiola");
        medicalCenterReinaFabiola.setName("Reina Fabiola");
        medicalCenterReinaFabiola.setEmails();
    }

    @Test
    @DisplayName("NewMedicalCenter: Valid MedicalCenterDTO should return APIResponse")
    void testNewMedicalCenter_whenMedicalCenterDTOIsValid_ShouldReturnAPIResponse() {
        MedicalCenterDTO medicalCenterDTO = new MedicalCenterDTO();
        medicalCenterDTO.setName("Reina Fabiola");

        MedicalCenter medicalCenter = new MedicalCenter();
        medicalCenter.setId(1L);
        medicalCenter.setName("Reina Fabiola");

        when(medicalCenterRepository.save(any(MedicalCenter.class))).thenReturn(medicalCenter);

        APIResponse result = medicalCenterService.newMedicalCenter(medicalCenterDTO);

        assertEquals(HttpStatus.OK.value(), result.getStatus());
        assertEquals(MessagesResponse.addSuccess, result.getMessage());
        assertNotNull(result.getData());

        verify(medicalCenterRepository, times(1)).save(any(MedicalCenter.class));
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

    // Resto de los métodos de prueba
    // ...

}