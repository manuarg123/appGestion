package com.example.api.identificationType;

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

class IdentificationTypeServiceTest {

    @Mock
    private IdentificationTypeRepository identificationTypeRepository;

    @InjectMocks
    private IdentificationTypeService identificationTypeService;

    private IdentificationType identificationType;
    private IdentificationType identificationType2;

    private List<IdentificationType> identificationTypeList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.identificationType = new IdentificationType(1L, "identificationType");
        this.identificationType2 = new IdentificationType(2L, "identificationType2");
        this.identificationTypeList = Arrays.asList(identificationType, identificationType2);
    }

    @Test
    @DisplayName("GetIdentificationType: If id exists should return APIResponse data key")
    void testGetIdentificationType_whenIdExists_shouldReturnAPIResponse(){
        Long id = 1L;

        when(identificationTypeRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(identificationType));

        APIResponse result = identificationTypeService.getIdentificationType(id);

        assertEquals(result.getData(), identificationType);
        verify(identificationTypeRepository, times(1)).findByIdAndDeletedAtIsNull(id);
    }

    @Test
    @DisplayName("GetIdentificationType: If identificationType was soft deleted or id not exists should thrown NotFoundException")
    void testGetIdentificationType_whenIdIdentificationTypeWasSoftDeleted_shouldThrownNotFoundException(){
        Long id = 1L;

        when(identificationTypeRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> identificationTypeService.getIdentificationType(id));

        verify(identificationTypeRepository, times(1)).findByIdAndDeletedAtIsNull(id);
    }

    @Test
    @DisplayName("GetIdentificationType: If id is null should thrown NotFoundException")
    void testGetIdentificationType_whenIdIsNull_shouldThrownNotFoundException(){
        Long id = 1L;

        when(identificationTypeRepository.findByIdAndDeletedAtIsNull(null)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> identificationTypeService.getIdentificationType(id));

        verify(identificationTypeRepository, times(1)).findByIdAndDeletedAtIsNull(id);
    }

    @Test
    @DisplayName("GetIdentificationTypes: Should return list of IdentificationTypes")
    void testGetSpecialties_ShouldReturnListOfSpecialties(){
        when(identificationTypeRepository.findByDeletedAtIsNull()).thenReturn(identificationTypeList);

        List<IdentificationType> result = identificationTypeService.getIdentificationTypes();

        assertEquals(identificationTypeList, result);

        verify(identificationTypeRepository,times(1)).findByDeletedAtIsNull();
    }

    @Test
    @DisplayName("GetIdentificationTypes: If not Specialties exists should return empty list")
    void testGetSpecialties_whenNotSpecialtiesExists_ShouldReturnEmptyList(){
        when(identificationTypeRepository.findByDeletedAtIsNull()).thenReturn(new ArrayList<>());

        List<IdentificationType> result = identificationTypeService.getIdentificationTypes();

        assertEquals(0, result.size());

        verify(identificationTypeRepository, times(1)).findByDeletedAtIsNull();
    }

    @Test
    @DisplayName("newIdentificationType: If name alreadyExists should thrown DuplicatedRecordException")
    void testNewIdentificationType_whenNameAlreadyExists_ShouldThrownDuplicatedRecordException(){
        String name = "identificationType";

        IdentificationTypeDTO identificationTypeDTO = new IdentificationTypeDTO();
        identificationTypeDTO.setName(name);

        when(identificationTypeRepository.findIdentificationTypeByName(name)).thenReturn(Optional.of(new IdentificationType()));

        assertThrows(DuplicateRecordException.class, () -> identificationTypeService.newIdentificationType(identificationTypeDTO));

        verify(identificationTypeRepository, times(1)).findIdentificationTypeByName(name);
    }

    @Test
    @DisplayName("newIdentificationType: Valid IdentificationTypeDTO should return APIResponse")
    void testNewIdentificationType_whenIdentificationTypeDTOIsValid_ShouldReturnAPIResponse() {
        IdentificationTypeDTO identificationTypeDTO = new IdentificationTypeDTO();
        identificationTypeDTO.setName("Valid IdentificationType");

        assertDoesNotThrow(() -> identificationTypeService.newIdentificationType(identificationTypeDTO));

        verify(identificationTypeRepository, times(1)).findIdentificationTypeByName(identificationTypeDTO.getName());
    }

    @Test
    @DisplayName("newIdentificationType: If name is null should throw NotValidException")
    void testNewIdentificationType_whenNameIsNull_ShouldThrownNotValidException() {
        IdentificationTypeDTO identificationTypeDTO = new IdentificationTypeDTO();
        identificationTypeDTO.setName(null);

        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<IdentificationTypeDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(identificationTypeDTO, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);

        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> identificationTypeService.newIdentificationType(identificationTypeDTO), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(identificationTypeRepository, never()).findIdentificationTypeByName(anyString());
    }

    @Test
    @DisplayName("newIdentificationType: If name is blank should throw NotValidException")
    void testNewIdentificationType_whenNameIsBlank_ShouldThrownNotValidException() {
        IdentificationTypeDTO identificationTypeDTO = new IdentificationTypeDTO();
        identificationTypeDTO.setName("");

        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<IdentificationTypeDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(identificationTypeDTO, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);

        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> identificationTypeService.newIdentificationType(identificationTypeDTO), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(identificationTypeRepository, never()).findIdentificationTypeByName(anyString());
    }

    @Test
    @DisplayName("newIdentificationType: If name is much size should throw NotValidException")
    void testNewIdentificationType_whenNameIsToMuchSize_ShouldThrownNotValidException() {
        IdentificationTypeDTO identificationTypeDTO = new IdentificationTypeDTO();
        identificationTypeDTO.setName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<IdentificationTypeDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(identificationTypeDTO, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);

        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> identificationTypeService.newIdentificationType(identificationTypeDTO), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(identificationTypeRepository, never()).findIdentificationTypeByName(anyString());
    }

    @Test
    @DisplayName("EditIdentificationType: Valid IdentificationTypeDTO should return APIResponse")
    void testEditIdentificationType_whenIdentificationTypeDTOIsValid_ShouldReturnAPIResponse() {
        Long id = 1L;
        IdentificationTypeDTO identificationTypeDTO = new IdentificationTypeDTO();
        identificationTypeDTO.setName("Valid IdentificationType");

        IdentificationType existingIdentificationType = new IdentificationType();
        existingIdentificationType.setId(id);
        existingIdentificationType.setName("Existing IdentificationType");

        when(identificationTypeRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(existingIdentificationType));
        when(identificationTypeRepository.findIdentificationTypeByName(identificationTypeDTO.getName())).thenReturn(Optional.empty());
        when(identificationTypeRepository.save(existingIdentificationType)).thenReturn(existingIdentificationType);

        APIResponse result = identificationTypeService.editIdentificationType(id, identificationTypeDTO);

        assertEquals(HttpStatus.OK.value(), result.getStatus());
        assertEquals(MessagesResponse.editSuccess, result.getMessage());
        assertNotNull(result.getData());

        verify(identificationTypeRepository, times(1)).findByIdAndDeletedAtIsNull(id);
        verify(identificationTypeRepository, times(1)).findIdentificationTypeByName(identificationTypeDTO.getName());
        verify(identificationTypeRepository, times(1)).save(existingIdentificationType);
    }

    @Test
    @DisplayName("EditIdentificationType: If name is null should throw NotValidException")
    void testEditIdentificationType_whenNameIsNull_ShouldThrownNotValidException() {
        Long id = 1L;
        IdentificationTypeDTO identificationTypeDTO = new IdentificationTypeDTO();
        identificationTypeDTO.setName(null);

        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<IdentificationTypeDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(identificationTypeDTO, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);

        IdentificationTypeDTO existingIdentificationType = new IdentificationTypeDTO();
        existingIdentificationType.setName(null);

        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> identificationTypeService.editIdentificationType(id,existingIdentificationType), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(identificationTypeRepository, never()).findIdentificationTypeByName(anyString());
    }

    @Test
    @DisplayName("EditIdentificationType: If name is blank should throw NotValidException")
    void testEditIdentificationType_whenNameIsBlank_ShouldThrownNotValidException() {
        Long id = 1L;
        IdentificationTypeDTO existingIdentificationType = new IdentificationTypeDTO();
        existingIdentificationType.setName("");

        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<IdentificationTypeDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(existingIdentificationType, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);


        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> identificationTypeService.editIdentificationType(id,existingIdentificationType), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(identificationTypeRepository, never()).findIdentificationTypeByName(anyString());
    }
    @Test
    @DisplayName("EditIdentificationType: If name is much size should throw NotValidException")
    void testEditIdentificationType_whenNameIsToMuchSize_ShouldThrownNotValidException() {
        Long id = 1L;
        IdentificationTypeDTO existingIdentificationType = new IdentificationTypeDTO();
        existingIdentificationType.setName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<IdentificationTypeDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(existingIdentificationType, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);


        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> identificationTypeService.editIdentificationType(id,existingIdentificationType), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(identificationTypeRepository, never()).findIdentificationTypeByName(anyString());
    }

    @Test
    @DisplayName("EditIdentificationType: If name Exists Should return DuplicatedRecordException")
    void testEditIdentificationType_whenNameExists_ShouldThrownDuplicatedRecordException(){
        Long id = 1L;
        String newName = "Existing IdentificationType";
        IdentificationTypeDTO identificationTypeDTO = new IdentificationTypeDTO();
        identificationTypeDTO.setName(newName);

        IdentificationType existingIdentificationType = new IdentificationType();
        existingIdentificationType.setId(id);
        existingIdentificationType.setName("Existing IdentificationType");

        when(identificationTypeRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(existingIdentificationType));
        when(identificationTypeRepository.findIdentificationTypeByName(newName)).thenReturn(Optional.of(existingIdentificationType));

        assertThrows(DuplicateRecordException.class, () -> identificationTypeService.editIdentificationType(id, identificationTypeDTO));

        verify(identificationTypeRepository, times(1)).findByIdAndDeletedAtIsNull(id);
        verify(identificationTypeRepository, times(1)).findIdentificationTypeByName(newName);
        verify(identificationTypeRepository, never()).save(existingIdentificationType);
    }

    @Test
    @DisplayName("EditIdentificationType: If record not found should throw NotFoundException")
    void testEditIdentificationType_whenRecordNotFound_ShouldThrowNotFoundException() {
        Long id = 1L;
        IdentificationTypeDTO identificationTypeDTO = new IdentificationTypeDTO();
        identificationTypeDTO.setName("Valid IdentificationType");

        when(identificationTypeRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> identificationTypeService.editIdentificationType(id, identificationTypeDTO));

        verify(identificationTypeRepository, times(1)).findByIdAndDeletedAtIsNull(id);
        verify(identificationTypeRepository, never()).findIdentificationTypeByName(anyString());
        verify(identificationTypeRepository, never()).save(any());
    }
}