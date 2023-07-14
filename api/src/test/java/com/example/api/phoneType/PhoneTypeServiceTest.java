package com.example.api.phoneType;

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

class PhoneTypeServiceTest {

    @Mock
    private PhoneTypeRepository phoneTypeRepository;

    @InjectMocks
    private PhoneTypeService phoneTypeService;

    private PhoneType phoneType;
    private PhoneType phoneType2;

    private List<PhoneType> phoneTypeList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.phoneType = new PhoneType(1L, "phoneType");
        this.phoneType2 = new PhoneType(2L, "phoneType2");
        this.phoneTypeList = Arrays.asList(phoneType, phoneType2);
    }

    @Test
    @DisplayName("GetPhoneType: If id exists should return APIResponse data key")
    void testGetPhoneType_whenIdExists_shouldReturnAPIResponse(){
        Long id = 1L;

        when(phoneTypeRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(phoneType));

        APIResponse result = phoneTypeService.getPhoneType(id);

        assertEquals(result.getData(), phoneType);
        verify(phoneTypeRepository, times(1)).findByIdAndDeletedAtIsNull(id);
    }

    @Test
    @DisplayName("GetPhoneType: If phoneType was soft deleted or id not exists should thrown NotFoundException")
    void testGetPhoneType_whenIdPhoneTypeWasSoftDeleted_shouldThrownNotFoundException(){
        Long id = 1L;

        when(phoneTypeRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> phoneTypeService.getPhoneType(id));

        verify(phoneTypeRepository, times(1)).findByIdAndDeletedAtIsNull(id);
    }

    @Test
    @DisplayName("GetPhoneType: If id is null should thrown NotFoundException")
    void testGetPhoneType_whenIdIsNull_shouldThrownNotFoundException(){
        Long id = 1L;

        when(phoneTypeRepository.findByIdAndDeletedAtIsNull(null)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> phoneTypeService.getPhoneType(id));

        verify(phoneTypeRepository, times(1)).findByIdAndDeletedAtIsNull(id);
    }

    @Test
    @DisplayName("GetPhoneTypes: Should return list of Specialities")
    void testGetSpecialties_ShouldReturnListOfSpecialties(){
        when(phoneTypeRepository.findByDeletedAtIsNull()).thenReturn(phoneTypeList);

        List<PhoneType> result = phoneTypeService.getPhoneTypes();

        assertEquals(phoneTypeList, result);

        verify(phoneTypeRepository,times(1)).findByDeletedAtIsNull();
    }

    @Test
    @DisplayName("GetPhoneTypes: If not Specialties exists should return empty list")
    void testGetSpecialties_whenNotSpecialtiesExists_ShouldReturnEmptyList(){
        when(phoneTypeRepository.findByDeletedAtIsNull()).thenReturn(new ArrayList<>());

        List<PhoneType> result = phoneTypeService.getPhoneTypes();

        assertEquals(0, result.size());

        verify(phoneTypeRepository, times(1)).findByDeletedAtIsNull();
    }

    @Test
    @DisplayName("newPhoneType: If name alreadyExists should thrown DuplicatedRecordException")
    void testNewPhoneType_whenNameAlreadyExists_ShouldThrownDuplicatedRecordException(){
        String name = "phoneType";

        PhoneTypeDTO phoneTypeDTO = new PhoneTypeDTO();
        phoneTypeDTO.setName(name);

        when(phoneTypeRepository.findPhoneTypeByName(name)).thenReturn(Optional.of(new PhoneType()));

        assertThrows(DuplicateRecordException.class, () -> phoneTypeService.newPhoneType(phoneTypeDTO));

        verify(phoneTypeRepository, times(1)).findPhoneTypeByName(name);
    }

    @Test
    @DisplayName("newPhoneType: Valid PhoneTypeDTO should return APIResponse")
    void testNewPhoneType_whenPhoneTypeDTOIsValid_ShouldReturnAPIResponse() {
        PhoneTypeDTO phoneTypeDTO = new PhoneTypeDTO();
        phoneTypeDTO.setName("Valid PhoneType");

        assertDoesNotThrow(() -> phoneTypeService.newPhoneType(phoneTypeDTO));

        verify(phoneTypeRepository, times(1)).findPhoneTypeByName(phoneTypeDTO.getName());
    }

    @Test
    @DisplayName("newPhoneType: If name is null should throw NotValidException")
    void testNewPhoneType_whenNameIsNull_ShouldThrownNotValidException() {
        PhoneTypeDTO phoneTypeDTO = new PhoneTypeDTO();
        phoneTypeDTO.setName(null);

        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<PhoneTypeDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(phoneTypeDTO, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);

        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> phoneTypeService.newPhoneType(phoneTypeDTO), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(phoneTypeRepository, never()).findPhoneTypeByName(anyString());
    }

    @Test
    @DisplayName("newPhoneType: If name is blank should throw NotValidException")
    void testNewPhoneType_whenNameIsBlank_ShouldThrownNotValidException() {
        PhoneTypeDTO phoneTypeDTO = new PhoneTypeDTO();
        phoneTypeDTO.setName("");

        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<PhoneTypeDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(phoneTypeDTO, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);

        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> phoneTypeService.newPhoneType(phoneTypeDTO), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(phoneTypeRepository, never()).findPhoneTypeByName(anyString());
    }

    @Test
    @DisplayName("newPhoneType: If name is much size should throw NotValidException")
    void testNewPhoneType_whenNameIsToMuchSize_ShouldThrownNotValidException() {
        PhoneTypeDTO phoneTypeDTO = new PhoneTypeDTO();
        phoneTypeDTO.setName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<PhoneTypeDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(phoneTypeDTO, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);

        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> phoneTypeService.newPhoneType(phoneTypeDTO), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(phoneTypeRepository, never()).findPhoneTypeByName(anyString());
    }

    @Test
    @DisplayName("EditPhoneType: Valid PhoneTypeDTO should return APIResponse")
    void testEditPhoneType_whenPhoneTypeDTOIsValid_ShouldReturnAPIResponse() {
        Long id = 1L;
        PhoneTypeDTO phoneTypeDTO = new PhoneTypeDTO();
        phoneTypeDTO.setName("Valid PhoneType");

        PhoneType existingPhoneType = new PhoneType();
        existingPhoneType.setId(id);
        existingPhoneType.setName("Existing PhoneType");

        when(phoneTypeRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(existingPhoneType));
        when(phoneTypeRepository.findPhoneTypeByName(phoneTypeDTO.getName())).thenReturn(Optional.empty());
        when(phoneTypeRepository.save(existingPhoneType)).thenReturn(existingPhoneType);

        APIResponse result = phoneTypeService.editPhoneType(id, phoneTypeDTO);

        assertEquals(HttpStatus.OK.value(), result.getStatus());
        assertEquals(MessagesResponse.editSuccess, result.getMessage());
        assertNotNull(result.getData());

        verify(phoneTypeRepository, times(1)).findByIdAndDeletedAtIsNull(id);
        verify(phoneTypeRepository, times(1)).findPhoneTypeByName(phoneTypeDTO.getName());
        verify(phoneTypeRepository, times(1)).save(existingPhoneType);
    }

    @Test
    @DisplayName("EditPhoneType: If name is null should throw NotValidException")
    void testEditPhoneType_whenNameIsNull_ShouldThrownNotValidException() {
        Long id = 1L;
        PhoneTypeDTO phoneTypeDTO = new PhoneTypeDTO();
        phoneTypeDTO.setName(null);

        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<PhoneTypeDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(phoneTypeDTO, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);

        PhoneTypeDTO existingPhoneType = new PhoneTypeDTO();
        existingPhoneType.setName(null);

        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> phoneTypeService.editPhoneType(id,existingPhoneType), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(phoneTypeRepository, never()).findPhoneTypeByName(anyString());
    }

    @Test
    @DisplayName("EditPhoneType: If name is blank should throw NotValidException")
    void testEditPhoneType_whenNameIsBlank_ShouldThrownNotValidException() {
        Long id = 1L;
        PhoneTypeDTO existingPhoneType = new PhoneTypeDTO();
        existingPhoneType.setName("");

        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<PhoneTypeDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(existingPhoneType, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);


        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> phoneTypeService.editPhoneType(id,existingPhoneType), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(phoneTypeRepository, never()).findPhoneTypeByName(anyString());
    }
    @Test
    @DisplayName("EditPhoneType: If name is much size should throw NotValidException")
    void testEditPhoneType_whenNameIsToMuchSize_ShouldThrownNotValidException() {
        Long id = 1L;
        PhoneTypeDTO existingPhoneType = new PhoneTypeDTO();
        existingPhoneType.setName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<PhoneTypeDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(existingPhoneType, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);


        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> phoneTypeService.editPhoneType(id,existingPhoneType), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(phoneTypeRepository, never()).findPhoneTypeByName(anyString());
    }

    @Test
    @DisplayName("EditPhoneType: If name Exists Should return DuplicatedRecordException")
    void testEditPhoneType_whenNameExists_ShouldThrownDuplicatedRecordException(){
        Long id = 1L;
        String newName = "Existing PhoneType";
        PhoneTypeDTO phoneTypeDTO = new PhoneTypeDTO();
        phoneTypeDTO.setName(newName);

        PhoneType existingPhoneType = new PhoneType();
        existingPhoneType.setId(id);
        existingPhoneType.setName("Existing PhoneType");

        when(phoneTypeRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(existingPhoneType));
        when(phoneTypeRepository.findPhoneTypeByName(newName)).thenReturn(Optional.of(existingPhoneType));

        assertThrows(DuplicateRecordException.class, () -> phoneTypeService.editPhoneType(id, phoneTypeDTO));

        verify(phoneTypeRepository, times(1)).findByIdAndDeletedAtIsNull(id);
        verify(phoneTypeRepository, times(1)).findPhoneTypeByName(newName);
        verify(phoneTypeRepository, never()).save(existingPhoneType);
    }

    @Test
    @DisplayName("EditPhoneType: If record not found should throw NotFoundException")
    void testEditPhoneType_whenRecordNotFound_ShouldThrowNotFoundException() {
        Long id = 1L;
        PhoneTypeDTO phoneTypeDTO = new PhoneTypeDTO();
        phoneTypeDTO.setName("Valid PhoneType");

        when(phoneTypeRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> phoneTypeService.editPhoneType(id, phoneTypeDTO));

        verify(phoneTypeRepository, times(1)).findByIdAndDeletedAtIsNull(id);
        verify(phoneTypeRepository, never()).findPhoneTypeByName(anyString());
        verify(phoneTypeRepository, never()).save(any());
    }
}