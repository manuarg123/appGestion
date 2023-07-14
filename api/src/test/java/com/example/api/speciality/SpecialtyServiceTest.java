package com.example.api.speciality;

import com.example.api.common.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SpecialtyServiceTest {

    @Mock
    private SpecialtyRepository specialtyRepository;

    @InjectMocks
    private SpecialtyService specialtyService;

    private Speciality speciality;
    private Speciality speciality2;

    private List<Speciality> specialityList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.speciality = new Speciality(1L, "specialty");
        this.speciality2 = new Speciality(2L, "specialty2");
        this.specialityList = Arrays.asList(speciality, speciality2);
    }

    @Test
    @DisplayName("GetSpecialty: If id exists should return APIResponse data key")
    void testGetSpecialty_whenIdExists_shouldReturnAPIResponse(){
        Long id = 1L;

        when(specialtyRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(speciality));

        APIResponse result = specialtyService.getSpeciality(id);

        assertEquals(result.getData(), speciality);
        verify(specialtyRepository, times(1)).findByIdAndDeletedAtIsNull(id);
    }

    @Test
    @DisplayName("GetSpecialty: If specialty was soft deleted or id not exists should thrown NotFoundException")
    void testGetSpecialty_whenIdSpecialtyWasSoftDeleted_shouldThrownNotFoundException(){
        Long id = 1L;

        when(specialtyRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> specialtyService.getSpeciality(id));

        verify(specialtyRepository, times(1)).findByIdAndDeletedAtIsNull(id);
    }

    @Test
    @DisplayName("GetSpecialty: If id is null should thrown NotFoundException")
    void testGetSpecialty_whenIdIsNull_shouldThrownNotFoundException(){
        Long id = 1L;

        when(specialtyRepository.findByIdAndDeletedAtIsNull(null)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> specialtyService.getSpeciality(id));

        verify(specialtyRepository, times(1)).findByIdAndDeletedAtIsNull(id);
    }

    @Test
    @DisplayName("GetSpecialities: Should return list of Specialities")
    void testGetSpecialties_ShouldReturnListOfSpecialties(){
        when(specialtyRepository.findByDeletedAtIsNull()).thenReturn(specialityList);

        List<Speciality> result = specialtyService.getSpecialities();

        assertEquals(specialityList, result);

        verify(specialtyRepository,times(1)).findByDeletedAtIsNull();
    }

    @Test
    @DisplayName("GetSpecialities: If not Specialties exists should return empty list")
    void testGetSpecialties_whenNotSpecialtiesExists_ShouldReturnEmptyList(){
        when(specialtyRepository.findByDeletedAtIsNull()).thenReturn(new ArrayList<>());

        List<Speciality> result = specialtyService.getSpecialities();

        assertEquals(0, result.size());

        verify(specialtyRepository, times(1)).findByDeletedAtIsNull();
    }

    @Test
    @DisplayName("newSpecialty: If name alreadyExists should thrown DuplicatedRecordException")
    void testNewSpecialty_whenNameAlreadyExists_ShouldThrownDuplicatedRecordException(){
        String name = "specialty";

        SpecialtyDTO specialtyDTO = new SpecialtyDTO();
        specialtyDTO.setName(name);

        when(specialtyRepository.findSpecialityByName(name)).thenReturn(Optional.of(new Speciality()));

        assertThrows(DuplicateRecordException.class, () -> specialtyService.newSpeciality(specialtyDTO));

        verify(specialtyRepository, times(1)).findSpecialityByName(name);
    }

    @Test
    @DisplayName("newSpecialty: Valid SpecialtyDTO should return APIResponse")
    void testNewSpecialty_whenSpecialtyDTOIsValid_ShouldReturnAPIResponse() {
        SpecialtyDTO specialtyDTO = new SpecialtyDTO();
        specialtyDTO.setName("Valid Specialty");

        assertDoesNotThrow(() -> specialtyService.newSpeciality(specialtyDTO));

        verify(specialtyRepository, times(1)).findSpecialityByName(specialtyDTO.getName());
    }

    @Test
    @DisplayName("newSpecialty: If name is null should throw NotValidException")
    void testNewSpecialty_whenNameIsNull_ShouldThrownNotValidException() {
        SpecialtyDTO specialtyDTO = new SpecialtyDTO();
        specialtyDTO.setName(null);

        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<SpecialtyDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(specialtyDTO, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);

        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> specialtyService.newSpeciality(specialtyDTO), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(specialtyRepository, never()).findSpecialityByName(anyString());
    }

    @Test
    @DisplayName("newSpecialty: If name is blank should throw NotValidException")
    void testNewSpecialty_whenNameIsBlank_ShouldThrownNotValidException() {
        SpecialtyDTO specialtyDTO = new SpecialtyDTO();
        specialtyDTO.setName("");

        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<SpecialtyDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(specialtyDTO, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);

        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> specialtyService.newSpeciality(specialtyDTO), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(specialtyRepository, never()).findSpecialityByName(anyString());
    }

    @Test
    @DisplayName("newSpecialty: If name is much size should throw NotValidException")
    void testNewSpecialty_whenNameIsToMuchSize_ShouldThrownNotValidException() {
        SpecialtyDTO specialtyDTO = new SpecialtyDTO();
        specialtyDTO.setName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<SpecialtyDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(specialtyDTO, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);

        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> specialtyService.newSpeciality(specialtyDTO), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(specialtyRepository, never()).findSpecialityByName(anyString());
    }

    @Test
    @DisplayName("EditSpecialty: Valid SpecialtyDTO should return APIResponse")
    void testEditSpecialty_whenSpecialtyDTOIsValid_ShouldReturnAPIResponse() {
        Long id = 1L;
        SpecialtyDTO specialtyDTO = new SpecialtyDTO();
        specialtyDTO.setName("Valid Specialty");

        Speciality existingSpeciality = new Speciality();
        existingSpeciality.setId(id);
        existingSpeciality.setName("Existing Specialty");

        when(specialtyRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(existingSpeciality));
        when(specialtyRepository.findSpecialityByName(specialtyDTO.getName())).thenReturn(Optional.empty());
        when(specialtyRepository.save(existingSpeciality)).thenReturn(existingSpeciality);

        APIResponse result = specialtyService.editSpeciality(id, specialtyDTO);

        assertEquals(HttpStatus.OK.value(), result.getStatus());
        assertEquals(MessagesResponse.editSuccess, result.getMessage());
        assertNotNull(result.getData());

        verify(specialtyRepository, times(1)).findByIdAndDeletedAtIsNull(id);
        verify(specialtyRepository, times(1)).findSpecialityByName(specialtyDTO.getName());
        verify(specialtyRepository, times(1)).save(existingSpeciality);
    }

    @Test
    @DisplayName("EditSpecialty: If name is null should throw NotValidException")
    void testEditSpecialty_whenNameIsNull_ShouldThrownNotValidException() {
        Long id = 1L;
        SpecialtyDTO specialtyDTO = new SpecialtyDTO();
        specialtyDTO.setName(null);

        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<SpecialtyDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(specialtyDTO, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);

        SpecialtyDTO existingSpeciality = new SpecialtyDTO();
        existingSpeciality.setName(null);

        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> specialtyService.editSpeciality(id,existingSpeciality), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(specialtyRepository, never()).findSpecialityByName(anyString());
    }

    @Test
    @DisplayName("EditSpecialty: If name is blank should throw NotValidException")
    void testEditSpecialty_whenNameIsBlank_ShouldThrownNotValidException() {
        Long id = 1L;
        SpecialtyDTO existingSpeciality = new SpecialtyDTO();
        existingSpeciality.setName("");

        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<SpecialtyDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(existingSpeciality, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);


        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> specialtyService.editSpeciality(id,existingSpeciality), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(specialtyRepository, never()).findSpecialityByName(anyString());
    }
    @Test
    @DisplayName("EditSpecialty: If name is much size should throw NotValidException")
    void testEditSpecialty_whenNameIsToMuchSize_ShouldThrownNotValidException() {
        Long id = 1L;
        SpecialtyDTO existingSpeciality = new SpecialtyDTO();
        existingSpeciality.setName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<SpecialtyDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(existingSpeciality, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);


        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> specialtyService.editSpeciality(id,existingSpeciality), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(specialtyRepository, never()).findSpecialityByName(anyString());
    }

    @Test
    @DisplayName("EditSpecialty: If name Exists Should return DuplicatedRecordException")
    void testEditSpecialty_whenNameExists_ShouldThrownDuplicatedRecordException(){
        Long id = 1L;
        String newName = "Existing Specialty";
        SpecialtyDTO specialtyDTO = new SpecialtyDTO();
        specialtyDTO.setName(newName);

        Speciality existingSpeciality = new Speciality();
        existingSpeciality.setId(id);
        existingSpeciality.setName("Existing Specialty");

        when(specialtyRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(existingSpeciality));
        when(specialtyRepository.findSpecialityByName(newName)).thenReturn(Optional.of(existingSpeciality));

        assertThrows(DuplicateRecordException.class, () -> specialtyService.editSpeciality(id, specialtyDTO));

        verify(specialtyRepository, times(1)).findByIdAndDeletedAtIsNull(id);
        verify(specialtyRepository, times(1)).findSpecialityByName(newName);
        verify(specialtyRepository, never()).save(existingSpeciality);
    }

    @Test
    @DisplayName("EditSpecialty: If record not found should throw NotFoundException")
    void testEditSpecialty_whenRecordNotFound_ShouldThrowNotFoundException() {
        Long id = 1L;
        SpecialtyDTO specialtyDTO = new SpecialtyDTO();
        specialtyDTO.setName("Valid Specialty");

        when(specialtyRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> specialtyService.editSpeciality(id, specialtyDTO));

        verify(specialtyRepository, times(1)).findByIdAndDeletedAtIsNull(id);
        verify(specialtyRepository, never()).findSpecialityByName(anyString());
        verify(specialtyRepository, never()).save(any());
    }
}