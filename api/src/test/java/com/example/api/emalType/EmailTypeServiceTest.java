package com.example.api.emalType;

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

class EmailTypeServiceTest {@Mock
private EmailTypeRepository emailTypeRepository;

    @InjectMocks
    private EmailTypeService emailTypeService;

    private EmailType emailType;
    private EmailType emailType2;

    private List<EmailType> emailTypeList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.emailType = new EmailType(1L, "emailType");
        this.emailType2 = new EmailType(2L, "emailType2");
        this.emailTypeList = Arrays.asList(emailType, emailType2);
    }

    @Test
    @DisplayName("GetEmailType: If id exists should return APIResponse data key")
    void testGetEmailType_whenIdExists_shouldReturnAPIResponse(){
        Long id = 1L;

        when(emailTypeRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(emailType));

        APIResponse result = emailTypeService.getEmailType(id);

        assertEquals(result.getData(), emailType);
        verify(emailTypeRepository, times(1)).findByIdAndDeletedAtIsNull(id);
    }

    @Test
    @DisplayName("GetEmailType: If emailType was soft deleted or id not exists should thrown NotFoundException")
    void testGetEmailType_whenIdEmailTypeWasSoftDeleted_shouldThrownNotFoundException(){
        Long id = 1L;

        when(emailTypeRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> emailTypeService.getEmailType(id));

        verify(emailTypeRepository, times(1)).findByIdAndDeletedAtIsNull(id);
    }

    @Test
    @DisplayName("GetEmailType: If id is null should thrown NotFoundException")
    void testGetEmailType_whenIdIsNull_shouldThrownNotFoundException(){
        Long id = 1L;

        when(emailTypeRepository.findByIdAndDeletedAtIsNull(null)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> emailTypeService.getEmailType(id));

        verify(emailTypeRepository, times(1)).findByIdAndDeletedAtIsNull(id);
    }

    @Test
    @DisplayName("GetEmailTypes: Should return list of EmailTypes")
    void testGetEmailTypes_ShouldReturnListOfEmailTypes(){
        when(emailTypeRepository.findByDeletedAtIsNull()).thenReturn(emailTypeList);

        List<EmailType> result = emailTypeService.getEmailTypes();

        assertEquals(emailTypeList, result);

        verify(emailTypeRepository,times(1)).findByDeletedAtIsNull();
    }

    @Test
    @DisplayName("GetEmailTypes: If not EmailTypes exists should return empty list")
    void testGetEmailTypes_whenNotEmailTypesExists_ShouldReturnEmptyList(){
        when(emailTypeRepository.findByDeletedAtIsNull()).thenReturn(new ArrayList<>());

        List<EmailType> result = emailTypeService.getEmailTypes();

        assertEquals(0, result.size());

        verify(emailTypeRepository, times(1)).findByDeletedAtIsNull();
    }

    @Test
    @DisplayName("newEmailType: If name alreadyExists should throw DuplicateRecordException")
    void testNewEmailType_whenNameAlreadyExists_shouldThrownDuplicateRecordException(){
        String name = "emailType";

        EmailTypeDTO emailTypeDTO = new EmailTypeDTO();
        emailTypeDTO.setName(name);

        when(emailTypeRepository.findEmailTypeByName(name)).thenReturn(Optional.of(new EmailType()));

        assertThrows(DuplicateRecordException.class, () -> emailTypeService.newEmailType(emailTypeDTO));

        verify(emailTypeRepository, times(1)).findEmailTypeByName(name);
    }

    @Test
    @DisplayName("newEmailType: Valid EmailTypeDTO should return APIResponse")
    void testNewEmailType_whenEmailTypeDTOIsValid_ShouldReturnAPIResponse() {
        EmailTypeDTO emailTypeDTO = new EmailTypeDTO();
        emailTypeDTO.setName("Valid EmailType");

        assertDoesNotThrow(() -> emailTypeService.newEmailType(emailTypeDTO));

        verify(emailTypeRepository, times(1)).findEmailTypeByName(emailTypeDTO.getName());
    }

    @Test
    @DisplayName("newEmailType: If name is null should throw NotValidException")
    void testNewEmailType_whenNameIsNull_ShouldThrownNotValidException() {
        EmailTypeDTO emailTypeDTO = new EmailTypeDTO();
        emailTypeDTO.setName(null);

        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<EmailTypeDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(emailTypeDTO, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);

        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> emailTypeService.newEmailType(emailTypeDTO), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(emailTypeRepository, never()).findEmailTypeByName(anyString());
    }

    @Test
    @DisplayName("newEmailType: If name is blank should throw NotValidException")
    void testNewEmailType_whenNameIsBlank_ShouldThrownNotValidException() {
        EmailTypeDTO emailTypeDTO = new EmailTypeDTO();
        emailTypeDTO.setName("");

        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<EmailTypeDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(emailTypeDTO, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);

        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> emailTypeService.newEmailType(emailTypeDTO), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(emailTypeRepository, never()).findEmailTypeByName(anyString());
    }

    @Test
    @DisplayName("newEmailType: If name is too long should throw NotValidException")
    void testNewEmailType_whenNameIsTooLong_ShouldThrownNotValidException() {
        EmailTypeDTO emailTypeDTO = new EmailTypeDTO();
        emailTypeDTO.setName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<EmailTypeDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(emailTypeDTO, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);

        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> emailTypeService.newEmailType(emailTypeDTO), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(emailTypeRepository, never()).findEmailTypeByName(anyString());
    }

    @Test
    @DisplayName("editEmailType: Valid EmailTypeDTO should return APIResponse")
    void testEditEmailType_whenEmailTypeDTOIsValid_ShouldReturnAPIResponse() {
        Long id = 1L;
        EmailTypeDTO emailTypeDTO = new EmailTypeDTO();
        emailTypeDTO.setName("Valid EmailType");

        EmailType existingEmailType = new EmailType();
        existingEmailType.setId(id);
        existingEmailType.setName("Existing EmailType");

        when(emailTypeRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(existingEmailType));
        when(emailTypeRepository.findEmailTypeByName(emailTypeDTO.getName())).thenReturn(Optional.empty());
        when(emailTypeRepository.save(existingEmailType)).thenReturn(existingEmailType);

        APIResponse result = emailTypeService.editEmailType(id, emailTypeDTO);

        assertEquals(HttpStatus.OK.value(), result.getStatus());
        assertEquals(MessagesResponse.editSuccess, result.getMessage());
        assertNotNull(result.getData());

        verify(emailTypeRepository, times(1)).findByIdAndDeletedAtIsNull(id);
        verify(emailTypeRepository, times(1)).findEmailTypeByName(emailTypeDTO.getName());
        verify(emailTypeRepository, times(1)).save(existingEmailType);
    }

    @Test
    @DisplayName("editEmailType: If name is null should throw NotValidException")
    void testEditEmailType_whenNameIsNull_ShouldThrownNotValidException() {
        Long id = 1L;
        EmailTypeDTO emailTypeDTO = new EmailTypeDTO();
        emailTypeDTO.setName(null);

        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<EmailTypeDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(emailTypeDTO, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);

        EmailTypeDTO existingEmailType = new EmailTypeDTO();
        existingEmailType.setName(null);

        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> emailTypeService.editEmailType(id,existingEmailType), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(emailTypeRepository, never()).findEmailTypeByName(anyString());
    }

    @Test
    @DisplayName("editEmailType: If name is blank should throw NotValidException")
    void testEditEmailType_whenNameIsBlank_ShouldThrownNotValidException() {
        Long id = 1L;
        EmailTypeDTO existingEmailType = new EmailTypeDTO();
        existingEmailType.setName("");

        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<EmailTypeDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(existingEmailType, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);


        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> emailTypeService.editEmailType(id,existingEmailType), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(emailTypeRepository, never()).findEmailTypeByName(anyString());
    }
    @Test
    @DisplayName("editEmailType: If name is too long should throw NotValidException")
    void testEditEmailType_whenNameIsTooLong_ShouldThrownNotValidException() {
        Long id = 1L;
        EmailTypeDTO existingEmailType = new EmailTypeDTO();
        existingEmailType.setName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        // Simula la validación fallida con @Valid
        Set<ConstraintViolation<EmailTypeDTO>> violations = new HashSet<>();
        violations.add(Validation.buildDefaultValidatorFactory().getValidator().validateProperty(existingEmailType, "name").iterator().next());
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);


        // Verifica que se lance la excepción NotValidException
        assertThrows(NotValidException.class, () -> emailTypeService.editEmailType(id,existingEmailType), exception.getMessage());

        // Verifica que no se haya llamado al repositorio
        verify(emailTypeRepository, never()).findEmailTypeByName(anyString());
    }

    @Test
    @DisplayName("editEmailType: If name Exists Should throw DuplicateRecordException")
    void testEditEmailType_whenNameExists_ShouldThrownDuplicateRecordException(){
        Long id = 1L;
        String newName = "Existing EmailType";
        EmailTypeDTO emailTypeDTO = new EmailTypeDTO();
        emailTypeDTO.setName(newName);

        EmailType existingEmailType = new EmailType();
        existingEmailType.setId(id);
        existingEmailType.setName("Existing EmailType");

        when(emailTypeRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(existingEmailType));
        when(emailTypeRepository.findEmailTypeByName(newName)).thenReturn(Optional.of(existingEmailType));

        assertThrows(DuplicateRecordException.class, () -> emailTypeService.editEmailType(id, emailTypeDTO));

        verify(emailTypeRepository, times(1)).findByIdAndDeletedAtIsNull(id);
        verify(emailTypeRepository, times(1)).findEmailTypeByName(newName);
        verify(emailTypeRepository, never()).save(existingEmailType);
    }

    @Test
    @DisplayName("editEmailType: If record not found should throw NotFoundException")
    void testEditEmailType_whenRecordNotFound_ShouldThrowNotFoundException() {
        Long id = 1L;
        EmailTypeDTO emailTypeDTO = new EmailTypeDTO();
        emailTypeDTO.setName("Valid EmailType");

        when(emailTypeRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> emailTypeService.editEmailType(id, emailTypeDTO));

        verify(emailTypeRepository, times(1)).findByIdAndDeletedAtIsNull(id);
        verify(emailTypeRepository, never()).findEmailTypeByName(anyString());
        verify(emailTypeRepository, never()).save(any());
    }
}