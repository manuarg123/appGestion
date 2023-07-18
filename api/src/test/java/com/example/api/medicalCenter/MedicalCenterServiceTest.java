package com.example.api.medicalCenter;

import com.example.api.address.AddressDTO;
import com.example.api.address.AddressService;
import com.example.api.common.*;
import com.example.api.email.EmailDTO;
import com.example.api.email.EmailService;
import com.example.api.identification.IdentificationService;
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
    private MedicalCenterDTO medicalCenterDTOReinaFabiola;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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

        // Simula la validación fallida con @Valid
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", null);

        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> medicalCenterService.newMedicalCenter(medicalCenterDTO), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(medicalCenterRepository, never()).save(any(MedicalCenter.class));
    }

    // Resto de los métodos de prueba
    // ...

}